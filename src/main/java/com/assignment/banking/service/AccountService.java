package com.assignment.banking.service;

import com.assignment.banking.domain.Account;
import com.assignment.banking.domain.DepositRequest;
import com.assignment.banking.domain.Transaction;
import com.assignment.banking.domain.TransferRequest;
import com.assignment.banking.exception.AccountNotFoundException;
import com.assignment.banking.exception.BankServiceCustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    Account getAccountByNumber(String accNumber) throws AccountNotFoundException;

    Page<Transaction> getAllTransactions(String accNumber,  Pageable page ) throws AccountNotFoundException;

    Account createAccount(Account account);

    String depositAmount(DepositRequest depositRequest) throws AccountNotFoundException;

    String tranferAmount(TransferRequest transferRequest)
            throws AccountNotFoundException, BankServiceCustomException;
}
