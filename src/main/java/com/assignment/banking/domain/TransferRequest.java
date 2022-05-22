package com.assignment.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public  class TransferRequest {

    private Integer id;
    private String senderAccountNumber;
    private String beneficaryAccountNumber;
    private String txnReference;
    private String purposeOfTransfer;
    private Float amountToTransfer;
    private String currency;
    private Date txnTime;

}
