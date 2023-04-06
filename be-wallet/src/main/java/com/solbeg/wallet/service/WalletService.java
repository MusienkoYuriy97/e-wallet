package com.solbeg.wallet.service;

import com.solbeg.wallet.dto.WalletActionRequest;
import com.solbeg.wallet.dto.WalletCreateRequest;
import com.solbeg.wallet.dto.WalletResponse;
import com.solbeg.wallet.dto.WalletTransferRequest;
import com.solbeg.wallet.entity.User;
import com.solbeg.wallet.entity.Wallet;
import com.solbeg.wallet.exception.WalletException;
import com.solbeg.wallet.mapper.WalletMapper;
import com.solbeg.wallet.repository.WalletRepository;
import com.solbeg.wallet.utils.WalletNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    private final WalletNumberGenerator walletNumberGenerator;

    @Transactional(readOnly = true)
    public Page<WalletResponse> getWallets(Pageable pageable) {
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var wallets = walletRepository.findAllByUser(pageable, user);
        return wallets.map(walletMapper::toDto);
    }

    @Transactional
    public void createWallet(WalletCreateRequest walletCreateRequest) {
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var newWallet = walletMapper.toWallet(walletCreateRequest, walletNumberGenerator.generate());
        newWallet.setUser(user);

        walletRepository.save(newWallet);
    }


    @Transactional
    public WalletResponse topUpWallet(WalletActionRequest walletActionRequest) {
        var wallet = findByWalletNumberAndUser(walletActionRequest.getWalletNumber());

        wallet.setAmount(wallet.getAmount().add(walletActionRequest.getAmount()));
        return walletMapper.toDto(wallet);
    }

    @Transactional
    public WalletResponse withdrawMoney(WalletActionRequest walletActionRequest) {
        var wallet = findByWalletNumberAndUser(walletActionRequest.getWalletNumber());

        var resultAmount = wallet.getAmount().subtract(walletActionRequest.getAmount());
        if (BigDecimal.ZERO.compareTo(resultAmount) > 0) {
            throw new WalletException("Not enough money to withdraw");
        }
        wallet.setAmount(resultAmount);
        return walletMapper.toDto(wallet);
    }

    @Transactional
    public void transfer(WalletTransferRequest walletTransferRequest) {
        if (walletTransferRequest.getWalletNumber().equals(walletTransferRequest.getReceiverWalletNumber())) {
            throw new WalletException("Cannot transfer money to the current wallet");
        }

        withdrawMoney(walletTransferRequest);
        sendMoney(walletTransferRequest.getReceiverWalletNumber(), walletTransferRequest.getAmount());
    }

    private void sendMoney(String toWallet, BigDecimal amount) {
        var optionalWallet = walletRepository.findByWalletNumber(toWallet);
        if (optionalWallet.isEmpty()) {
            throw new WalletException("Wallet doesn't exist");
        }

        var wallet = optionalWallet.get();
        wallet.setAmount(wallet.getAmount().add(amount));
    }

    private Wallet findByWalletNumberAndUser(String walletNumber) {
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var optionalWallet = walletRepository.findByWalletNumberAndUser(walletNumber, user);
        if (optionalWallet.isEmpty()) {
            throw new WalletException("Wallet doesn't exist");
        }

        return optionalWallet.get();
    }
}
