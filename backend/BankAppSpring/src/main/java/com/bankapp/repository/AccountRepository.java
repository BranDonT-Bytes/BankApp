package com.bankapp.repository;

import com.bankapp.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<AccountEntity, Long> {
}