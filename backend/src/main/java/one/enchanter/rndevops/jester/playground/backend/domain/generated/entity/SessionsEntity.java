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
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.120979801+09:00", comments="Source field: sessions.id")
    private String id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.121166306+09:00", comments="Source field: sessions.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.121301861+09:00", comments="Source field: sessions.created_at")
    private LocalDateTime createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.121420382+09:00", comments="Source field: sessions.expires_at")
    private LocalDateTime expiresAt;
}