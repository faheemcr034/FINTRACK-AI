package Myproject.FINTRACK.DTO;

public class ExpenseDTO {
    private String title;
    private double amount;
    private String category;
    public ExpenseDTO() {
    }
    public ExpenseDTO(String title, double amount, String category) {
        this.title = title;
        this.amount = amount;
        this.category = category;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
}
