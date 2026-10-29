package one.enchanter.rndevops.jester.playground.backend.controller;

/**
 * 
 * InvalidField
 * 
 * @param field
 * @param message
 * @param rejectedValue
 */
public record InvalidField(
        String field,
        String message,
        Object rejectedValue) {
};
