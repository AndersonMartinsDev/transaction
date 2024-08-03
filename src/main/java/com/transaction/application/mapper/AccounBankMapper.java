package com.transaction.application.mapper;

import com.transaction.application.dto.AccountBankDto;
import com.transaction.domain.entities.AccountBank;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccounBankMapper {
    private AccounBankMapper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static List<AccountBank> toListAccountBank(List<AccountBankDto> list) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return null;
        }
        return list
                .stream()
                .map(AccounBankMapper::toAccountBank)
                .collect(Collectors.toList());
    }
    public static AccountBank toAccountBank(AccountBankDto dto){
        var entity = new AccountBank();
        entity.setBalance(dto.getBalance());
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setAmmount(dto.getAmmount());
        entity.setMcc(dto.getMcc());
        entity.setMccDescription(dto.getMccDescription());
        return entity;
    }



}
