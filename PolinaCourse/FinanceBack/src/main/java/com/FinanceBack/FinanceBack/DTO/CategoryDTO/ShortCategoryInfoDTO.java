package com.FinanceBack.FinanceBack.DTO.CategoryDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortCategoryInfoDTO {
    private Integer categories_id; // ID категории
    private Integer user_id; // ID пользователя
    private String name; // Название категории
    private String type; // Тип категории
}