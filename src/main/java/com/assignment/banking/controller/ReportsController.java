package com.assignment.banking.controller;

import com.assignment.banking.domain.Transaction;
import com.assignment.banking.exception.AccountNotFoundException;
import com.assignment.banking.exception.BankServiceCustomException;
import com.assignment.banking.service.AccountService;
import com.assignment.banking.utils.CustomPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/reports")
@Validated
@Slf4j
public class ReportsController {

    @Autowired
    private AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/txns")
    public CustomPage<Transaction> getTxns(@NotNull String accountNumber, Pageable page )
                throws AccountNotFoundException, BankServiceCustomException {
        log.info("Reports request: {} ", accountNumber);
        Page<Transaction> response = accountService.getAllTransactions(accountNumber, page);
        return new CustomPage(response);
    }

}
