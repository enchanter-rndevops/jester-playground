package one.enchanter.rndevops.jester.playground.backend.domain.generated.entity;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentitiesEntity {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.116686251+09:00", comments="Source field: user_identities.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.116985055+09:00", comments="Source field: user_identities.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.117135559+09:00", comments="Source field: user_identities.provider")
    private String provider;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.117315561+09:00", comments="Source field: user_identities.provided_user_id")
    private String providedUserId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.117435545+09:00", comments="Source field: user_identities.created_at")
    private LocalDateTime createdAt;
}