package one.enchanter.rndevops.jester.playground.backend.domain.generated.entity;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsEntity {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.868408553+09:00", comments="Source field: items.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.868661466+09:00", comments="Source field: items.user_sub")
    private String userSub;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.86882752+09:00", comments="Source field: items.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869056205+09:00", comments="Source field: items.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869178444+09:00", comments="Source field: items.created_at")
    private LocalDateTime createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-10-23T17:14:36.869270293+09:00", comments="Source field: items.updated_at")
    private LocalDateTime updatedAt;
}