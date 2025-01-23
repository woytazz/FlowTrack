package io.flowtrack.gateway.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.flowtrack.shared.common.CommonConstants;
import io.flowtrack.shared.security.interfaces.JwtDecoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider implements JwtDecoder {

    @Value("${ISSUER:flowtrack}")
    private String issuer;

    @Value("${EXTERNAL_JWT_SECRET:external_jwt_secret}")
    private String externalJwtSecret;

    @Value("${INTERNAL_JWT_SECRET:internal_jwt_secret}")
    private String internalJwtSecret;

    private Algorithm internalJwtAlgorithm;
    private Algorithm externalJwtAlgorithm;
    private JWTVerifier externalJwtVerifier;

    @PostConstruct
    public void init() {
        this.internalJwtAlgorithm = Algorithm.HMAC256(internalJwtSecret);
        this.externalJwtAlgorithm = Algorithm.HMAC256(externalJwtSecret);
        this.externalJwtVerifier = JWT.require(externalJwtAlgorithm).withIssuer(issuer).build();
    }

    public JWTVerifier getJwtVerifier() {
        return this.externalJwtVerifier;
    }

    public String provideInternalJwt(String username) {
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(username)
                .withClaim(CommonConstants.ROLES_JWT_CLAIM, CommonConstants.ROLE_SYSTEM)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000L))
                .sign(internalJwtAlgorithm);
    }

    public Pair<String, String> provideExternalJwtPair(String username, Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String accessToken = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(username)
                .withClaim(CommonConstants.ACCESS_JWT_CLAIM, true)
                .withClaim(CommonConstants.ROLES_JWT_CLAIM, roles)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 50000000L))
                .sign(externalJwtAlgorithm);

        String refreshToken = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(username)
                .withClaim(CommonConstants.ACCESS_JWT_CLAIM, false)
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 100000000L))
                .sign(externalJwtAlgorithm);

        return Pair.of(accessToken, refreshToken);
    }

    @Override
    public DecodedJWT decode(String jwt) throws JWTVerificationException {
        return externalJwtVerifier.verify(jwt);
    }
}
