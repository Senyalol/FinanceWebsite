package com.FinanceBack.FinanceBack.DTO.TransactionDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class CreateTransactionDTO {
    private Integer userId; // Измените на userId
    private Integer categoryId; // Измените на categoryId
    private BigDecimal amount;
    private Instant date; // Измените на Instant
    private String description;
}
