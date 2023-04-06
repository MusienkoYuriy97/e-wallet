package com.solbeg.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WalletTransferRequest extends WalletActionRequest {
    @NotNull
    @Size(min = 7, max = 7, message = "Wallet number should contain only 7 symbols")
    private String receiverWalletNumber;
}
