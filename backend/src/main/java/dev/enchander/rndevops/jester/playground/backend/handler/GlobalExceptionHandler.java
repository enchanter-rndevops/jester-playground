package dev.enchander.rndevops.jester.playground.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import dev.enchander.rndevops.jester.playground.backend.Utility;
import dev.enchander.rndevops.jester.playground.backend.constant.Severity;
import dev.enchander.rndevops.jester.playground.backend.exception.ForbiddenOperationException;
import dev.enchander.rndevops.jester.playground.backend.exception.UnauthorizedOperationException;
import dev.enchander.rndevops.jester.playground.backend.repository.records.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handle404(NoHandlerFoundException ex, HttpServletRequest req) {

        // ログ出力（404 の原因が分かる）
        log.warn("404 Not Found: {}", req.getRequestURI());

        String errorId = Utility.generateErrorId("W");

        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.WARNING,
                ex.getMessage() + "（エラーID: " + errorId + "）");

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    // ResponseStatus例外ハンドラ。
    // この例外は、SpringBootがthrowするものでなく、自分でthrowする。
    // @ExceptionHandler(ResponseStatusException.class)
    // public ResponseEntity<ApiResponse<Void>>
    // handleResponseStatusException(ResponseStatusException ex,
    // HttpServletRequest request) {
    // String errorId = Utility.generateErrorId("E");

    // log.warn("[{}] ステータス例外発生: {}", errorId, ex.getReason(), ex.getMessage());

    // ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR,
    // ex.getReason() + "（エラーID: " + errorId + "）");

    // return new ResponseEntity<>(responseBody, ex.getStatusCode());

    // }

    // Controllerからの未認証(401)のキャッチ。
    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedOperationException(UnauthorizedOperationException ex,
            HttpServletRequest request) {

        String errorId = Utility.generateErrorId("E");
        log.warn("[{}] アクセス拒否: {}", errorId, ex.getMessage());
        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR, "認証されていません（エラーID: " + errorId + "）");
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);

    }

    // Controllerからの未認可（403）のキャッチ
    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbiddenOperationException(ForbiddenOperationException ex,
            HttpServletRequest request) {

        String errorId = Utility.generateErrorId("E");
        log.warn("[{}] アクセス拒否（権限不足）: {}", errorId, ex.getMessage());
        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR, "権限がありません（エラーID: " + errorId + "）");

        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);

    }

    // 予期せぬすべての例外（Exception）をキャッチして500を返す
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest request) {

        // 例外時にエラーIDを生成します。
        // このエラーIDは、 ログと画面（厳密にいえば、画面ではなくレスポンス）の両方に同じ値を出力します。
        // これにより、画面に表示されるエラーIDでログを検索することで、場所を特定しやすくなります。
        // エラーコードなんて表示しても、それだけではぜんぜんわからない。結局はログを見ることになるので、このようにエラーから場所を特定できるようにしてみました。
        // UUIDの先頭8桁しか使用していないので、もしかすると、同じIDが生成されることもあるかもしれません。
        // 数字とアルファベット大文字で8文字なので、可能性は限りなく低いし、同じIDが生成されてもちょっと検索がめんどくさくなるくらいで、大した問題にはならないでしょう。
        // 8文字ぜんぶわからくても、たぶん4文字くらいわかればじゅうぶんに探せると思うし。
        String errorId = Utility.generateErrorId("E");

        log.error("[{}] 予期しない例外が発生しました。{}", errorId, ex.getMessage(), ex);

        ApiResponse<Void> responseBody = new ApiResponse<Void>(Severity.ERROR, errorId);

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}