package one.enchanter.rndevops.jester.playground.backend.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * InvalidField
 *
 * @param field
 * @param message
 * @param rejectedValue
 */
@Getter
@RequiredArgsConstructor
public class InvalidField {

    private final String field;
    private final String message;
    private final Object rejectedValue;
}
