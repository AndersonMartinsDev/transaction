package com.transaction.external;

import com.transaction.application.dto.TransactionDto;
import com.transaction.domain.enums.CodeReturnEnum;
import com.transaction.domain.services.TransactionService;
import com.transaction.domain.services.impl.TransactionServiceImpl;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static com.transaction.application.mapper.TransactionMapper.toTransaction;
import static com.transaction.domain.util.DataReturnPatternUtil.dataPattern;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionServiceImpl;
    private TimeLimiter timer = TimeLimiter.of(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(100)).build());

    @Autowired
    public TransactionController(TransactionServiceImpl transactionServiceImpl) {
        this.transactionServiceImpl = transactionServiceImpl;
    }

    @PostMapping
    public Callable createTransaction(@RequestBody TransactionDto transactionDto) {
        return TimeLimiter.decorateFutureSupplier(timer, () ->
                CompletableFuture.supplyAsync(() -> {
                    transactionServiceImpl.createTransaction(toTransaction(transactionDto));
                    return new ResponseEntity<>(dataPattern(CodeReturnEnum.APPROVED), HttpStatus.OK);
                })
        );
    }

}
