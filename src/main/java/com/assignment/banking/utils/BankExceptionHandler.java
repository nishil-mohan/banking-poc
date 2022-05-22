package com.assignment.banking.utils;

import com.assignment.banking.exception.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BankExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(AccountNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorId(HttpStatus.NOT_FOUND.value());
        errorMessage.setErrorMessage(ex.getMessage());
        errorMessage.setTimeStamp(new Date());

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorId(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setErrorMessage(ex.getMessage());
        errorMessage.setTimeStamp(new Date());

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleValidationException(Exception ex) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorId(HttpStatus.BAD_REQUEST.value());

        if (ex instanceof MethodArgumentNotValidException) {
            errorMessage.setErrorMessage(((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()
                    .stream().map((e) -> e.getDefaultMessage()).collect(Collectors.joining(",")));
        }
        if (ex instanceof ConstraintViolationException) {
            errorMessage.setErrorMessage(((ConstraintViolationException) ex).getConstraintViolations().stream()
                    .map((e) -> e.getMessage()).collect(Collectors.joining(",")));
        }
        errorMessage.setTimeStamp(new Date());

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AuthorizationServiceException.class})
    public ResponseEntity<ErrorMessage> handleAuthorisationException(Exception ex) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorId(HttpStatus.FORBIDDEN.value());

            errorMessage.setErrorMessage(((AuthorizationServiceException) ex).getMessage());

        errorMessage.setTimeStamp(new Date());

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
    }
}
