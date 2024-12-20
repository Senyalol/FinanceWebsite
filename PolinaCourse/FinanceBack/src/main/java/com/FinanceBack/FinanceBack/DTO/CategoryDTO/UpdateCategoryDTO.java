package com.FinanceBack.FinanceBack.DTO.CategoryDTO;
import lombok.Data;

@Data
public class UpdateCategoryDTO {

    private Integer categories_id;
    private Integer user_id;
    private String name;
    private String type;

}
