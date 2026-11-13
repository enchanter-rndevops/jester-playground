package one.enchanter.rndevops.jester.playground.backend.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * CognitoDummyController
 * 
 * テスト用のダミーCognito.
 * パスワードがそのままsubになります。
 * これを使用しなくても、クライアント側でIDTokenを作成し、session/login を呼んでも同じ。
 * 
 * curlでの実行例。
 * @formatter:off
 * ```
 * curl -X POST http://localhost:8080/api/v1/cognito/login \
 * -H "Content-Type: application/json" \
 * -d '{
 *   "authFlow": "USER_PASSWORD_AUTH",
 *   "clientId": "dummy-client-id",
 *   "authParameters": {
 *     "USERNAME": "test-user",
 *     "PASSWORD": "my-secret-password"
 *   }
 * }'
 * '''
 * @formatter:on
 */
@Profile({ "local", "test" })
@RestController
@RequestMapping("api/v1/cognito")
public class CognitoDummyController {

    // Cognitoの InitiateAuth リクエスト構造
    public static class InitiateAuthRequest {
        private String authFlow;
        private String clientId;
        private Map<String, String> authParameters;

        public String getAuthFlow() {
            return authFlow;
        }

        public void setAuthFlow(String authFlow) {
            this.authFlow = authFlow;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public Map<String, String> getAuthParameters() {
            return authParameters;
        }

        public void setAuthParameters(Map<String, String> authParameters) {
            this.authParameters = authParameters;
        }
    }

    // Cognitoの InitiateAuth レスポンス構造
    public static class InitiateAuthResponse {
        private AuthenticationResult authenticationResult;
        private Map<String, String> challengeParameters = new HashMap<>();

        public AuthenticationResult getAuthenticationResult() {
            return authenticationResult;
        }

        public void setAuthenticationResult(AuthenticationResult authenticationResult) {
            this.authenticationResult = authenticationResult;
        }

        public Map<String, String> getChallengeParameters() {
            return challengeParameters;
        }

        public void setChallengeParameters(Map<String, String> challengeParameters) {
            this.challengeParameters = challengeParameters;
        }
    }

    public static class AuthenticationResult {
        private String accessToken;
        private Integer expiresIn = 3600;
        private String idToken;
        private String refreshToken;
        private String tokenType = "Bearer";

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Integer getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(Integer expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<InitiateAuthResponse> initiateAuth(@RequestBody InitiateAuthRequest request) {
        String username = "user";
        String password = "password";

        if (request.getAuthParameters() != null) {
            username = request.getAuthParameters().getOrDefault("USERNAME", "default-user");
            password = request.getAuthParameters().getOrDefault("PASSWORD", "default-password");
        }

        // パスワードを sub として含めたダミーのIDトークン (JWT形式: header.payload.signature)
        String payloadJson = String.format("{\"sub\":\"%s\",\"cognito:username\":\"%s\"}", password, username);
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());
        String dummyIdToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9." + encodedPayload + ".dummy-signature";

        AuthenticationResult authResult = new AuthenticationResult();
        authResult.setAccessToken("dummy-access-token-" + username);
        authResult.setIdToken(dummyIdToken);
        authResult.setRefreshToken("dummy-refresh-token-" + username);

        InitiateAuthResponse response = new InitiateAuthResponse();
        response.setAuthenticationResult(authResult);

        return ResponseEntity.ok(response);
    }
}