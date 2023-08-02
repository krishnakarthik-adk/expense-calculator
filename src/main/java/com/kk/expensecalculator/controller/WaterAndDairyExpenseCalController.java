package com.kk.expensecalculator.controller;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.service.WaterAndDairyExpenseService;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;

@RestController
@RequestMapping(value = "/api/v1")
public class WaterAndDairyExpenseCalController {
	
	@Autowired
	WaterAndDairyExpenseService waterAndDairyExpenseService;
	
	@RequestMapping(value = "/saveWaterAndDairyExpense", method = {RequestMethod.POST})
	public void saveWaterAndDairyExpense(@RequestBody List<WaterDairyExpenseDTO> expenseDataList) {
		List<WaterDairyExpenseDTO> expenseList = expenseDataList;
		waterAndDairyExpenseService.saveWaterAndDairyExpense(expenseList);
	}
	
	@RequestMapping(value = "/getWaterAndExpenseDataFor", method = RequestMethod.GET, produces = "application/json")
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpense(@RequestParam String strStartDate, @RequestParam String strEndDate){
		
		LocalDate startDate = null;
		LocalDate endDate = null;
		
		if(StringUtils.isNotBlank(strStartDate)) {
			startDate = ExpenseCalDateUtils.convertStringDateToLocalDate(strStartDate);
		} else {
			startDate = LocalDate.now();
		}
		if(StringUtils.isNotBlank(strEndDate)) {
			endDate = ExpenseCalDateUtils.convertStringDateToLocalDate(strStartDate);
		} else {
			endDate = LocalDate.now();
		}
		
		return waterAndDairyExpenseService.getWaterAndDairyExpenseDataForDateRange(startDate, endDate);
	}
	
}
