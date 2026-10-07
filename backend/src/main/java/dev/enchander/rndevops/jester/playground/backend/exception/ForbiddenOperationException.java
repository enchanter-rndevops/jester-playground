package dev.enchander.rndevops.jester.playground.backend.exception;

/**
 * Controllerから403を返す場合にこの例外をthrowする。
 * SpringSecurityの AccessDeniedException
 * は、Filterでキャッチされてしまい、/errorにリダイレクトされてしまうため、Controllerから403を返す場合はこの例外を使うこと
 */
public class ForbiddenOperationException extends RuntimeException {

    public ForbiddenOperationException(String message) {
        super(message);
    }

}
