package com.transaction.infrastructure.advice;

import com.transaction.infrastructure.model.ExceptionModel;
import com.transaction.domain.enums.CodeReturnEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.transaction.domain.util.DataReturnPatternUtil.dataPattern;

public abstract class PatterErrorReturn {
    protected Map<String, Object> codeVerify(String message) {
        CodeReturnEnum codeReturnEnum = null;
        try {
            codeReturnEnum = CodeReturnEnum.valueOf(message);
        }catch (IllegalArgumentException exception){
            codeReturnEnum = CodeReturnEnum.ANY_OTHER_PROBLEM;
        }
        return dataPattern(codeReturnEnum);
    }

    protected ResponseEntity<ExceptionModel> buildResponse(String message, HttpStatus status) {
        var codeReturn = codeVerify(message);
        return new ResponseEntity<ExceptionModel>(ExceptionModel
                .builder()
                .message((String) codeReturn.get("message"))
                .code((String) codeReturn.get("code"))
                .build(), status);
    }
}
