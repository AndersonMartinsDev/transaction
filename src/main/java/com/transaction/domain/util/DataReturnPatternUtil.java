package com.transaction.domain.util;

import com.transaction.domain.enums.CodeReturnEnum;

import java.util.Map;

public class DataReturnPatternUtil {
    private DataReturnPatternUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Map<String,Object> dataPattern(CodeReturnEnum codeReturnEnum){
        return Map.of(
                "code",codeReturnEnum.getCode(),
                "message",codeReturnEnum.getDescription()
        );
    }
}
