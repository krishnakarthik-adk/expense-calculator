package com.kk.expensecalculator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kk.expensecalculator.domain.ExpenseRecordSelectDO;

@Repository
public interface ExpenseRecordSelectRepo extends JpaRepository<ExpenseRecordSelectDO, Long>{
	
	@Query(value = "SELECT s.SELECT_OPTIONS FROM EXPENSE_RECORD_SELECT_OPTIONS s WHERE s.SELECT_NAME='EXPENSE_RECORD_OPTIONS'", nativeQuery = true)
	String findBySelectName();

}
