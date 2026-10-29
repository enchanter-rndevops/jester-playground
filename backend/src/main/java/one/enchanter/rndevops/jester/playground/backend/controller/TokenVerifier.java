package one.enchanter.rndevops.jester.playground.backend.controller;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.exception.ForbiddenOperationException;
import one.enchanter.rndevops.jester.playground.backend.properties.CognitoProperties;

public interface TokenVerifier {

    public Map<String, Object> verify(String idToken);

}

/**
 * IDTokenを検証する。
 */
@Component
@Slf4j
@Profile({ "aws" })
class CognitoTokenVerifier implements TokenVerifier {

    private final CognitoProperties cognitoProperties;

    private final JwtDecoder jwtDecoder;

    public CognitoTokenVerifier(CognitoProperties cognitoProperties) {
        this.cognitoProperties = cognitoProperties;
        this.jwtDecoder = JwtDecoders.fromIssuerLocation(cognitoProperties.issuer());
    }

    public Map<String, Object> verify(String idToken) {

        Jwt jwt = jwtDecoder.decode(idToken);

        log.debug("JWT Claims: " + jwt.getClaims());

        // iss, aud(clientid)の検証をここで行う。
        if (!cognitoProperties.audience().equals(jwt.getAudience().getFirst())) {
            throw new ForbiddenOperationException("Invalid token");
        }

        if (!cognitoProperties.issuer().equals(jwt.getIssuer().toString())) {
            throw new ForbiddenOperationException("Invalid token");
        }

        return jwt.getClaims();
    }
}

@Component
@Profile({ "local", "test" })
class LocalTokenVerifier implements TokenVerifier {

    public Map<String, Object> verify(String idToken) {
        try {
            // 署名検証なしで JWT をパース
            // SignedJWT jwt = SignedJWT.parse(idToken);
            JWT jwt = JWTParser.parse(idToken);

            if (jwt == null) {
                throw new RuntimeException("Invalid local token");
            }
            return jwt.getJWTClaimsSet().getClaims();
        } catch (Exception e) {
            throw new RuntimeException("Invalid local token", e);
        }
    }
}
