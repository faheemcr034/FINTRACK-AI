package Myproject.FINTRACK.exception;

public class ExpenseNotFoundException extends RuntimeException {
    private String message;

    public ExpenseNotFoundException(String message) {
        this.message = message;
    }

}
