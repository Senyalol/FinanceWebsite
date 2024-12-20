package com.FinanceBack.FinanceBack.repositorises;

import org.springframework.data.jpa.repository.JpaRepository;
import com.FinanceBack.FinanceBack.Enities.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {
    //Transaction findByTransaction_id(Integer transactionId); // Исправлено на findByTransaction_id
}