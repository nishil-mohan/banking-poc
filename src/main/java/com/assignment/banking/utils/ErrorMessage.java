package com.assignment.banking.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private Integer errorId;
    private String errorMessage;
    private Date timeStamp;

}
