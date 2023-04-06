package com.solbeg.wallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WalletActionRequest {
    @NotNull
    @NotBlank
    private String walletNumber;

    @NotNull
    @Pattern(regexp = "^USD$", message = "Only USD currency is allowed")
    private String currency;

    @NotNull
    @DecimalMin(value = "0.01", message = "The minimum amount is 0.01")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Only 2 fractions allowed")
    private BigDecimal amount;
}
