package com.assignment.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public  class DepositRequest {
    private String accountNumber;
    private Float amount;
    private String currency;
    private String remarks;
}
