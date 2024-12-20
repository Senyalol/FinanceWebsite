package com.FinanceBack.FinanceBack.servises;

import com.FinanceBack.FinanceBack.DTO.BudgetDTO.CreateBudgetDTO;
import com.FinanceBack.FinanceBack.DTO.BudgetDTO.ShortBudgetDTO;
import com.FinanceBack.FinanceBack.DTO.BudgetDTO.UpdateBudgetDTO;
import com.FinanceBack.FinanceBack.Enities.Budget;
import com.FinanceBack.FinanceBack.Enities.User;
import com.FinanceBack.FinanceBack.Enities.Category;
import com.FinanceBack.FinanceBack.repositorises.BudgetRepository;
import com.FinanceBack.FinanceBack.repositorises.CategoryRepository;
import com.FinanceBack.FinanceBack.repositorises.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ShortBudgetDTO> getBudgets() {
        return budgetRepository.findAll().stream()
                .map(this::convertToShortBudgetDTO)
                .toList();
    }

    public ShortBudgetDTO getBudgetById(int id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Budget not found with ID " + id));
        return convertToShortBudgetDTO(budget);
    }

    public void createBudget(CreateBudgetDTO createBudgetDTO) {
        User user = userRepository.findById(createBudgetDTO.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("User not found with ID " + createBudgetDTO.getUser_id()));

        Category category = categoryRepository.findById(createBudgetDTO.getCategory_id())
                .orElseThrow(() -> new NoSuchElementException("Category not found with ID " + createBudgetDTO.getCategory_id()));

        Budget budget = new Budget();
        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(createBudgetDTO.getAmount());
        budget.setStartDate(createBudgetDTO.getStartDate());
        budget.setEndDate(createBudgetDTO.getEndDate());

        budgetRepository.save(budget);
    }

    public void updateBudget(int id, UpdateBudgetDTO updateBudgetDTO) {
        Budget budgetToUpdate = budgetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Budget not found with ID " + id));

        if (updateBudgetDTO.getUser_id() != null) {
            User user = userRepository.findById(updateBudgetDTO.getUser_id())
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID " + updateBudgetDTO.getUser_id()));
            budgetToUpdate.setUser(user);
        }
        if (updateBudgetDTO.getCategory_id() != null) {
            Category category = categoryRepository.findById(updateBudgetDTO.getCategory_id())
                    .orElseThrow(() -> new NoSuchElementException("Category not found with ID " + updateBudgetDTO.getCategory_id()));
            budgetToUpdate.setCategory(category);
        }
        if (updateBudgetDTO.getAmount() != null) {
            budgetToUpdate.setAmount(updateBudgetDTO.getAmount());
        }
        if (updateBudgetDTO.getStartDate() != null) {
            budgetToUpdate.setStartDate(updateBudgetDTO.getStartDate());
        }
        if (updateBudgetDTO.getEndDate() != null) {
            budgetToUpdate.setEndDate(updateBudgetDTO.getEndDate());
        }

        budgetRepository.save(budgetToUpdate);
    }

    public void deleteBudget(int id) {
        Budget budgetToDelete = budgetRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Budget not found with ID " + id));
        budgetRepository.delete(budgetToDelete);
    }

    private ShortBudgetDTO convertToShortBudgetDTO(Budget budget) {
        ShortBudgetDTO budgetDTO = new ShortBudgetDTO();
        budgetDTO.setBudgets_id(budget.getBudgets_id());
        budgetDTO.setUser_id(budget.getUser().getId());
        budgetDTO.setCategory_id(budget.getCategory().getId());
        budgetDTO.setAmount(budget.getAmount());
        budgetDTO.setStartDate(budget.getStartDate());
        budgetDTO.setEndDate(budget.getEndDate());
        return budgetDTO;
    }
}