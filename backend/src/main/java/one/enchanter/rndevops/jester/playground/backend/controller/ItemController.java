package one.enchanter.rndevops.jester.playground.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.UserPrincipal;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;
import one.enchanter.rndevops.jester.playground.backend.exception.NotFoundException;
import one.enchanter.rndevops.jester.playground.backend.service.ItemService;

/**
 *
 * ItemListRequestBody
 */
@Data
@AllArgsConstructor
class ItemListRequestBody {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemListResponseData {
    private List<ItemGetResponseData> items;
}

/**
 * ItemLRegisterRequestBody
 *
 * @param description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemRegisterRequestBody {

    @NotEmpty
    @Size(min = 1, max = 50)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemRegisterResponseData {
    private Long id;
}

/**
 *
 * ItemGetRequestBody
 *
 * @param id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemGetRequestBody {
    private String id;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemGetResponseData {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String username;
}

/**
 *
 * ItemDeleteRequestBody
 *
 * @param id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemDeleteRequestBody {
    @NotEmpty
    @Size(min = 1, max = 50)
    private String id;
}

class ItemDeleteResponseData {
}

/**
 *
 * ItemUpdateRequestBody
 *
 * @param id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
class ItemUpdateRequestBody {

    @NotEmpty
    @Size(min = 1, max = 50)
    private String id;
}

class ItemUpdateResponseData {
}

/**
 *
 * ItemController
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/list")
    public ApiResponse<ItemListResponseData> list(HttpServletRequest request) {
        log.info("ItemController.list() called");

        var items = itemService.getItemList();
        var r = items
                .stream()
                .map(e -> {
                    return new ItemGetResponseData(
                            e.getId(),
                            e.getName(),
                            e.getDescription(),
                            e.getCreatedAt(),
                            e.getUserName());
                })
                .toList();

        return new ApiResponse<ItemListResponseData>(
                Severity.SUCCESS,
                "OK",
                new ItemListResponseData(r));
    }

    @PostMapping("/register")
    public ApiResponse<ItemRegisterResponseData> register(
            HttpServletRequest request,
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ItemRegisterRequestBody body) {

        Long userId = principal.getUserId();

        ItemsEntity newRow = itemService.registerItem(
                userId,
                body.getName(),
                body.getDescription());

        return new ApiResponse<ItemRegisterResponseData>(
                Severity.SUCCESS,
                "OK",
                new ItemRegisterResponseData(newRow.getId()));
    }

    /**
     *
     *
     */
    @PostMapping("/get")
    public ApiResponse<ItemGetResponseData> get(
            @Valid @RequestBody ItemGetRequestBody body) {
        log.info("ItemController.get() called");

        var item = itemService.getItem(Long.parseLong(body.getId()));

        if (item == null) {
            throw new NotFoundException("アイテムが存在ません。");
            // この例外をthrowすると、GlobalExceptionHandlerで以下のレスポンスと同じものを返すようになっています。
            // こうすることで、以下のようなメリットがある。
            // * Controllerでは（特別な要件がない場合）、戻り値に ResponseEntityを使わず、ApiResponse を直接使用することができる。
            // * Controllerはokだけ。
            // return new ApiResponse<ItemGetResponseData>(Severity.ERROR, "アイテムが存在しません。");
        } else {
            var responseData = new ItemGetResponseData(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getCreatedAt(),
                    item.getUserName());
            return new ApiResponse<ItemGetResponseData>(
                    Severity.SUCCESS,
                    "OK",
                    responseData);
        }
    }

    @PostMapping("/delete")
    public ApiResponse<Void> delete(
            @Valid @RequestBody ItemDeleteRequestBody body) {
        itemService.deleteItem(Long.parseLong(body.getId()));

        return new ApiResponse<Void>(Severity.SUCCESS, "OK");
    }
}
