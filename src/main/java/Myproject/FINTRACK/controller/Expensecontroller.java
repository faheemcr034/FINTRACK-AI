package Myproject.FINTRACK.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
   
   

}
