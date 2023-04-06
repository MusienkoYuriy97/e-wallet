package com.solbeg.wallet.service;

import com.solbeg.wallet.data.TestSecurityContext;
import com.solbeg.wallet.entity.User;
import com.solbeg.wallet.entity.Wallet;
import com.solbeg.wallet.exception.WalletException;
import com.solbeg.wallet.repository.WalletRepository;
import com.solbeg.wallet.utils.WalletNumberGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static com.solbeg.wallet.data.WalletTestData.DEFAULT_WALLET_NUMBER;
import static com.solbeg.wallet.data.WalletTestData.optionalWallet;
import static com.solbeg.wallet.data.WalletTestData.topUpRequest;
import static com.solbeg.wallet.data.WalletTestData.walletCreateRequest;
import static com.solbeg.wallet.data.WalletTestData.wallets;
import static com.solbeg.wallet.data.WalletTestData.withdrawRequest;
import static com.solbeg.wallet.data.WalletTestData.wrongTransferRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class WalletServiceTest {
    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private WalletNumberGenerator walletNumberGenerator;

    @Autowired
    private TestSecurityContext testSecurityContext;
    @Autowired
    private WalletService walletService;

    @Test
    void getWallets() {
        //mock
        var wallets = wallets();
        doReturn(wallets).when(walletRepository).findAllByUser(any(Pageable.class), any(User.class));
        testSecurityContext.build();

        //call method
        var getWalletsResponse = walletService.getWallets(PageRequest.of(0, 5));

        // assert
        assertNotNull(getWalletsResponse.getContent());
        assertEquals(wallets.getContent().size(), getWalletsResponse.getContent().size());
        assertEquals(wallets.getTotalElements(), getWalletsResponse.getTotalElements());

        var randomWallet = wallets.getContent().get(0);
        assertTrue(getWalletsResponse.stream().anyMatch((response -> response.getId().equals(randomWallet.getId())))
        );
    }

    @Test
    void createWallet() {
        //mock
        testSecurityContext.build();
        doReturn(DEFAULT_WALLET_NUMBER).when(walletNumberGenerator).generate();
        doReturn(null).when(walletRepository).save(any(Wallet.class));

        //call method
        walletService.createWallet(walletCreateRequest());

        // assert
        verify(walletNumberGenerator, times(1)).generate();
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void topUpWallet() {
        //mock
        testSecurityContext.build();
        var optionalWallet = optionalWallet(BigDecimal.valueOf(100));
        doReturn(optionalWallet)
                .when(walletRepository).findByWalletNumberAndUser(eq(DEFAULT_WALLET_NUMBER), any(User.class));

        //call method
        var topUpRequest = topUpRequest();
        var walletResponse = walletService.topUpWallet(topUpRequest);

        // assert
        assertNotNull(optionalWallet);
        assertTrue(optionalWallet.isPresent());
        assertEquals(topUpRequest.getWalletNumber(), walletResponse.getWalletNumber());
        assertEquals(topUpRequest.getCurrency(), walletResponse.getCurrency());
        assertEquals(optionalWallet.get().getAmount(), walletResponse.getAmount());
    }

    @Test
    void walletNotFound() {
        //mock
        testSecurityContext.build();
        doThrow(WalletException.class)
                .when(walletRepository).findByWalletNumberAndUser(eq(DEFAULT_WALLET_NUMBER), any(User.class));

        //call method
        assertThrows(WalletException.class, () -> walletService.withdrawMoney(withdrawRequest()));
        assertThrows(WalletException.class, () -> walletService.topUpWallet(topUpRequest()));
    }

    @Test
    void withdrawMoney() {
        //mock
        testSecurityContext.build();
        var optionalWallet = optionalWallet(BigDecimal.valueOf(50));
        doReturn(optionalWallet)
                .when(walletRepository).findByWalletNumberAndUser(eq(DEFAULT_WALLET_NUMBER), any(User.class));

        //call method
        var withdrawRequest = withdrawRequest();
        var walletResponse = walletService.withdrawMoney(withdrawRequest);

        // assert
        assertNotNull(optionalWallet);
        assertTrue(optionalWallet.isPresent());
        assertEquals(withdrawRequest.getWalletNumber(), walletResponse.getWalletNumber());
        assertEquals(withdrawRequest.getCurrency(), walletResponse.getCurrency());
        assertEquals(optionalWallet.get().getAmount(), walletResponse.getAmount());
    }

    @Test
    void withdrawMoneyMoneyNotEnough() {
        //mock
        testSecurityContext.build();
        var optionalWallet = optionalWallet(BigDecimal.valueOf(0));
        doReturn(optionalWallet)
                .when(walletRepository).findByWalletNumberAndUser(eq(DEFAULT_WALLET_NUMBER), any(User.class));

        //call method
        var withdrawRequest = withdrawRequest();

        // assert
        assertThrows(WalletException.class, () -> walletService.withdrawMoney(withdrawRequest));
    }

    @Test
    void transferToYourselfThrowException() {
        assertThrows(WalletException.class, () -> walletService.transfer(wrongTransferRequest()));
    }
}