package com.transaction.external;

import com.transaction.application.dto.AccountBankDto;
import com.transaction.application.mapper.AccounBankMapper;
import com.transaction.domain.entities.AccountBank;
import com.transaction.domain.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.transaction.application.mapper.AccounBankMapper.*;

@RestController
@RequestMapping("/account-banks")
public class AccountController {

    private final AccountService accountBankService;

    @Autowired
    public AccountController(AccountService accountBankService) {
        this.accountBankService = accountBankService;
    }

    @PostMapping
    public ResponseEntity<Map<String,String>> createAccountBank(@RequestBody List<AccountBankDto> accountBank) {
        var list = accountBank.stream().map(vl -> {
            vl.setBalance(vl.getAmmount());
            return vl;
        }).collect(Collectors.toList());

        accountBankService.save(toListAccountBank(list));

        return new ResponseEntity<>(Map.of("retorno","Contas criadas com sucesso!"), HttpStatus.CREATED);
    }
}