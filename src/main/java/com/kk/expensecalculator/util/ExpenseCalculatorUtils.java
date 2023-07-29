package com.kk.expensecalculator.util;

import java.util.ArrayList;
import java.util.List;

import com.kk.expensecalculator.domain.WaterDairyExpenseDO;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;

public class ExpenseCalculatorUtils {
	
	public static List<WaterDairyExpenseDTO> toDTOObjectFromDO(List<WaterDairyExpenseDO> expenseDataDOList) {
		
		List<WaterDairyExpenseDTO> expenseDTOList = new ArrayList<>();
		
		expenseDataDOList.stream().forEach(data -> {
			WaterDairyExpenseDTO waterDairyExpenseDTO = new WaterDairyExpenseDTO();
			waterDairyExpenseDTO.setComments(data.getComments());
			waterDairyExpenseDTO.setItem(data.getItem());
			waterDairyExpenseDTO.setQuantity(data.getQuantity());
			waterDairyExpenseDTO.setUnitPrice(data.getUnitPrice());
			waterDairyExpenseDTO.setTotalPrice(data.getTotalPrice());
			waterDairyExpenseDTO.setDateOfExpense(data.getDateOfExpense());
			
			expenseDTOList.add(waterDairyExpenseDTO);
		});
		
		return expenseDTOList;
	}

	public static List<WaterDairyExpenseDO> toDomainObjectFromDTO(List<WaterDairyExpenseDTO> waterAndDairyExpenseDTOList) {
		
		List<WaterDairyExpenseDO> expenseList = new ArrayList<>();
		
		waterAndDairyExpenseDTOList.stream().forEach( data -> {
			WaterDairyExpenseDO waterDairyExpenseDO = new WaterDairyExpenseDO();
			waterDairyExpenseDO.setComments(data.getComments());
			waterDairyExpenseDO.setItem(data.getItem());
			waterDairyExpenseDO.setQuantity(data.getQuantity());
			waterDairyExpenseDO.setUnitPrice(data.getUnitPrice());
			waterDairyExpenseDO.setTotalPrice(data.getTotalPrice());
			waterDairyExpenseDO.setDateOfExpense(data.getDateOfExpense());
			
			expenseList.add(waterDairyExpenseDO);
		});
		
		return expenseList;
	}
}
