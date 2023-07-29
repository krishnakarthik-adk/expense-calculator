package com.kk.expensecalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kk.expensecalculator.domain.WaterDairyExpenseDO;

@Repository
public interface WaterAndDairyExpenseRepo extends JpaRepository<WaterDairyExpenseDO, Long>{

}
