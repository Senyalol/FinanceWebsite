package com.FinanceBack.FinanceBack.controllers;

import com.FinanceBack.FinanceBack.servises.TransactionService;
import com.FinanceBack.FinanceBack.DTO.TransactionDTO.CreateTransactionDTO;
import com.FinanceBack.FinanceBack.DTO.TransactionDTO.ShortTransactionDTO;
import com.FinanceBack.FinanceBack.DTO.TransactionDTO.UpdateTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // Импорт для ResponseEntity
import jakarta.validation.Valid; // Импорт для аннотации Valid
import org.springframework.http.HttpStatus; // Импорт для HttpStatus
import java.util.NoSuchElementException; // Импорт для NoSuchElementException

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<ShortTransactionDTO> getAllTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    public ShortTransactionDTO getTransactionById(@PathVariable int id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        try {
            transactionService.createTransaction(createTransactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTransaction(@PathVariable("id") int id, @Valid @RequestBody UpdateTransactionDTO updateTransactionDTO) {
        try {
            transactionService.updateTransaction(id, updateTransactionDTO);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") int id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}