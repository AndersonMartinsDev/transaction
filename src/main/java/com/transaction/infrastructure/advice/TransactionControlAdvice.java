package com.transaction.infrastructure.advice;

import com.transaction.infrastructure.model.ExceptionModel;
import com.transaction.domain.exceptions.TransactionException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class TransactionControlAdvice extends PatterErrorReturn {

    @ExceptionHandler({
            InvalidDataAccessResourceUsageException.class,
            HttpMessageNotWritableException.class,
            InvalidDataAccessApiUsageException.class,
            UnsupportedOperationException.class,
            EntityNotFoundException.class,
            JpaSystemException.class,
            NullPointerException.class,
            UnexpectedTypeException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class})
    public ResponseEntity<ExceptionModel> exception(Exception exception) {
        return buildResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ExceptionModel> exception(HttpMessageNotReadableException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionModel> exception(NoResourceFoundException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ExceptionModel> exception(TransactionException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.OK);
    }
}
