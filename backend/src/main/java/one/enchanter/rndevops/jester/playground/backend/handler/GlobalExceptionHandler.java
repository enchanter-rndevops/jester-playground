package one.enchanter.rndevops.jester.playground.backend.handler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    /**
     * @Valid によるバリデーションエラー
     * @param ex
     * @return
     */
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

    /*
     * 型変換エラー時のタイプによるメッセージ変換Map
     * 型が増えた場合、ここに追加すること。
     */
    private static final Map<Class<?>, String> TYPE_NAMES = Map.ofEntries(
            Map.entry(byte.class, "整数"),
            Map.entry(Byte.class, "整数"),
            Map.entry(short.class, "整数"),
            Map.entry(Short.class, "整数"),
            Map.entry(int.class, "整数"),
            Map.entry(Integer.class, "整数"),
            Map.entry(long.class, "整数"),
            Map.entry(Long.class, "整数"),
            Map.entry(float.class, "小数"),
            Map.entry(Float.class, "小数"),
            Map.entry(double.class, "小数"),
            Map.entry(Double.class, "小数"),
            Map.entry(boolean.class, "真偽値(true/false)"),
            Map.entry(Boolean.class, "真偽値(true/false)"),
            Map.entry(String.class, "文字列"),
            Map.entry(LocalDate.class, "日付(yyyy-MM-dd)"),
            Map.entry(LocalDateTime.class, "日時(yyyy-MM-dd HH:mm:ss)"),
            Map.entry(BigDecimal.class, "数値"),
            Map.entry(UUID.class, "UUID"));

    /**
     * 型不一致エラー
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        Class<?> requiredType = ex.getRequiredType();

        String t = TYPE_NAMES.getOrDefault(requiredType, "正しい値");

        InvalidField detail = new InvalidField(
                ex.getName(),
                String.format("%s を入力してください。", t),
                ex.getValue());

        ApiResponse<Void> body = new ApiResponse<>(
                Severity.ERROR,
                "リクエストパラメータの型が正しくありません。",
                List.of(detail));

        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * リクエストボディ形式エラー(JSONパースエラーなど)。
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalMessageException(HttpMessageNotReadableException ex) {

        ApiResponse<Void> body = new ApiResponse<>(
                Severity.ERROR,
                "リクエストボディの型が正しくありません。",
                List.of());

        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.BAD_REQUEST);

    }

    /**
     * Controllerが存在しない場合のエラー。
     * メッセージは修正したほうがいいかもしれない。
     * 
     * @param ex
     * @param req
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handle404(NoHandlerFoundException ex, HttpServletRequest req) {

        log.warn("404 Not Found: {}", req.getRequestURI());

        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR, ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    /**
     * Handlerは正常に実行されたが、必要なリソースが存在しない場合の例外。
     * Controller側で404を返すのではなく、この例外を使用すること。ControllerからはOKのレスポンスのみ返すようにすること。
     * メッセージは例外生成時に設定したメッセージをそのまま使用します。
     * 
     * 必要であれば、例外をthrowする側でエラーIDをメッセージに含めること。
     * String errorId = Utility.generateErrorId("E");
     * で簡単に生成できます。
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NotFoundException ex) {

        ApiResponse<Void> body = new ApiResponse<>(Severity.ERROR, ex.getMessage());

        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.NOT_FOUND);
    }

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