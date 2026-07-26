package Myproject.FINTRACK.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Myproject.FINTRACK.entity.Expense;

@Service
public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();
    public Expense addExpense(Expense expense) {
        expenses.add(expense);
        return expense;
    }
    public List<Expense> getExpenses() {
        return expenses;
    }
}
