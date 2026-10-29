package one.enchanter.rndevops.jester.playground.backend.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.Utility;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.controller.ApiResponse;
import one.enchanter.rndevops.jester.playground.backend.controller.InvalidField;
import one.enchanter.rndevops.jester.playground.backend.exception.ForbiddenOperationException;
import one.enchanter.rndevops.jester.playground.backend.exception.NotFoundException;
import one.enchanter.rndevops.jester.playground.backend.exception.UnauthorizedOperationException;

/**
 * 
 * GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 1. @Valid によるバリデーションエラー
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {

        List<InvalidField> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new InvalidField(
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()))
                .toList();

        ApiResponse<Void> body = new ApiResponse<>(
                Severity.ERROR,
                "入力値に誤りがあります。",
                details);
        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.BAD_REQUEST);
    }

    // 2. 型不一致エラー
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        InvalidField detail = new InvalidField(
                ex.getName(),
                String.format("'%s' は正しい型ではありません。", ex.getValue()),
                ex.getValue());

        ApiResponse<Void> body = new ApiResponse<>(
                Severity.ERROR,
                "リクエストパラメータの型が正しくありません。",
                List.of(detail));

        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handle404(NoHandlerFoundException ex, HttpServletRequest req) {

        log.warn("404 Not Found: {}", req.getRequestURI());

        String errorId = Utility.generateErrorId("W");

        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR,
                ex.getMessage() + "（エラーID: " + errorId + "）");

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handlerは正常に実行されたが、必要なリソースが存在しない場合の例外。
     * Controller側で404を返すのではなく、この例外を使用すること。ControllerからはOKのレスポンスのみ返すようにすること。
     * メッセージは例外生成時に設定したメッセージをそのまま使用します。
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NotFound ex) {

        ApiResponse<Void> body = new ApiResponse<>(Severity.ERROR, ex.getMessage());

        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.NOT_FOUND);
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