package Myproject.FINTRACK.Specification;
import org.springframework.data.jpa.domain.Specification;
import Myproject.FINTRACK.entity.Expense;
public class ExpenseSpecification {
    public static Specification<Expense> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }
       public static Specification<Expense> titleContains(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + search.toLowerCase() + "%");
    }
    public static Specification<Expense> hasMinAmount(Double minAmount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
}
    public static Specification<Expense> hasMaxAmount(Double maxAmount) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
    }
}