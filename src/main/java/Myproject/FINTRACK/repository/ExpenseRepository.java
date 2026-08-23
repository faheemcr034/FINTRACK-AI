package Myproject.FINTRACK.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Myproject.FINTRACK.entity.Expense;
@Repository
public interface  ExpenseRepository  extends JpaRepository<Expense, Long>,JpaSpecificationExecutor<Expense> {
    Page<Expense> findByTitleContainingIgnoreCase(String title,Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE e.category = :category")
    Page<Expense> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :search, '%')) AND e.category = :category")
    Page<Expense> findByTitleAndCategory(@Param("search") String search, @Param("category") String category, Pageable pageable);

    @Query("SELECT e FROM Expense e WHERE e.amount BETWEEN :minAmount AND :maxAmount")
    Page<Expense> findByAmountRange(@Param("minAmount") Double minAmount, @Param("maxAmount") Double maxAmount, Pageable pageable);
}
