package one.enchanter.rndevops.jester.playground.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import one.enchanter.rndevops.jester.playground.backend.config.SecurityConfig;
import one.enchanter.rndevops.jester.playground.backend.domain.extension.entity.ItemsUserEntity;
import one.enchanter.rndevops.jester.playground.backend.domain.generated.entity.ItemsEntity;
import one.enchanter.rndevops.jester.playground.backend.service.ItemService;
import one.enchanter.rndevops.jester.playground.backend.test.WithUserPrincipal;

@WebMvcTest(controllers = ItemController.class)
@ActiveProfiles("test")
@Import({ SecurityConfig.class })
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @BeforeEach
    void setup() throws Exception {

    }

    @Test
    @WithUserPrincipal(userId = 1L, provider = "cognito", providedUserId = "cognito-sub")
    @DisplayName("正常系：アイテム一覧取得APIが成功すること")
    void testListSuccess() throws Exception {

        when(itemService.getItemList()).thenReturn(List.of(
                new ItemsUserEntity(1L, "item1", "desc1", LocalDateTime.of(2024, 1, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 2, 0, 0), "uid1", "user1")));

        mockMvc.perform(
                post("/api/v1/item/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.severity").value("success"))
                .andExpect(jsonPath("$.message").value("OK"))

                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.items.length()").value(1))

                .andExpect(jsonPath("$.data.items[0].id").value(1))
                .andExpect(jsonPath("$.data.items[0].name").value("item1"))
                .andExpect(jsonPath("$.data.items[0].description").value("desc1"))
                .andExpect(jsonPath("$.data.items[0].createdAt")
                        .value("2024-01-01T00:00:00"))
                .andExpect(jsonPath("$.data.items[0].username")
                        .value("user1"));
    }

    @Test
    @WithUserPrincipal(userId = 1L, provider = "cognito", providedUserId = "cognito-sub")
    @DisplayName("0件：アイテム一覧取得APIで0件が返却されること")
    void testListEmpty() throws Exception {

        when(itemService.getItemList()).thenReturn(Collections.emptyList());

        mockMvc.perform(
                post("/api/v1/item/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.severity").value("success"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.items.length()").value(0));
    }

    @Test
    @WithUserPrincipal(userId = 1L, provider = "cognito", providedUserId = "cognito-sub")
    @DisplayName("正常系：アイテム取得APIが成功すること")
    void testGetSuccess() throws Exception {

        when(itemService.getItem(1L))
                .thenReturn(new ItemsUserEntity(
                        1L,
                        "item1",
                        "desc1",
                        LocalDateTime.of(2024, 1, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 2, 0, 0),
                        "uid1",
                        "user1"));

        String json = """
                {
                    "id":"1"
                }
                """;

        mockMvc.perform(
                post("/api/v1/item/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.severity").value("success"))
                .andExpect(jsonPath("$.message").value("OK"))

                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("item1"))
                .andExpect(jsonPath("$.data.description").value("desc1"))
                .andExpect(jsonPath("$.data.createdAt")
                        .value("2024-01-01T00:00:00"))
                .andExpect(jsonPath("$.data.username").value("user1"));
    }

    @Test
    @WithUserPrincipal(userId = 1L, provider = "cognito", providedUserId = "cognito-sub")
    void testGetNotFound() throws Exception {

        when(itemService.getItem(999L))
                .thenReturn(null);

        String json = """
                {
                    "id":"999"
                }
                """;

        mockMvc.perform(
                post("/api/v1/item/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserPrincipal(userId = 1L, provider = "cognito", providedUserId = "cognito-sub")
    @DisplayName("正常系：アイテム登録APIが成功すること")
    void testRegisterSuccess() throws Exception {

        ItemsEntity entity = new ItemsEntity();
        entity.setId(100L);

        when(itemService.registerItem(any(), any(), any()))
                .thenReturn(entity);

        String json = """
                {
                    "name":"name1",
                    "description":"description1"
                }
                """;

        mockMvc.perform(
                post("/api/v1/item/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.severity").value("success"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.id").value(100));
    }

    @Test
    @DisplayName("認証エラー：未認証の場合は401エラーとなること")
    void testRegisterUnauthorized() throws Exception {

        String json = """
                {
                    "name":"name1",
                    "description":"description1"
                }
                """;

        mockMvc.perform(
                post("/api/v1/item/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    @DisplayName("正常系：アイテム削除APIが成功すること")
    void testDeleteSuccess() throws Exception {

        String json = """
                {
                    "id":"1"
                }
                """;

        mockMvc.perform(
                post("/api/v1/item/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.severity").value("success"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}