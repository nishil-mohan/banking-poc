package com.assignment.banking.controller;

import com.assignment.banking.domain.Account;
import com.assignment.banking.domain.DepositRequest;
import com.assignment.banking.domain.TransactionReference;
import com.assignment.banking.exception.AccountNotFoundException;
import com.assignment.banking.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
@Validated
@Slf4j
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping(value = "/{accountNumber}")
    public Account getAccountInfo(@PathVariable("accountNumber") String accountNumber) throws AccountNotFoundException{
        log.info("Get  Account Info for {}", accountNumber);
        return service.getAccountByNumber(accountNumber);
    }

    @PostMapping(value = "/add")
    public Account createAccount(@Valid @RequestBody Account account) {
        log.info("New account create request with account object: {}", account);
        return service.createAccount(account);
    }


    @PutMapping(value="deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionReference depositAmount(@RequestBody DepositRequest depositRequest) throws AccountNotFoundException {
        log.info("Deposit API triggered for {}", depositRequest);
        return TransactionReference.builder().txnReference(service.depositAmount(depositRequest)).build();

    }

}
