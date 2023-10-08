package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.domain.WaterDairyExpenseDO;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.repository.WaterAndDairyExpenseRepo;
import com.kk.expensecalculator.util.ExpenseCalculatorUtils;

@Component
public class WaterAndDairyExpenseServiceImpl implements WaterAndDairyExpenseService {
	
	private static final Logger log = LogManager.getLogger(WaterAndDairyExpenseServiceImpl.class);

	@Autowired
	WaterAndDairyExpenseRepo waterAndDairyExpenseRepo;

	@Override
	public void saveWaterAndDairyExpense(List<WaterDairyExpenseDTO> waterAndDairyExpenseList, LocalDate dateOfExpense) {
		
		List<WaterDairyExpenseDO> expenseList = ExpenseCalculatorUtils.toDomainObjectFromDTO(waterAndDairyExpenseList, dateOfExpense);

		waterAndDairyExpenseRepo.saveAll(expenseList);
	}

	@Override
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseDataForDateRange(LocalDate startDate, LocalDate endDate) {
		
		log.info("Fetching water and dairy expense for the date range (after conversion to LocalDates): {}, {}", startDate, endDate);
 
		return ExpenseCalculatorUtils.toDTOObjectFromDO(waterAndDairyExpenseRepo.findByDateRange(startDate, endDate).stream().sorted(Comparator.comparing(WaterDairyExpenseDO::getDateOfExpense)).toList());
	}

	@Override
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseData() {
		
		return ExpenseCalculatorUtils.toDTOObjectFromDO(waterAndDairyExpenseRepo.findAll());
	}
	
}
