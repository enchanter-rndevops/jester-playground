package one.enchanter.rndevops.jester.playground.backend.domain.generated.entity;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionsEntity {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.863162627+09:00", comments="Source field: sessions.id")
    private String id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.863532568+09:00", comments="Source field: sessions.user_sub")
    private String userSub;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.863898802+09:00", comments="Source field: sessions.created_at")
    private LocalDateTime createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.864293772+09:00", comments="Source field: sessions.expires_at")
    private LocalDateTime expiresAt;
}