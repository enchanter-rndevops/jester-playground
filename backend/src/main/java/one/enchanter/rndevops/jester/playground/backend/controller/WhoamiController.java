
package one.enchanter.rndevops.jester.playground.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;
import one.enchanter.rndevops.jester.playground.backend.exception.NotFoundException;
import one.enchanter.rndevops.jester.playground.backend.service.ProfileService;

record WhoamiResponseData(
        String sub,
        String email,
        String name) {
}

@RestController
@RequestMapping("/api")
public class WhoamiController {

    @Autowired(required = false)
    private ProfileService profileService;

    @PostMapping("/v1/whoami")
    public ApiResponse<WhoamiResponseData> whoami(HttpServletRequest request) {
        // セッションからユーザー情報を取得（フィルターですでに認証済み・存在確認済みの前提）
        HttpSession session = request.getSession(false);

        var springSecurityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        String sub = springSecurityContext.getAuthentication().getPrincipal().toString();

        UsersEntity profile = profileService.getProfile(sub);

        if (profile == null) {

            // ここにログアウト処理を入れたほうがいいかもしれない。

            throw new NotFoundException("ユーザーが存在しません。");
        }
        return new ApiResponse<WhoamiResponseData>(Severity.SUCCESS, "OK",
                new WhoamiResponseData(profile.getSub(), profile.getEmail(), profile.getName()));

    }
}
