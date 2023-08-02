package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.domain.WaterDairyExpenseDO;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.repository.WaterAndDairyExpenseRepo;
import com.kk.expensecalculator.util.ExpenseCalculatorUtils;

@Component
public class WaterAndDairyExpenseServiceImpl implements WaterAndDairyExpenseService {

	@Autowired
	WaterAndDairyExpenseRepo waterAndDairyExpenseRepo;

	@Override
	public void saveWaterAndDairyExpense(List<WaterDairyExpenseDTO> waterAndDairyExpenseList) {
		
		List<WaterDairyExpenseDO> expenseList = ExpenseCalculatorUtils.toDomainObjectFromDTO(waterAndDairyExpenseList);

		waterAndDairyExpenseRepo.saveAll(expenseList);
	}

	@Override
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseDataForDateRange(LocalDate startDate, LocalDate endDate) {
 
		return ExpenseCalculatorUtils.toDTOObjectFromDO(waterAndDairyExpenseRepo.findByDateRange(startDate, endDate));
	}
	
}
