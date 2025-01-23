package io.flowtrack.shared.security.interfaces;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface JwtDecoder {
    DecodedJWT decode(String jwt) throws JWTVerificationException;
}
