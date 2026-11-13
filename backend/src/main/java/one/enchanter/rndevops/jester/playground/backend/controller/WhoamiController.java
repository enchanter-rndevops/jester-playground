
package one.enchanter.rndevops.jester.playground.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import one.enchanter.rndevops.jester.playground.backend.UserPrincipal;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.UsersEntity;
import one.enchanter.rndevops.jester.playground.backend.exception.NotFoundException;
import one.enchanter.rndevops.jester.playground.backend.service.ProfileService;

@Getter
@RequiredArgsConstructor
class WhoamiResponseData {
    private final String uid;
    private final String email;
    private final String name;
}

@RestController
@RequestMapping("/api")
public class WhoamiController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/v1/whoami")
    public ApiResponse<WhoamiResponseData> whoami(@AuthenticationPrincipal UserPrincipal principal) {

        // ここでnullチェックを行わない。SecurityContextが存在しない場合、interceptorで未認証を返すため、ここでprincipalがnullになることはない。
        Long userId = principal.getUserId();

        UsersEntity profile = profileService.getProfile(userId);

        if (profile == null) {
            throw new NotFoundException("ユーザーが存在しません。");
        }
        return new ApiResponse<WhoamiResponseData>(Severity.SUCCESS, "OK",
                new WhoamiResponseData(profile.getUid(), profile.getEmail(), profile.getName()));

    }
}
