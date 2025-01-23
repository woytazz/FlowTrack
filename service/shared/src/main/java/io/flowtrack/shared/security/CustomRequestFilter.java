package io.flowtrack.shared.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.flowtrack.shared.common.CommonConstants;
import io.flowtrack.shared.security.interfaces.JwtDecoder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomRequestFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(CommonConstants.BEARER_JWT_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String accessToken = authorizationHeader.substring(CommonConstants.BEARER_JWT_PREFIX.length());
            DecodedJWT decodedJWT = jwtDecoder.decode(accessToken);
            Map<String, Claim> claims = decodedJWT.getClaims();

            if (!isAccessToken(claims)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

            List<String> roles = claims.get(CommonConstants.ROLES_JWT_CLAIM).asList(String.class);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            decodedJWT.getSubject(),
                            null,
                            SecurityAuthenticationUtil.getSimpleGrantedAuthorities(roles)
                    )
            );

            filterChain.doFilter(request, response);

        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    private boolean isAccessToken(Map<String, Claim> claims) {
        return claims.get(CommonConstants.ACCESS_JWT_CLAIM).asBoolean();
    }

}
