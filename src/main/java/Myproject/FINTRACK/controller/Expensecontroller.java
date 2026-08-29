package Myproject.FINTRACK.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Myproject.FINTRACK.DTO.ExpenseDTO;
import Myproject.FINTRACK.service.ExpenseService;
import jakarta.validation.Valid;





@RestController
public class Expensecontroller {
    private final ExpenseService expenseService;

    public Expensecontroller(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
   @PostMapping("/expense")
   public ExpenseDTO addexpense(@Valid @RequestBody ExpenseDTO expensedto,@RequestParam Long userId) {
       return expenseService.addExpense(expensedto, userId);
   }
   @GetMapping("/expenses")
   public Page<ExpenseDTO> getexpenses(Pageable pageable) {
       return expenseService.getExpenses(pageable);
   }
   @GetMapping("/expenses/{id}")
   public ExpenseDTO getExpenseById(@PathVariable Long id) {
       return expenseService.getExpenseById(id);
   }

   @GetMapping("/expenses/user/{userId}")
   public Page<ExpenseDTO> getExpenseByUserId(@PathVariable Long userId, Pageable pageable) {
       return expenseService.getExpenseByUserId(userId, pageable);
   }

   @PutMapping("/expenses/{id}")
   public ExpenseDTO updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO updatedExpensedto) {
       return expenseService.updateExpense(id, updatedExpensedto);
}

    //filtering based on category and title
    @GetMapping("/expenses/filter")
    public Page<ExpenseDTO> filterExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") double minAmount,
            @RequestParam(required = false, defaultValue = "0") double maxAmount,
            Pageable pageable) {
        return expenseService.FilterExpenses(category, search, minAmount, maxAmount, pageable);
    }


    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}