package one.enchanter.rndevops.jester.playground.backend.domain.extension.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

// 認可はありません。
// 認可が必要であれば、role関連のテーブルを追加し、ここにroleを追加する必要があります。
@Data
@AllArgsConstructor
public class UserSessionEntity {
    private Long userId;
    private String sessionId;
    private String provider;
    private String providedUserId;
}
