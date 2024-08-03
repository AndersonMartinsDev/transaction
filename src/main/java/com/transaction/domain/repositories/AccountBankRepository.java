package com.transaction.domain.repositories;

import com.transaction.domain.entities.AccountBank;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface AccountBankRepository extends JpaRepository<AccountBank,UUID> {

    @Query(value = "Select * FROM account_bank ab " +
            "Where ab.account_number =:account " +
            "AND (ab.mcc like %:mcc% or ab.mcc_description='CASH')",nativeQuery = true)
    Set<AccountBank> findByAccountNumberAndMccLike(@Param("account") String account, @Param("mcc") String mcc);
}