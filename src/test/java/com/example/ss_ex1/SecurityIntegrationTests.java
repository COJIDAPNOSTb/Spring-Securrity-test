package com.example.ss_ex1;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("root")
    public void accessAsAdmin_shouldBeFullyAllowed() throws Exception {
        mockMvc.perform(get("/test/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));

        mockMvc.perform(get("/test/good"))
                .andExpect(status().isOk())
                .andExpect(content().string("good"));

        mockMvc.perform(post("/test/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi"));
    }

    @Test
    @WithUserDetails("user1") // Предполагается, что у вас есть простой пользователь "user" в базе
    public void accessAsUser_shouldBePartiallyAllowed() throws Exception {
        mockMvc.perform(get("/test/hello"))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/test/good"))
                .andExpect(status().isOk())
                .andExpect(content().string("good"));

        mockMvc.perform(post("/test/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi"));
    }
}
