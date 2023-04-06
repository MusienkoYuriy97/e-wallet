package com.solbeg.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponse {
    private Integer id;
    private String name;
    private String walletNumber;
    private String currency;
    private BigDecimal amount;
}
