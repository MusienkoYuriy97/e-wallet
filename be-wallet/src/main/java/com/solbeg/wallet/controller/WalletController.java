package com.solbeg.wallet.controller;

import com.solbeg.wallet.dto.WalletActionRequest;
import com.solbeg.wallet.dto.WalletCreateRequest;
import com.solbeg.wallet.dto.WalletResponse;
import com.solbeg.wallet.dto.WalletTransferRequest;
import com.solbeg.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public Page<WalletResponse> getWallets(@PageableDefault Pageable pageable) {
        return walletService.getWallets(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createWallet(@Valid @RequestBody WalletCreateRequest walletCreateRequest) {
        walletService.createWallet(walletCreateRequest);
    }

    @PutMapping("/topUp")
    public WalletResponse topUp(@Valid @RequestBody WalletActionRequest walletActionRequest) {
        return walletService.topUpWallet(walletActionRequest);
    }


    @PutMapping("/withdraw")
    public WalletResponse withdraw(@Valid @RequestBody WalletActionRequest walletActionRequest) {
        return walletService.withdrawMoney(walletActionRequest);
    }

    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody WalletTransferRequest walletTransferRequest) {
        walletService.transfer(walletTransferRequest);
    }
}
