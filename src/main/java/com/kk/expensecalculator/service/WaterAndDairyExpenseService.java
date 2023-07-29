package com.kk.expensecalculator.service;

import java.util.List;

import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;

public interface WaterAndDairyExpenseService {
	public void saveWaterAndDairyExpense(List<WaterDairyExpenseDTO> waterAndDairyExpenseList);

	public List<WaterDairyExpenseDTO> getWaterAndDairyExpenseData();

}
