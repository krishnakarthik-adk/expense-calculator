package com.kk.expensecalculator.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kk.expensecalculator.domain.ExpenseRecordDO;

@Repository
public interface ExpenseRecordRepo extends JpaRepository<ExpenseRecordDO, Long>{
	
	@Query(value = "SELECT * FROM EXPENSE_RECORD e WHERE e.DATE_OF_EXPENSE BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<ExpenseRecordDO> findByDateOfExpense(LocalDate startDate, LocalDate endDate);
	
	@Query(value = "SELECT * FROM EXPENSE_RECORD e WHERE e.EXPENSE_CATEGORY =:category AND e.DATE_OF_EXPENSE BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<ExpenseRecordDO> findByCategoryForDateRange(LocalDate startDate, LocalDate endDate, String category);

}
