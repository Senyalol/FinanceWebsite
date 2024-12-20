package com.FinanceBack.FinanceBack.servises;

import com.FinanceBack.FinanceBack.DTO.CategoryDTO.CreateCategoryDTO;
import com.FinanceBack.FinanceBack.DTO.CategoryDTO.ShortCategoryInfoDTO;
import com.FinanceBack.FinanceBack.DTO.CategoryDTO.UpdateCategoryDTO;
import com.FinanceBack.FinanceBack.Enities.Category;
import com.FinanceBack.FinanceBack.Enities.User;
import com.FinanceBack.FinanceBack.repositorises.CategoryRepository;
import com.FinanceBack.FinanceBack.repositorises.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CategoriesServise { // Исправлено имя класса

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoriesServise(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<ShortCategoryInfoDTO> getCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToShortCategoryInfoDTO)
                .toList();
    }

    public ShortCategoryInfoDTO getCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID " + id));
        return convertToShortCategoryInfoDTO(category);
    }

    public void createCategory(CreateCategoryDTO createCategoryDTO) {
        User user = userRepository.findById(createCategoryDTO.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID " + createCategoryDTO.getUser_id()));

        Category category = new Category();
        category.setUser(user);
        category.setName(createCategoryDTO.getName());
        category.setType(createCategoryDTO.getType());

        categoryRepository.save(category);
    }

    public void updateCategory(int id, UpdateCategoryDTO updateCategoryDTO) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID " + id));

        if (updateCategoryDTO.getName() != null) {
            categoryToUpdate.setName(updateCategoryDTO.getName());
        }
        if (updateCategoryDTO.getType() != null) {
            categoryToUpdate.setType(updateCategoryDTO.getType());
        }
        if (updateCategoryDTO.getUser_id() != null) {
            User user = userRepository.findById(updateCategoryDTO.getUser_id())
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID " + updateCategoryDTO.getUser_id()));
            categoryToUpdate.setUser(user);
        }

        categoryRepository.save(categoryToUpdate);
    }

    public void deleteCategory(int id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID " + id));
        categoryRepository.delete(categoryToDelete);
    }

    private ShortCategoryInfoDTO convertToShortCategoryInfoDTO(Category category) {
        ShortCategoryInfoDTO categoryDTO = new ShortCategoryInfoDTO();
        categoryDTO.setCategories_id(category.getId());
        categoryDTO.setUser_id(category.getUser().getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setType(category.getType());
        return categoryDTO;
    }
}