package com.transaction.domain.enums;

public enum CodeReturnEnum {
    APPROVED("00", "Transação Aprovada"),
    REJECTED("51", "Não possui Saldo suficiente"),
    NO_EXIST_ACCOUNT("99", "Conta ou carteira inexistente!"),
    ANY_OTHER_PROBLEM("07", "Houve um problema para realizar a transação");
    private String code;
    private String description;

    CodeReturnEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

}
