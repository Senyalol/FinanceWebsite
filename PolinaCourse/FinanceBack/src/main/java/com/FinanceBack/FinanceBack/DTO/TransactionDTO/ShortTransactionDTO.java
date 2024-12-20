package com.FinanceBack.FinanceBack.DTO.TransactionDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ShortTransactionDTO {
    private Integer transaction_id;
    private Integer user_id;
    private Integer categories_id;
    private BigDecimal amount;
    private Instant date; // Измените на Instant
    private String description;

}
