package com.FinanceBack.FinanceBack.controllers;

import com.FinanceBack.FinanceBack.DTO.CategoryDTO.CreateCategoryDTO;
import com.FinanceBack.FinanceBack.DTO.CategoryDTO.ShortCategoryInfoDTO;
import com.FinanceBack.FinanceBack.DTO.CategoryDTO.UpdateCategoryDTO; // Импортируем UpdateCategoryDTO
import com.FinanceBack.FinanceBack.servises.CategoriesServise; // Исправлено имя сервиса
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoriesServise categoriesService; // Исправлено имя сервиса

    @Autowired
    public CategoryController(CategoriesServise categoriesService) {
        this.categoriesService = categoriesService; // Исправлено имя сервиса
    }

    @GetMapping
    public List<ShortCategoryInfoDTO> getAllCategories() {
        return categoriesService.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortCategoryInfoDTO> getCategoryById(@PathVariable int id) {
        try {
            ShortCategoryInfoDTO categoryDTO = categoriesService.getCategoryById(id);
            return ResponseEntity.ok(categoryDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO) {
        try {
            categoriesService.createCategory(createCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Категория успешно создана");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Пользователь не найден с id: " + createCategoryDTO.getUser_id());
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании категории");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable("id") int id, @RequestBody UpdateCategoryDTO updateCategoryDTO) { // Изменено на UpdateCategoryDTO
        try {
            categoriesService.updateCategory(id, updateCategoryDTO); // Теперь передаем правильный тип
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") int id) {
        try {
            categoriesService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Логируйте ошибку
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}