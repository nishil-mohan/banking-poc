package com.assignment.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private TransactionType txnType;

    private String txnId;

    private Float amount;

    private String currencyCode;

    private String remarks;

}
