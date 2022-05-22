package com.assignment.banking.entity;

import com.assignment.banking.domain.Account;
import com.assignment.banking.domain.AccountType;
import com.assignment.banking.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 50, name = "account_number")
    private String number;

    @Column(nullable = false, length = 50, name = "customer_id")
    private String customerId;

    @Column(name = "branch_location", nullable = false, length = 50)
    private String branchId;

    @Column(nullable = false, length = 50, name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "opening_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date openingDate;

    @Column(name = "currency_code",nullable = false)
    private String currencyCode;

    @Column(name = "balance",nullable = false)
    private Float balance;

    @Column(name = "txn_limit", nullable = false)
    private Float transactionLimit;

    @Column(nullable = false, name = "is_active")
    private Boolean active;

    @OneToMany(mappedBy = "accountEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<TransactionsEntity> txns;

    /**
     * Method to prepare Account DTO from account entity
     *
     * @param accountEntity
     * @return account
     */
    public static Account prepareDto(AccountEntity accountEntity, boolean includeTransactions) {
        List<Transaction> txns = null;
        if (includeTransactions){
            txns = new ArrayList<>();
            var txnEntities = accountEntity.getTxns();
            for (int i = 0; i < txnEntities.size(); i++) {
                var txn = new Transaction();
                TransactionsEntity txnEntity = txnEntities.get(i);
                BeanUtils.copyProperties(txnEntities.get(i), txn);
                txns.add(txn);
            }

        }
        Account account = new Account(accountEntity.getId(), accountEntity.getNumber(),
                accountEntity.getBranchId(),accountEntity.getCustomerId(),accountEntity.getType(),
                null,
                accountEntity.getOpeningDate(), accountEntity.getActive(), accountEntity.getTransactionLimit(),
                accountEntity.getBalance(), txns);
        return account;
    }

    /**
     * Method to prepare Account List from Account Entity list
     *
     * @param accountEntities
     * @return list of accounts
     */
    public static List<Account> prepareDtoList(List<AccountEntity> accountEntities) {
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity entity : accountEntities) {
            accounts.add(prepareDto(entity, false));
        }
        return accounts;
    }
}
