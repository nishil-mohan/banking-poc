package com.assignment.banking.domain;

import com.assignment.banking.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Integer id;

    private String number;

    @NotBlank(message = "Branch location cannot be empty")
    private String branchLocation;

    private String customerId;

    @NotNull(message = "Account type cannot be empty")
    private AccountType type;

    private Double interestRate;

    private Date openingDate;

    private Boolean active;

    @NotNull(message = "Txn limit cannot be empty")
    private Float transactionLimit;

    private Float balance;

    private List<Transaction> transactions;

    /**
     * Method to prepare Entity from DTO
     *
     * @param account
     * @return accountEntity
     */
    public static AccountEntity prepareEntity(Account account, String loggedInUser) {
        AccountEntity entity = new AccountEntity();
        BeanUtils.copyProperties(account, entity);
        entity.setNumber("BANK-".concat(RandomStringUtils.randomAlphanumeric(10).toUpperCase()));
        entity.setActive(true);
        entity.setBranchId(account.getBranchLocation());
        entity.setCustomerId(loggedInUser);
        entity.setCurrencyCode("AED"); //TODO hardcoded now. Need to load from business rules
        entity.setBalance(Float.valueOf(0));
        entity.setOpeningDate(new Date());
        return entity;
    }


}
