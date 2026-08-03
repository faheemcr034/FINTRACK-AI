package Myproject.FINTRACK.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Myproject.FINTRACK.entity.Expense;
import Myproject.FINTRACK.exception.ExpenseNotFoundException;
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
    public Expense getExpenseById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public Expense updateExpense(Long id, Expense updatedExpense) {
        if(repository.findById(id).isPresent()) {
            Expense existingExpense = repository.findById(id).get();
            existingExpense.setTitle(updatedExpense.getTitle());
            existingExpense.setAmount(updatedExpense.getAmount());
            return repository.save(existingExpense);
        }
        else {
            throw new ExpenseNotFoundException("Expense not found");
        }
        
    }
    public void deleteExpense(Long id) {
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }
        else {
            throw new ExpenseNotFoundException("Expense not found");
        }
    }
}
