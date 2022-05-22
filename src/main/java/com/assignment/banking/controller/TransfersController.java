package com.assignment.banking.controller;

import com.assignment.banking.domain.TransactionReference;
import com.assignment.banking.domain.TransferRequest;
import com.assignment.banking.exception.AccountNotFoundException;
import com.assignment.banking.exception.BankServiceCustomException;
import com.assignment.banking.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/transfers")
@Validated
@Slf4j
public class TransfersController {

    @Autowired
    private AccountService accountService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/local")
    public ResponseEntity<TransactionReference> transferAccountToAccount(@NotNull @RequestBody TransferRequest transferRequest)
                throws AccountNotFoundException, BankServiceCustomException {
        log.info("Transfer Account to Account request received: {} ", transferRequest);
        var response = TransactionReference.builder().txnReference(accountService.tranferAmount(transferRequest)).build();
        return ResponseEntity.of(Optional.of(response));
    }

}
