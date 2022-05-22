package com.assignment.banking.repository;

import com.assignment.banking.entity.AccountEntity;
import com.assignment.banking.entity.TransactionsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsEntity, Integer> {

    Optional<TransactionsEntity> findByTxnId(String txnId);
    Page<TransactionsEntity> findByAccountEntity(AccountEntity accountId, Pageable page);


}
