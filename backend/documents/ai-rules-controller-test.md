# Backend Controller Test Rules for AI

* テスト対象はバリデーションとレスポンスボディ。
* ServiceはMock化する。ControllerテストはAPI仕様テストであり、Serviceの振る舞い検証は禁止。
* @WebMvcTest を使用
* DBアクセス禁止
* MockMvcを利用
* JSONPathですべての項目を検証。
* 複数パターンがある場合や、nullなどの検証も行うこと。
* Controllerテストでは業務ロジックを確認しない
* @DisplayName でテストケースを記述してください。
* テストケースは以下を検討すること
  * 正常系
  * 0件
  * NULL
  * 境界値
* テストコードのみ出力すること
*  importを含めて出力すること
* 省略記法は使用しないこと
* TODOコメントは出力しないこと
* 全テストケースを実装すること
* JSONPathですべてのレスポンス項目を検証すること
* バリデーションエラーの場合、invalidFieldsの内容を検証すること。


テストコードのサンプル
```
package one.enchanter.rndevops.jester.playground.backend.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import one.enchanter.rndevops.jester.playground.backend.config.SecurityConfig;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.repository.UserSessionRepository;
import one.enchanter.rndevops.jester.playground.backend.interceptor.SessionValidationInterceptor;
import one.enchanter.rndevops.jester.playground.backend.service.ItemService;

@WebMvcTest(controllers = ItemController.class)
@ActiveProfiles("test")
@Import({ SecurityConfig.class })
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private UserSessionRepository userSessionRepository;

    @MockitoBean
    private SessionValidationInterceptor sessionValidationInterceptor;

    /**
     * 
     */
    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    @DisplayName("正常系：アイテム取得APIが正しく動作すること")
    void testItemGet() throws Exception {

        when(sessionValidationInterceptor.preHandle(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any())).thenReturn(true);

         when(itemService.getItem(0))
                .thenReturn(new ItemsUserEntity(0L, "itemname", "description",
                        LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 2, 0, 0),
                        "user_sub",
                        "username"));

        String jsonBody = "{\"id\": 0}";

        ResultActions rs = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/item/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody));

        rs.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.severity").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invalidFields").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("username"));
    }

    // バリデーションエラーやエラー系のテストを追加すること。

}
```

```
package one.enchanter.rndevops.jester.playground.backend.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NotFoundException ex) {

        ApiResponse<Void> body = new ApiResponse<>(Severity.ERROR, ex.getMessage());

        return new ResponseEntity<ApiResponse<Void>>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedOperationException(UnauthorizedOperationException ex,
            HttpServletRequest request) {

        String errorId = Utility.generateErrorId("E");
        log.warn("[{}] アクセス拒否: {}", errorId, ex.getMessage());
        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR, "認証されていません（エラーID: " + errorId + "）");
        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbiddenOperationException(ForbiddenOperationException ex,
            HttpServletRequest request) {

        String errorId = Utility.generateErrorId("E");
        log.warn("[{}] アクセス拒否（権限不足）: {}", errorId, ex.getMessage());
        ApiResponse<Void> responseBody = new ApiResponse<>(Severity.ERROR, "権限がありません（エラーID: " + errorId + "）");

        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex, HttpServletRequest request) {

        String errorId = Utility.generateErrorId("E");

        log.error("[{}] 予期しない例外が発生しました。{}", errorId, ex.getMessage(), ex);

        ApiResponse<Void> responseBody = new ApiResponse<Void>(Severity.ERROR, errorId);

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
```

```
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
```

```
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
```