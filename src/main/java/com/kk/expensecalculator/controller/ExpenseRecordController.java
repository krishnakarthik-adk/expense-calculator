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

import com.kk.expensecalculator.dto.ExpenseRecordDTO;
import com.kk.expensecalculator.service.ExpenseRecordService;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;

@RestController
@RequestMapping("/api/expensecalculator/v1")
public class ExpenseRecordController {

	@Autowired
	private ExpenseRecordService expenseRecordService;
	
	@RequestMapping(value = "/saveExpenseRecords", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> saveExpenseRecords(@RequestBody List<ExpenseRecordDTO> expenseRecords, @RequestParam String dateOfExpense) {
		expenseRecordService.saveExpenseRecords(expenseRecords, dateOfExpense);
		
		return ResponseEntity.ok(HttpStatus.OK); // To check the diff between normal return and Response Entity
	}
	
	@RequestMapping(value = "/getExpenseRecordSelectOptions", method = RequestMethod.GET)
	public List<String> getExpenseRecordSelectOptions(){
		return expenseRecordService.getExpenseRecordSelectOptions();
	}
	
	@RequestMapping(value = "/getMonthlyExpenseRecords", method = RequestMethod.GET)
	public List<ExpenseRecordDTO> getMonthlyExpenseRecords(@RequestParam String strStartDate, @RequestParam String strEndDate) {
		
		// We initialize the startDate & endDate to LocalDate.now() if we don't receive the date range
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<LocalDate> dateRange = new ArrayList<>();
		
		if(StringUtils.isNotBlank(strStartDate) && StringUtils.isNotBlank(strEndDate)) {
			dateRange = ExpenseCalDateUtils.convertStringDateRangeToLocalDateRange(Arrays.asList(strStartDate, strEndDate), ExpenseCalDateUtils.INPUT_DATE_PATTERN);
			startDate = dateRange.get(0);
			endDate = dateRange.get(1);
		}
		
		return expenseRecordService.getMonthlyExpenseRecordsForDateRange(startDate, endDate);		
		
	}
}
