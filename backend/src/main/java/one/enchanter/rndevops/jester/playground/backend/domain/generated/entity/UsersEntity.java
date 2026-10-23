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
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.836403541+09:00", comments="Source field: users.sub")
    private String sub;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.838756778+09:00", comments="Source field: users.email")
    private String email;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.838905678+09:00", comments="Source field: users.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.839106991+09:00", comments="Source field: users.created_at")
    private LocalDateTime createdAt;
}