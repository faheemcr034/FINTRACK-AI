package Myproject.FINTRACK.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Myproject.FINTRACK.entity.Expense;
import Myproject.FINTRACK.service.ExpenseService;




@RestController
public class Expensecontroller {
    private final ExpenseService expenseService;
    
    List<Expense> expenses = new ArrayList<>();

    public Expensecontroller(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
   @PostMapping("/expense")
   public Expense addexpense(@RequestBody Expense expense) {
       return expenseService.addExpense(expense);
   }
   @GetMapping("/expenses")
   public List<Expense> getexpenses() {
       return expenseService.getExpenses();
   }
   @GetMapping("/expenses/{id}")
   public Expense getExpenseById(@PathVariable Long id) {
       return expenseService.getExpenseById(id);
   }
   @PutMapping("/expenses/{id}")
   public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
       return expenseService.updateExpense(id, updatedExpense);
}
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}