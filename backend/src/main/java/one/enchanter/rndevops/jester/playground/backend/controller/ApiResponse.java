package one.enchanter.rndevops.jester.playground.backend.controller;

import java.util.List;

import one.enchanter.rndevops.jester.playground.backend.constant.Severity;

/**
 * レスポンスのベース。
 * severityとmessageのみのレスポンスはこれを直接使用する。
 * 使用例。
 * new BaseResponse<Void>( Severity.OK, "OK");
 * 
 * @param severity
 * @param message
 * @param data
 * @param invalid
 */
public record ApiResponse<T>(
        Severity severity,
        String message,
        T data,
        List<InvalidField> invalidFields) {

    public ApiResponse(Severity severity, String message) {
        this(severity, message, null, List.of());
    }

    public ApiResponse(Severity severity, String message, T data) {
        this(severity, message, data, List.of());
    }

    public ApiResponse(Severity severity, String message, List<InvalidField> invalidFields) {
        this(severity, message, null, invalidFields);
    }

}
