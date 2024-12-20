package com.FinanceBack.FinanceBack.servises;

import com.FinanceBack.FinanceBack.DTO.TransactionDTO.CreateTransactionDTO;
import com.FinanceBack.FinanceBack.DTO.TransactionDTO.ShortTransactionDTO;
import com.FinanceBack.FinanceBack.DTO.TransactionDTO.UpdateTransactionDTO;
import com.FinanceBack.FinanceBack.Enities.Transaction;
import com.FinanceBack.FinanceBack.Enities.User;
import com.FinanceBack.FinanceBack.Enities.Category;
import com.FinanceBack.FinanceBack.repositorises.TransactionsRepository;
import com.FinanceBack.FinanceBack.repositorises.UserRepository;
import com.FinanceBack.FinanceBack.repositorises.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant; // Импорт класса Instant
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TransactionService {

    private final TransactionsRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public TransactionService(TransactionsRepository transactionRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ShortTransactionDTO> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return transactions.stream()
                .map(transaction -> {
                    ShortTransactionDTO transactionDTO = new ShortTransactionDTO();
                    transactionDTO.setTransaction_id(transaction.getTransaction_id());
                    transactionDTO.setUser_id(transaction.getUser().getId());
                    transactionDTO.setCategories_id(transaction.getCategory().getId());
                    transactionDTO.setAmount(transaction.getAmount());
                    transactionDTO.setDate(transaction.getDate());
                    transactionDTO.setDescription(transaction.getDescription());
                    return transactionDTO;
                }).toList();
    }

    public ShortTransactionDTO getTransactionById(int id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id: " + id));

        ShortTransactionDTO transactionDTO = new ShortTransactionDTO();
        transactionDTO.setTransaction_id(transaction.getTransaction_id());
        transactionDTO.setUser_id(transaction.getUser().getId());
        transactionDTO.setCategories_id(transaction.getCategory().getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDate(transaction.getDate());
        transactionDTO.setDescription(transaction.getDescription());

        return transactionDTO;
    }

    public void createTransaction(CreateTransactionDTO createTransactionDTO) {
        User user = userRepository.findById(createTransactionDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + createTransactionDTO.getUserId()));

        Category category = categoryRepository.findById(createTransactionDTO.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + createTransactionDTO.getCategoryId()));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(createTransactionDTO.getAmount());
        transaction.setDate(createTransactionDTO.getDate() != null ? createTransactionDTO.getDate() : Instant.now());
        transaction.setDescription(createTransactionDTO.getDescription());

        transactionRepository.save(transaction);
    }

    public void updateTransaction(int id, UpdateTransactionDTO updateTransactionDTO) {
        Transaction transactionToUpdate = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id: " + id));

        if (updateTransactionDTO.getUser_id() != null) {
            User user = userRepository.findById(updateTransactionDTO.getUser_id())
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + updateTransactionDTO.getUser_id()));
            transactionToUpdate.setUser(user);
        }

        if (updateTransactionDTO.getCategories_id() != null) {
            Category category = categoryRepository.findById(updateTransactionDTO.getCategories_id())
                    .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + updateTransactionDTO.getCategories_id()));
            transactionToUpdate.setCategory(category);
        }

        if (updateTransactionDTO.getAmount() != null) {
            transactionToUpdate.setAmount(updateTransactionDTO.getAmount());
        }

        if (updateTransactionDTO.getDate() != null) {
            transactionToUpdate.setDate(updateTransactionDTO.getDate());
        }

        if (updateTransactionDTO.getDescription() != null) {
            transactionToUpdate.setDescription(updateTransactionDTO.getDescription());
        }

        transactionRepository.save(transactionToUpdate);
    }

    public void deleteTransaction(int id) {
        Transaction transactionToDelete = transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with id: " + id));
        transactionRepository.delete(transactionToDelete);
    }
}