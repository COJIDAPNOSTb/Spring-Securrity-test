package com.example.ss_ex1;

import com.example.ss_ex1.config.AppConfig;
import com.example.ss_ex1.controller.TestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import(AppConfig.class)
public class SecurityTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void accessUnauthenticated_shouldBeDenied() throws Exception {

        mockMvc.perform(get("/test/hello"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/test/good"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/test/hello"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user1", password = "qwe", roles = {"USER"})
    public void accessAsUser_shouldBePartiallyAllowed() throws Exception {
        // Тест доступа с ролью USER

        // GET /test/hello требует роли ADMIN, должен быть запрещен
        mockMvc.perform(get("/test/hello"))
                .andExpect(status().isForbidden());

        // GET /test/good доступен всем аутентифицированным пользователям
        mockMvc.perform(get("/test/good"))
                .andExpect(status().isOk()).andExpect(content().string("good"));

        // POST /test/hello доступен всем аутентифицированным пользователям
        mockMvc.perform(post("/test/hello"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "root", password = "123", roles = {"ADMIN"})
    public void accessAsAdmin_shouldBeFullyAllowed() throws Exception {
        // Тест доступа с ролью ADMIN

        // Все эндпоинты должны быть доступны для админа
        mockMvc.perform(get("/test/hello"))
                .andExpect(status().isOk());


        mockMvc.perform(get("/test/good"))
                .andExpect(status().isOk());


        mockMvc.perform(post("/test/hello"))
                .andExpect(status().isForbidden());

    }

}