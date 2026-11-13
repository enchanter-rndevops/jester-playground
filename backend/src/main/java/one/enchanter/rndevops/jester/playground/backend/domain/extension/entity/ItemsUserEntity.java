package one.enchanter.rndevops.jester.playground.backend.domain.extension.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ItemsUserEntity
 */
@AllArgsConstructor
@Data
public class ItemsUserEntity {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String uid;
    private String userName;
}
