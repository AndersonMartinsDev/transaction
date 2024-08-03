package com.transaction.domain.exceptions;

import com.transaction.domain.enums.CodeReturnEnum;

public class TransactionException extends RuntimeException {
    public TransactionException(CodeReturnEnum codeReturnEnum) {
        super(codeReturnEnum.name());
    }
}
