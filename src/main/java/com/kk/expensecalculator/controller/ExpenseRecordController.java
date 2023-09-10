package com.kk.expensecalculator.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

//@CrossOrigin(origins = "http://expensecalculator-ui.s3-website.ap-south-1.amazonaws.com")
@RestController
@RequestMapping("/api/expensecalculator/v1")
public class ExpenseRecordController {

	private static final Logger log = LogManager.getLogger(ExpenseRecordController.class);
	
	@Autowired
	private ExpenseRecordService expenseRecordService;
	
	@RequestMapping(value = "/saveExpenseRecords", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> saveExpenseRecords(@RequestBody List<ExpenseRecordDTO> expenseRecords, @RequestParam String dateOfExpense) {
		
		log.info("The dateOfExpense received to save the records {}", dateOfExpense);
		
		expenseRecordService.saveExpenseRecords(expenseRecords, dateOfExpense);
		
		log.info("The expense records saved for the date: {}", dateOfExpense);
		
		return ResponseEntity.ok(HttpStatus.OK); // To check the diff between normal return and Response Entity
	}
	
	@RequestMapping(value = "/getExpenseRecordSelectOptions", method = RequestMethod.GET)
	public List<String> getExpenseRecordSelectOptions(){
		log.info("Fetching expense record SELECT options.");
		
		return expenseRecordService.getExpenseRecordSelectOptions();
	}
	
	@RequestMapping(value = "/getMonthlyExpenseRecords", method = RequestMethod.GET)
	public List<ExpenseRecordDTO> getMonthlyExpenseRecords(@RequestParam String strStartDate, @RequestParam String strEndDate) {
		
		log.info("Request to fetch the monthly expense records for date range - strStartDate, strEndDate: {} {}", strStartDate, strEndDate);
		
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
