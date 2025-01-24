package io.flowtrack.gateway.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowtrack.gateway.security.AuthController;
import io.flowtrack.gateway.security.dto.LoginRequest;
import io.flowtrack.gateway.security.dto.LoginResponse;
import io.flowtrack.gateway.security.jwt.JwtProvider;
import io.flowtrack.shared.common.CommonConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtProvider jwtProvider;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        super(new AntPathRequestMatcher(AuthController.LOGIN_URL, HttpMethod.POST.name()), authenticationManager);
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        return this.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Pair<String, String> jwtPair = generateJwt(authResult);
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken(jwtPair.getFirst())
                .refreshToken(jwtPair.getSecond())
                .build();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private Pair<String, String> generateJwt(Authentication authentication) {
        if (authentication.getName().equals(CommonConstants.SYSTEM)) {
            return Pair.of(jwtProvider.provideInternalJwt(authentication.getName()), StringUtils.EMPTY);
        }
        return jwtProvider.provideExternalJwtPair(authentication.getName(), authentication.getAuthorities());
    }
}
