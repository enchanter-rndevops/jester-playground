package one.enchanter.rndevops.jester.playground.backend.domain.extension.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserIdentityEntity {
    private Long userId;
    private String email;
    private String name;
}
