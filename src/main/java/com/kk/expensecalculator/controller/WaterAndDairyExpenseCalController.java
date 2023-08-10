package com.kk.expensecalculator.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		// We initialize the startDate & endDate to LocalDate.now() if we don't receive the date range
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<LocalDate> dateRange = new ArrayList<>();
		
		if(StringUtils.isNotBlank(strStartDate) && StringUtils.isNotBlank(strEndDate)) {
			dateRange = ExpenseCalDateUtils.convertStringDateRangeToLocalDateRange(Arrays.asList(strStartDate, strEndDate));
			startDate = dateRange.get(0);
			endDate = dateRange.get(1);
		}
		
		return waterAndDairyExpenseService.getWaterAndDairyExpenseDataForDateRange(startDate, endDate);
	}
	
	// Demo for UI project
	@RequestMapping(value = "/getWaterAndExpenseData", method = RequestMethod.GET, produces = "application/json")
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpense1(){
		return waterAndDairyExpenseService.getWaterAndDairyExpenseData();
	}
	
}
