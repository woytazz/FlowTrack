package io.flowtrack.shared.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.flowtrack.shared.security.interfaces.JwtDecoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtInternalDecoder implements JwtDecoder {

    @Value("${INTERNAL_JWT_SECRET:internal_jwt_secret}")
    private String internalJwtSecret;

    private JWTVerifier internalJwtVerifier;

    @PostConstruct
    public void init() {
        Algorithm internalJwtAlgorithm = Algorithm.HMAC256(internalJwtSecret);
        this.internalJwtVerifier = JWT.require(internalJwtAlgorithm).build();
    }

    @Override
    public DecodedJWT decode(String jwt) throws JWTVerificationException {
        return internalJwtVerifier.verify(jwt);
    }
}
