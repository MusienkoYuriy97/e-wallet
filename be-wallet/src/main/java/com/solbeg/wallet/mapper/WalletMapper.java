package com.solbeg.wallet.mapper;

import com.solbeg.wallet.dto.WalletCreateRequest;
import com.solbeg.wallet.dto.WalletResponse;
import com.solbeg.wallet.entity.Wallet;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WalletMapper {
    WalletResponse toDto(Wallet wallet);

    Wallet toWallet(WalletCreateRequest walletCreateRequest, String walletNumber);
}
