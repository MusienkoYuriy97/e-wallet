package com.solbeg.wallet.repository;

import com.solbeg.wallet.entity.User;
import com.solbeg.wallet.entity.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Page<Wallet> findAllByUser(Pageable pageable, User user);
    Optional<Wallet> findByWalletNumberAndUser(String walletNumber, User user);
    Optional<Wallet> findByWalletNumber(String walletNumber);

    boolean existsByWalletNumber(String walletNumber);

}
