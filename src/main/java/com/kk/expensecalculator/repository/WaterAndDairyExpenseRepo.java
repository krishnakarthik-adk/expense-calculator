package com.kk.expensecalculator.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kk.expensecalculator.domain.WaterDairyExpenseDO;

@Repository
public interface WaterAndDairyExpenseRepo extends JpaRepository<WaterDairyExpenseDO, Long> {

	@Query(value = "SELECT * FROM WATER_DAIRY_EXPENSE w WHERE w.DATE_OF_EXPENSE BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<WaterDairyExpenseDO> findByDateRange(LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT * FROM WATER_DAIRY_EXPENSE w WHERE w.ITEM_NAME = :item AND w.DATE_OF_EXPENSE BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<WaterDairyExpenseDO> findByDateRangeForItem(LocalDate startDate, LocalDate endDate, String item);
}
