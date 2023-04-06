package com.solbeg.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletCreateRequest {

    @NotNull
    @Size(min = 2, message = "Size is too shor. Shouldn't be shorter than 3.")
    @Size(max = 20, message = "Size is too long. Shouldn't be longer than 20.")
    private String name;

    @NotNull
    @Pattern(regexp = "^USD$", message = "Only USD currency is allowed")
    private String currency;
}
