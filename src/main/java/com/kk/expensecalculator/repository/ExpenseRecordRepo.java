package com.kk.expensecalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kk.expensecalculator.domain.ExpenseRecordDO;

public interface ExpenseRecordRepo extends JpaRepository<ExpenseRecordDO, Long>{

}
