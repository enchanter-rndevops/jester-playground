package one.enchanter.rndevops.jester.playground.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controllerのサンプルコード。
 * このプロジェクトは、APIしかないため、Controllerという名前は適切ではない。
 * 本来であれば、endpoint などにするべきだと思う。
 * 歴史的経緯のため、Controllerとしておく。アノテーションの名前も、@RestController となっていることだし。
 * 
 * Controllerで行うこと。これ以外の処理を行わないこと。
 * * パラメータのバリデーション
 * * Serviceの呼び出し。
 * * Service呼び出し結果からのレスポンスボディの生成。
 */

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    // ここでは直接Mapを生成していますが、実際は ApiResponse<T>を使う。
    @GetMapping("/hello")
    public Map<String, Object> hello() {
        return Map.of(
                "message", "Hello, Spring Boot!",
                "status", "ok",
                "timestamp", System.currentTimeMillis());
    }
}
