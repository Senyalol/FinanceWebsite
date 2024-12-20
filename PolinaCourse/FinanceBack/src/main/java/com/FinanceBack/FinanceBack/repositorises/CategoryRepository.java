package com.FinanceBack.FinanceBack.repositorises;

import org.springframework.data.jpa.repository.JpaRepository;
import com.FinanceBack.FinanceBack.Enities.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
        Category findByName(String name);
}
