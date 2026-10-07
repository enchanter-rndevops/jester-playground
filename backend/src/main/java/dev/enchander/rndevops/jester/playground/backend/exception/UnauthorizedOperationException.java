package dev.enchander.rndevops.jester.playground.backend.exception;

/**
 * Controllerから401を返す場合にこの例外をthrowする。
 * SpringSecurityの AuthenticationException
 * は、Filterでキャッチされてしまい、/errorにリダイレクトされてしまうため、Controllerから401を返す場合はこの例外を使うこと。
 */
public class UnauthorizedOperationException extends RuntimeException {

    public UnauthorizedOperationException(String message) {
        super(message);
    }

}
