package com.transaction.external;

import com.transaction.domain.entities.Transaction;
import com.transaction.domain.enums.CodeReturnEnum;
import com.transaction.domain.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.transaction.domain.util.DataReturnPatternUtil.dataPattern;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>(dataPattern(CodeReturnEnum.APPROVED),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
