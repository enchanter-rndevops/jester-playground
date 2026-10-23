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
    Long id;
    String name;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String sub;
    String userName;
}
