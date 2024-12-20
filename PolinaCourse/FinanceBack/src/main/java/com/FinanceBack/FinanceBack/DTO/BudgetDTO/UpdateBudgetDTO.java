package com.FinanceBack.FinanceBack.DTO.BudgetDTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateBudgetDTO {
    private Integer user_id; // ID пользователя
    private Integer category_id; // ID категории
    private BigDecimal amount; // Сумма бюджета
    private LocalDate startDate; // Дата начала
    private LocalDate endDate; // Дата окончания
}