package com.FinanceBack.FinanceBack.repositorises;

import org.springframework.data.jpa.repository.JpaRepository;
import com.FinanceBack.FinanceBack.Enities.Budget;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    //Budget findBudgetByBudgets_id(Integer id);
}