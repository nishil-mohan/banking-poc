package com.assignment.banking.service.impl;

import com.assignment.banking.domain.*;
import com.assignment.banking.entity.AccountEntity;
import com.assignment.banking.entity.TransactionsEntity;
import com.assignment.banking.exception.AccountNotFoundException;
import com.assignment.banking.exception.BankServiceCustomException;
import com.assignment.banking.repository.AccountRepository;
import com.assignment.banking.repository.TransactionsRepository;
import com.assignment.banking.service.AccountService;
import com.assignment.banking.utils.SecurityUtil;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionsRepository txnRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MeterRegistry meterRegistry;


    @Override
    public Account getAccountByNumber(String accId) throws AccountNotFoundException{
        Optional<AccountEntity> accountEntityOpt = accountRepository.findAccountByNumber(accId);
        AccountEntity accountEntity = accountEntityOpt.orElseThrow(()->new AccountNotFoundException("Account not found"));
        securityUtil.checkForLoggedInUserAccess(accountEntity.getCustomerId());
        return AccountEntity.prepareDto(accountEntity, false);
    }

    @Override
    public Account createAccount(Account account) {
        String loggedInUser = securityUtil.getLoggedInUserName();
        AccountEntity entity = accountRepository.save(Account.prepareEntity(account, loggedInUser));
        this.meterRegistry.counter("Accounts", "Branch",account.getBranchLocation()).increment();
        return AccountEntity.prepareDto(entity, false);
    }



    @Override
    public String depositAmount(DepositRequest depositRequest) throws AccountNotFoundException {
        Optional<AccountEntity> accountEntityOpt = accountRepository.findAccountByNumber(depositRequest.getAccountNumber());
        AccountEntity account = accountEntityOpt.orElseThrow(()->new AccountNotFoundException("Account not found"));
        securityUtil.checkForLoggedInUserAccess(account.getCustomerId());
        account.setBalance(account.getBalance() + depositRequest.getAmount());
        TransactionsEntity txnSource = createTransactionEntity(depositRequest.getAmount(), depositRequest.getCurrency(),
                    depositRequest.getRemarks(), account,
                    TransactionType.DEPOSIT );
        accountRepository.save(account);
        txnRepository.save(txnSource);
        return txnSource.getTxnId();
    }

    @Override
    public String tranferAmount(TransferRequest transferRequest)
            throws AccountNotFoundException, BankServiceCustomException {
        Optional<AccountEntity> sourceAccountOpt = accountRepository.findAccountByNumber(transferRequest.getSenderAccountNumber());
        AccountEntity sourceAccount = sourceAccountOpt.orElseThrow(()->new AccountNotFoundException("Source Account not found"));
        securityUtil.checkForLoggedInUserAccess(sourceAccount.getCustomerId());
        if (!(sourceAccount.getBalance() > transferRequest.getAmountToTransfer() && sourceAccount.getActive()))
            throw new BankServiceCustomException("Transfer not allowed");

        Optional<AccountEntity> destinationAccountOpt = accountRepository.findAccountByNumber(transferRequest.getBeneficaryAccountNumber());
        AccountEntity destinationAccount = destinationAccountOpt.orElseThrow(()->new AccountNotFoundException("Destination Account not found"));

        sourceAccount.setBalance(sourceAccount.getBalance() - transferRequest.getAmountToTransfer());
        destinationAccount.setBalance(destinationAccount.getBalance() + transferRequest.getAmountToTransfer());
        String txnId = "TXNREF-".concat(RandomStringUtils.randomAlphanumeric(10));

        var sourceAccountEntity = accountRepository.save(sourceAccount);
        TransactionsEntity txnSource = createTransactionEntity(transferRequest.getAmountToTransfer(), transferRequest.getCurrency(),
                transferRequest.getPurposeOfTransfer(), sourceAccountEntity,
                TransactionType.WITHDRAW );
        txnSource.setTxnId(txnId);

        var destinationAccountEntity = accountRepository.save(destinationAccount);
        TransactionsEntity txnDestination = createTransactionEntity(transferRequest.getAmountToTransfer(), transferRequest.getCurrency(),
                transferRequest.getPurposeOfTransfer(), destinationAccountEntity,
                TransactionType.DEPOSIT);
        txnDestination.setTxnId(txnId);

        txnRepository.save(txnSource);
        txnRepository.save(txnDestination);
        return txnId;
    }

    @Override
    public Page<Transaction> getAllTransactions(String accountNumber ,Pageable page ) throws AccountNotFoundException{
        var accountOpt = accountRepository.findAccountByNumber(accountNumber);
        var account = accountOpt.orElseThrow(()->new AccountNotFoundException("Account not found"));
        securityUtil.checkForLoggedInUserAccess(account.getCustomerId());
        var txnsEntity = txnRepository.findByAccountEntity(account, page);
        return txnsEntity.map(entity->
                 convertToTxn(entity)
        );
    }

    private Transaction convertToTxn(TransactionsEntity entity) {

            Transaction txn = new Transaction();
            txn.setTxnId(entity.getTxnId());
            txn.setTxnType(entity.getTxnType());
            txn.setAmount(entity.getAmount());
            txn.setRemarks(entity.getRemarks());
            txn.setCurrencyCode(entity.getCurrencyCode());
            return txn;

    }


    private TransactionsEntity createTransactionEntity(Float amount, String currencyCode, String remarks, AccountEntity account,
                                                       TransactionType txnType) {
        TransactionsEntity txnSource =  TransactionsEntity.builder()
                .txnId("TXNREF-".concat(RandomStringUtils.randomAlphanumeric(10).toUpperCase()))
                .accountEntity(account)
                .txnTime(new Date())
                .txnType(txnType)
                .remarks(remarks)
                .currencyCode(currencyCode)
                .amount(amount).build();
        return txnSource;
    }

}