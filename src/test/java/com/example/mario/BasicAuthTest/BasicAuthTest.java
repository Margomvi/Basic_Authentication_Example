package com.example.mario.BasicAuthTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BasicAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void accesoConCredencialesCorrectasDevuelve200() throws Exception {
        String authHeader = "Basic " + Base64.getEncoder().encodeToString("mario:123456".getBytes());

        mockMvc.perform(get("/")
                        .header("Authorization", authHeader))
                .andExpect(status().isOk()); // Espero un código 200
    }

    @Test
    void accesoConCredencialesIncorrectasDevuelve401() throws Exception {
        mockMvc.perform(get("/")
                        .with(httpBasic("mario", "wrong")))
                .andExpect(status().isUnauthorized());
    }
}

