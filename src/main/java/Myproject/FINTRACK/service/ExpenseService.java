package Myproject.FINTRACK.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Myproject.FINTRACK.DTO.ExpenseDTO;
import Myproject.FINTRACK.entity.Expense;
import Myproject.FINTRACK.exception.ExpenseNotFoundException;
import Myproject.FINTRACK.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }
    //CRUD
    public ExpenseDTO addExpense(ExpenseDTO expensedto) {
        Expense expense = convertToEntity(expensedto);
        Expense savedExpense = repository.save(expense);
        return convertToDTO(savedExpense);
    }
    public List<ExpenseDTO> getExpenses() {
        List<Expense> expenses = repository.findAll();
        List<ExpenseDTO> expenseDTOs = new ArrayList<>();
        for(Expense expense : expenses) {
            expenseDTOs.add(convertToDTO(expense));
        }
        return expenseDTOs;
    }
    
    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        return convertToDTO(expense); 
    }
    public ExpenseDTO updateExpense(Long id, ExpenseDTO updatedExpensedto) {
        if(repository.findById(id).isPresent()) {
            Expense existingExpense = repository.findById(id).get();
            existingExpense.setTitle(updatedExpensedto.getTitle());
            existingExpense.setAmount(updatedExpensedto.getAmount());
            Expense updatedExpense = repository.save(existingExpense);
            return convertToDTO(updatedExpense);
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
    //DTO conversions
    private Expense convertToEntity(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setTitle(expenseDTO.getTitle());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());  
        return expense;     
    }
    private ExpenseDTO convertToDTO(Expense expense) {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setTitle(expense.getTitle());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setCategory(expense.getCategory());
        return expenseDTO;
    }
}
