package com.example.koifarm.repository;



import com.example.koifarm.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transactions, UUID> {

    @Query("select YEAR(t.createAt) as year, MONTH(t.createAt) as month, SUM(t.amount) from Transactions t " +
            "where t.status='SUCCESS' and t.to.id =:userId " +
            "GROUP BY YEAR(t.createAt), MONTH(t.createAt) " +
            "ORDER BY YEAR(t.createAt), MONTH(t.createAt)")
    List<Object[]> calculateMonthlyRev(@Param("userId") long userId);
}
