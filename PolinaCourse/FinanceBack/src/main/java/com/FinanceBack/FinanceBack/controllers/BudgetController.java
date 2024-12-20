package com.FinanceBack.FinanceBack.controllers;

import com.FinanceBack.FinanceBack.servises.BudgetService;
import com.FinanceBack.FinanceBack.DTO.BudgetDTO.CreateBudgetDTO;
import com.FinanceBack.FinanceBack.DTO.BudgetDTO.ShortBudgetDTO;
import com.FinanceBack.FinanceBack.DTO.BudgetDTO.UpdateBudgetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<ShortBudgetDTO> getAllBudgets() {
        return budgetService.getBudgets();
    }

    @GetMapping("/{id}")
    public ShortBudgetDTO getBudgetById(@PathVariable int id) {
        return budgetService.getBudgetById(id);
    }

    @PostMapping
    public void createBudget(@Valid @RequestBody CreateBudgetDTO createBudgetDTO) {
        budgetService.createBudget(createBudgetDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBudget(@PathVariable int id, @Valid @RequestBody UpdateBudgetDTO updateBudgetDTO) {
        try {
            budgetService.updateBudget(id, updateBudgetDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable int id) {
        try {
            budgetService.deleteBudget(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}