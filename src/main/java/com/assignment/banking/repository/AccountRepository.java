package com.assignment.banking.repository;

import com.assignment.banking.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    @Query("select acc from AccountEntity acc where acc.number=?1")
    Optional<AccountEntity> findAccountByNumber(String accountNumber);
}
