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
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.124521899+09:00", comments="Source field: items.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.124852811+09:00", comments="Source field: items.user_id")
    private Long userId;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.124945773+09:00", comments="Source field: items.name")
    private String name;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.125092048+09:00", comments="Source field: items.description")
    private String description;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.125523036+09:00", comments="Source field: items.created_at")
    private LocalDateTime createdAt;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", date="2026-11-12T16:36:19.12563284+09:00", comments="Source field: items.updated_at")
    private LocalDateTime updatedAt;
}