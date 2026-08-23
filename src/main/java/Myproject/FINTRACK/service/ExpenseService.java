package Myproject.FINTRACK.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import Myproject.FINTRACK.DTO.ExpenseDTO;
import Myproject.FINTRACK.Specification.ExpenseSpecification;
import static Myproject.FINTRACK.Specification.ExpenseSpecification.hasCategory;
import Myproject.FINTRACK.entity.Expense;
import Myproject.FINTRACK.exception.ExpenseNotFoundException;
import Myproject.FINTRACK.repository.ExpenseRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);
    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }
    //CRUD
    public ExpenseDTO addExpense(ExpenseDTO expensedto) {
        log.info("Adding expense: {}", expensedto.getTitle());
        Expense expense = convertToEntity(expensedto);
        Expense savedExpense = repository.save(expense);
        log.info("Expense added with ID: {}", savedExpense.getId());
        return convertToDTO(savedExpense);
    }

    public Page<ExpenseDTO> getExpenses(Pageable pageable) {
        Page<Expense> expensePage = repository.findAll(pageable);
        return expensePage.map(this::convertToDTO);
    }
        
    public Page<ExpenseDTO> searchExpensesByTitle(String title, Pageable pageable) {
        Page<Expense> expenses = repository.findByTitleContainingIgnoreCase(title, pageable);
        return expenses.map(this::convertToDTO);
    }

    //category filtering
    public Page<ExpenseDTO> getExpensesByCategory(String category, Pageable pageable) {
        Page<Expense> expenses = repository.findByCategory(category, pageable);
        return expenses.map(this::convertToDTO);
    }
    //category and title filtering
    public Page<ExpenseDTO> findExpensesByTitleAndCategory(String search, String category, Pageable pageable) {
        Page<Expense> expenses = repository.findByTitleAndCategory(search, category, pageable);
        log.info("Showing results based on category and title you asked for");
        return expenses.map(this::convertToDTO);
    }
    //amount range filtering
    public Page<ExpenseDTO> findExpensesByAmountRange(Double minAmount, Double maxAmount, Pageable pageable) {
        Page<Expense> expenses = repository.findByAmountRange(minAmount, maxAmount, pageable);
        return expenses.map(this::convertToDTO);
    }
    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> { log.warn("Expense with ID {} not found", id); return new ExpenseNotFoundException("Expense not found"); });
        log.info("Retrieved expense with ID: {}", id);
        return convertToDTO(expense);
    }
    public ExpenseDTO updateExpense(Long id, ExpenseDTO updatedExpensedto) {
        if(repository.findById(id).isPresent()) {
            Expense existingExpense = repository.findById(id).get();
            existingExpense.setTitle(updatedExpensedto.getTitle());
            existingExpense.setAmount(updatedExpensedto.getAmount());
            Expense updatedExpense = repository.save(existingExpense);
            log.info("Expense with ID {} updated", id);
            return convertToDTO(updatedExpense);
        }
        else {
            log.warn("Expense with ID {} not found for update", id);
            throw new ExpenseNotFoundException("Expense not found");
        }
        
    }
    public void deleteExpense(Long id) {
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }
        else {
            log.warn("Expense with ID {} not found for deletion", id);
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
    public Page<ExpenseDTO> FilterExpenses(String category, String search,double minAmount,double maxAmount, Pageable pageable) {
        Specification<Expense> spec = Specification.unrestricted();
        if (category != null && !category.isEmpty()) {
            spec = spec.and(ExpenseSpecification.hasCategory(category));
        }
        if (search != null && !search.isEmpty()) {
            spec = spec.and(ExpenseSpecification.titleContains(search));
        }
        if (minAmount != 0) {
            spec = spec.and(ExpenseSpecification.hasMinAmount(minAmount));
        }
        if (maxAmount != 0) {
            spec = spec.and(ExpenseSpecification.hasMaxAmount(maxAmount));
        }
        Page<Expense> expenses = repository.findAll(spec, pageable);
        return expenses.map(this::convertToDTO);
    }
   
}