package Myproject.FINTRACK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Myproject.FINTRACK.entity.Expense;

@Repository
public interface  ExpenseRepository  extends JpaRepository<Expense, Long> {
    
}
