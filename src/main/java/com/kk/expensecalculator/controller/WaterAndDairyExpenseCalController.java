package com.kk.expensecalculator.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<HttpStatus> saveWaterAndDairyExpense(@RequestBody List<WaterDairyExpenseDTO> expenseDataList, @RequestParam String dateOfExpense) {
		List<WaterDairyExpenseDTO> expenseList = expenseDataList;

		// We initialize the dateOfExpense to LocalDate.now() if we don't receive the date parameter
		LocalDate expenseDate = LocalDate.now();
		
		if(StringUtils.isNotBlank(dateOfExpense)) {
			expenseDate = ExpenseCalDateUtils.convertStringDateToLocalDate(dateOfExpense, ExpenseCalDateUtils.INPUT_DATE_PATTERN);
		}
		
		waterAndDairyExpenseService.saveWaterAndDairyExpense(expenseList, expenseDate);
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getWaterAndExpenseDataFor", method = RequestMethod.GET, produces = "application/json")
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpense(@RequestParam String strStartDate, @RequestParam String strEndDate){
		
		// We initialize the startDate & endDate to LocalDate.now() if we don't receive the date range
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<LocalDate> dateRange = new ArrayList<>();
		
		if(StringUtils.isNotBlank(strStartDate) && StringUtils.isNotBlank(strEndDate)) {
			dateRange = ExpenseCalDateUtils.convertStringDateRangeToLocalDateRange(Arrays.asList(strStartDate, strEndDate), ExpenseCalDateUtils.INPUT_DATE_PATTERN);
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
