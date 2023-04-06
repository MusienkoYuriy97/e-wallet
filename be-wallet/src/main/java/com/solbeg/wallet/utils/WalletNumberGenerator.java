package com.solbeg.wallet.utils;

import com.solbeg.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class WalletNumberGenerator {
    private final WalletRepository walletRepository;


    public String generate() {

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        char c = (char) (rnd.nextInt(26) + 'a');
        final String result = String.valueOf(c) + n;

        if (walletRepository.existsByWalletNumber(result)) {
            generate();
        }

        return result;
    }
}
