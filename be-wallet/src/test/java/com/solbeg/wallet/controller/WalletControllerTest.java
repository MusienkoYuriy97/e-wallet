package com.solbeg.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solbeg.wallet.dto.WalletActionRequest;
import com.solbeg.wallet.dto.WalletCreateRequest;
import com.solbeg.wallet.dto.WalletTransferRequest;
import com.solbeg.wallet.entity.User;
import com.solbeg.wallet.security.JwtService;
import com.solbeg.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.solbeg.wallet.data.TestSecurityContext.DEFAULT_USERNAME;
import static com.solbeg.wallet.data.WalletTestData.topUpRequest;
import static com.solbeg.wallet.data.WalletTestData.topUpResponse;
import static com.solbeg.wallet.data.WalletTestData.transferRequest;
import static com.solbeg.wallet.data.WalletTestData.walletCreateRequest;
import static com.solbeg.wallet.data.WalletTestData.walletResponses;
import static com.solbeg.wallet.data.WalletTestData.withdrawRequest;
import static com.solbeg.wallet.data.WalletTestData.withdrawResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;

    @MockBean
    private WalletService walletService;

    @Test
    void getWallets() throws Exception {
        // when
        String userJwtToken = jwtService.generateToken(User.builder().username(DEFAULT_USERNAME).build());
        doReturn(walletResponses()).when(walletService).getWallets(any(Pageable.class));

        // assert
        mockMvc.perform(get("/wallets?page=0&size=5")
                .header("Authorization", "Bearer " + userJwtToken)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createWallet() throws Exception {
        // when
        String userJwtToken = jwtService.generateToken(User.builder().username(DEFAULT_USERNAME).build());
        doNothing().when(walletService).createWallet(any(WalletCreateRequest.class));

        // assert
        mockMvc.perform(post("/wallets")
                .header("Authorization", "Bearer " + userJwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(walletCreateRequest()))
        )
                .andExpect(status().isCreated());
    }

    @Test
    void topUp() throws Exception {
        // when
        String userJwtToken = jwtService.generateToken(User.builder().username(DEFAULT_USERNAME).build());
        doReturn(topUpResponse()).when(walletService).topUpWallet(any(WalletActionRequest.class));

        // assert
        mockMvc.perform(put("/wallets/topUp")
                .header("Authorization", "Bearer " + userJwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(topUpRequest()))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void withdraw() throws Exception {
        // when
        String userJwtToken = jwtService.generateToken(User.builder().username(DEFAULT_USERNAME).build());
        doReturn(withdrawResponse()).when(walletService).withdrawMoney(any(WalletActionRequest.class));

        // assert
        mockMvc.perform(put("/wallets/withdraw")
                        .header("Authorization", "Bearer " + userJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(withdrawRequest()))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void transfer() throws Exception {
        // when
        String userJwtToken = jwtService.generateToken(User.builder().username(DEFAULT_USERNAME).build());
        doNothing().when(walletService).transfer(any(WalletTransferRequest.class));

        // assert
        mockMvc.perform(post("/wallets/transfer")
                        .header("Authorization", "Bearer " + userJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transferRequest()))
                )
                .andExpect(status().isOk());
    }
}