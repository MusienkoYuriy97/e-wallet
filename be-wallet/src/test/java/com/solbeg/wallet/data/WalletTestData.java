package com.solbeg.wallet.data;

import com.solbeg.wallet.dto.WalletActionRequest;
import com.solbeg.wallet.dto.WalletCreateRequest;
import com.solbeg.wallet.dto.WalletResponse;
import com.solbeg.wallet.dto.WalletTransferRequest;
import com.solbeg.wallet.entity.Wallet;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@UtilityClass
public class WalletTestData {
    public static final String DEFAULT_WALLET_NUMBER = "a111111";

    public static WalletCreateRequest walletCreateRequest() {
        return WalletCreateRequest.builder()
                .name("My wallet")
                .currency("USD")
                .build();
    }

    public static WalletActionRequest topUpRequest() {
        return WalletActionRequest.builder()
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(BigDecimal.TEN)
                .currency("USD")
                .build();
    }

    public static Optional<Wallet> optionalWallet(BigDecimal amount) {
        return Optional.of(Wallet.builder()
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(amount)
                .currency("USD")
                .build());
    }

    public static WalletResponse topUpResponse() {
        return WalletResponse.builder()
                .name("MyWallet")
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(BigDecimal.valueOf(100))
                .currency("USD")
                .build();
    }

    public static WalletActionRequest withdrawRequest() {
        return WalletActionRequest.builder()
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(BigDecimal.ONE)
                .currency("USD")
                .build();
    }

    public static WalletResponse withdrawResponse() {
        return WalletResponse.builder()
                .name("MyWallet")
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(BigDecimal.TEN)
                .currency("USD")
                .build();
    }

    public static WalletTransferRequest transferRequest() {
        return WalletTransferRequest.builder()
                .receiverWalletNumber("a222222")
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(BigDecimal.TEN)
                .currency("USD")
                .build();
    }

    public static WalletTransferRequest wrongTransferRequest() {
        return WalletTransferRequest.builder()
                .receiverWalletNumber(DEFAULT_WALLET_NUMBER)
                .walletNumber(DEFAULT_WALLET_NUMBER)
                .amount(BigDecimal.TEN)
                .currency("USD")
                .build();
    }

    public static Page<Wallet> wallets() {
        return new PageImpl<>(List.of(
                Wallet.builder()
                        .id(1)
                        .walletNumber(DEFAULT_WALLET_NUMBER)
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                Wallet.builder()
                        .id(2)
                        .walletNumber("11-11-12")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                Wallet.builder()
                        .id(3)
                        .walletNumber("11-11-13")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                Wallet.builder()
                        .id(4)
                        .walletNumber("11-11-14")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                Wallet.builder()
                        .id(5)
                        .walletNumber("11-11-15")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build()
        ), PageRequest.of(0, 5), 5);
    }

    public static Page<WalletResponse> walletResponses() {
        return new PageImpl<>(List.of(
                WalletResponse.builder()
                        .id(1)
                        .walletNumber(DEFAULT_WALLET_NUMBER)
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                WalletResponse.builder()
                        .id(2)
                        .walletNumber("11-11-12")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                WalletResponse.builder()
                        .id(3)
                        .walletNumber("11-11-13")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                WalletResponse.builder()
                        .id(4)
                        .walletNumber("11-11-14")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build(),
                WalletResponse.builder()
                        .id(5)
                        .walletNumber("11-11-15")
                        .currency("USD")
                        .amount(BigDecimal.TEN)
                        .build()
        ), PageRequest.of(0, 5), 5);
    }

}
