package one.enchanter.rndevops.jester.playground.backend.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

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

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void testItemGet() throws Exception {

        when(sessionValidationInterceptor.preHandle(
                ArgumentMatchers.any(),
                ArgumentMatchers.any(),
                ArgumentMatchers.any())).thenReturn(true);

        LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime.of(2000, 1, 2, 0, 0);

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

}
