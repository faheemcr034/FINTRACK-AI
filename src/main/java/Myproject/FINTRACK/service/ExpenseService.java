package Myproject.FINTRACK.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Myproject.FINTRACK.DTO.ExpenseDTO;
import Myproject.FINTRACK.Specification.ExpenseSpecification;
import Myproject.FINTRACK.entity.Expense;
import Myproject.FINTRACK.entity.User;
import Myproject.FINTRACK.exception.ExpenseNotFoundException;
import Myproject.FINTRACK.repository.ExpenseRepository;
import Myproject.FINTRACK.repository.UserRepository;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(ExpenseService.class);
    public ExpenseService(ExpenseRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }
    //CRUD
    public ExpenseDTO addExpense(ExpenseDTO expensedto,Long userId) {
        log.info("Adding expense: {}", expensedto.getTitle());
        User user = userRepository.findById(userId).orElseThrow(() -> { log.warn("User with ID {} not found", userId); return new RuntimeException("User not found"); });
        Expense expense = convertToEntity(expensedto);
        expense.setUser(user); // Set the user for the expense
        Expense savedExpense = repository.save(expense);
        log.info("Expense added with ID: {}", savedExpense.getId());
        return convertToDTO(savedExpense);
    }

    public Page<ExpenseDTO> getExpenses(Pageable pageable) {
        Page<Expense> expensePage = repository.findAll(pageable);
        return expensePage.map(this::convertToDTO);
    }
    
    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = repository.findById(id).orElseThrow(() -> { log.warn("Expense with ID {} not found", id); return new ExpenseNotFoundException("Expense not found"); });
        log.info("Retrieved expense with ID: {}", id);
        return convertToDTO(expense);
    }
    public Page<ExpenseDTO> getExpenseByUserId(Long userId, Pageable pageable) {
        Page<Expense> expensePage = repository.findByUserId(userId, pageable);
        return expensePage.map(this::convertToDTO);
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

    
    //filtering based on category, title and amount range
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