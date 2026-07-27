package Myproject.FINTRACK.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Myproject.FINTRACK.entity.Expense;
import Myproject.FINTRACK.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }
    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }
    public List<Expense> getExpenses() {
        return repository.findAll();
    }
}
