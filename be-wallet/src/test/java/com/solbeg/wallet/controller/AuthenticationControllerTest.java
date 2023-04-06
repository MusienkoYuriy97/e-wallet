package com.solbeg.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solbeg.wallet.dto.AuthenticationRequest;
import com.solbeg.wallet.dto.AuthenticationResponse;
import com.solbeg.wallet.dto.RegisterRequest;
import com.solbeg.wallet.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.solbeg.wallet.data.TestSecurityContext.DEFAULT_PASSWORD;
import static com.solbeg.wallet.data.TestSecurityContext.DEFAULT_USERNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @MockBean
    private AuthenticationService authenticationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() throws Exception {
        // when
        doReturn(AuthenticationResponse.builder().token("Bearer {token}").build()).when(authenticationService)
                .register(any(RegisterRequest.class));

        // assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(RegisterRequest.builder()
                                .username(DEFAULT_USERNAME)
                                .password(DEFAULT_PASSWORD)
                                .build()))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void authenticate() throws Exception {
        // when
        doReturn(AuthenticationResponse.builder().token("Bearer {token}").build()).when(authenticationService)
                .authenticate(any(AuthenticationRequest.class));

        // assert
        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(AuthenticationRequest.builder()
                                .username(DEFAULT_USERNAME)
                                .password(DEFAULT_PASSWORD)
                                .build()))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }
}