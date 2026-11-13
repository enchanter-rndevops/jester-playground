package one.enchanter.rndevops.jester.playground.backend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// SecurityContextHolder の Principal に保存する値。
@Getter
@RequiredArgsConstructor
public class UserPrincipal {
    private final Long userId;
    private final String provider;
    private final String providedUserId;

}
