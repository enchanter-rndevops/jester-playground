package dev.enchander.rndevops.jester.playground.backend.domain.extension.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSessionEntity {
    private String sub;
    private String email;
    private String name;
}
