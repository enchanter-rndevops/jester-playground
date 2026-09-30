
package dev.enchander.rndevops.jester.playground.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.enchander.rndevops.jester.playground.backend.constant.Severity;
import dev.enchander.rndevops.jester.playground.backend.domain.generated.entity.Users;
import dev.enchander.rndevops.jester.playground.backend.repository.records.ApiResponse;
import dev.enchander.rndevops.jester.playground.backend.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class WhoamiController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/v1/whoami")
    public ApiResponse<?> whoami(HttpServletRequest request) {
        // セッションからユーザー情報を取得（フィルターですでに認証済み・存在確認済みの前提）
        HttpSession session = request.getSession(false);

        var springSecurityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        String sub = springSecurityContext.getAuthentication().getPrincipal().toString();

        Users profile = profileService.getProfile(sub);

        // profileを直接bodyにしているけど、こんなことはやらない方がいいと思います。
        return new ApiResponse<>(Severity.SUCCESS, "Success", profile);
    }
}
