package com.solbeg.wallet.utils;

import com.solbeg.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class WalletNumberGeneratorTest {
    @Autowired
    private WalletNumberGenerator walletNumberGenerator;
    @MockBean
    private WalletRepository walletRepository;

    @Test
    void generate() {
        //when
        doReturn(false).when(walletRepository).existsByWalletNumber(anyString());
        //do
        var generate = walletNumberGenerator.generate();
        //then
        assertNotNull(generate);
        assertEquals(7, generate.length());
    }
}