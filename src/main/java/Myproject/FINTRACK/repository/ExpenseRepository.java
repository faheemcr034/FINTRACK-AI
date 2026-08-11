package Myproject.FINTRACK.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Myproject.FINTRACK.entity.Expense;

public interface  ExpenseRepository  extends JpaRepository<Expense, Long> {
    
}
