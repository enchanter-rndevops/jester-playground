package one.enchanter.rndevops.jester.playground.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import one.enchanter.rndevops.jester.playground.backend.constant.Severity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;
import one.enchanter.rndevops.jester.playground.backend.service.ItemService;

/**
 * 
 * ItemListRequestBody
 */
record ItemListRequestBody() {

}

record ItemListResponseData(
        List<ItemGetResponseData> items) {
}

/**
 * ItemLRegisterRequestBody
 * 
 * @param description
 */
record ItemRegisterRequestBody(
        String name,
        String description) {
}

record ItemRegisterResponseData(
        Long id) {
}

/**
 * 
 * ItemGetRequestBody
 * 
 * @param id
 */
record ItemGetRequestBody(String id) {
}

record ItemGetResponseData(
        Long id,
        LocalDateTime timestamp,
        String username,
        String name,
        String description) {
}

/**
 * 
 * ItemDeleteRequestBody
 * 
 * @param id
 */
record ItemDeleteRequestBody(String id) {
}

record ItemDeleteResponseData() {
}

/**
 * 
 * ItemUpdateRequestBody
 * 
 * @param id
 */
record ItemUpdateRequestBody(String id) {
}

record ItemUpdateResponseData() {
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
    public ApiResponse<ItemListResponseData> list(HttpServletRequest request, @RequestBody ItemListRequestBody body) {
        log.info("ItemController.list() called");

        var items = itemService.getItemList();
        var r = items.stream().map((e) -> {
            return new ItemGetResponseData(e.getId(), e.getCreatedAt(), e.getUserName(), e.getName(),
                    e.getDescription());
        }).toList();

        return new ApiResponse<ItemListResponseData>(Severity.SUCCESS, "OK", new ItemListResponseData(r));

    }

    @PostMapping("/register")
    public ApiResponse<ItemRegisterResponseData> register(HttpServletRequest request,
            @RequestBody ItemRegisterRequestBody body) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String sub = authentication.getPrincipal().toString();

        ItemsEntity newRow = itemService.registerItem(sub, body.name(), body.description());

        return new ApiResponse<ItemRegisterResponseData>(Severity.SUCCESS, "OK",
                new ItemRegisterResponseData(newRow.getId()));
    }

    @PostMapping("/get")
    public ApiResponse<ItemGetResponseData> get(@RequestBody ItemGetRequestBody body) {
        log.info("ItemController.get() called");

        var item = itemService.getItem(Long.parseLong(body.id()));

        var responseData = new ItemGetResponseData(item.getId(), item.getCreatedAt(), item.getUserName(),
                item.getName(),
                item.getDescription());

        return new ApiResponse<ItemGetResponseData>(Severity.SUCCESS, "OK", responseData);

    }

    @PostMapping("/delete")
    public ApiResponse<Void> delete(@RequestBody ItemDeleteRequestBody body) {

        itemService.deleteItem(Long.parseLong(body.id()));

        return new ApiResponse<Void>(Severity.SUCCESS, "OK");

    }

}
