package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.List;

import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;

public interface WaterAndDairyExpenseService {
	public void saveWaterAndDairyExpense(List<WaterDairyExpenseDTO> waterAndDairyExpenseList, LocalDate dateOfExpense);

	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseDataForDateRange(LocalDate startDate, LocalDate endDate);

	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseData();

	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseDataForDateRangeForItem(LocalDate startDate, LocalDate endDate, String item);

}
