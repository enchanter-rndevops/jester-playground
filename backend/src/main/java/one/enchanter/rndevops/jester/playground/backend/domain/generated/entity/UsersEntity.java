package one.enchanter.rndevops.jester.playground.backend.domain.generated.entity;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.092832144+09:00", comments="Source field: users.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.095105226+09:00", comments="Source field: users.uid")
    private String uid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.095598811+09:00", comments="Source field: users.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.095748132+09:00", comments="Source field: users.email")
    private String email;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.09600808+09:00", comments="Source field: users.created_at")
    private LocalDateTime createdAt;
}