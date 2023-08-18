package com.kk.expensecalculator.controller;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kk.expensecalculator.dto.ExpenseSummaryDTO;
import com.kk.expensecalculator.service.ExpenseSummary;

@RestController
@RequestMapping("/api/expensecalculator/v1")
public class ExpenseSummaryController {

	@Autowired
	private ExpenseSummary expenseSummary;
	
	// The below is for Water and Dairy expense
	@RequestMapping(value = "/expenseSummary", method = RequestMethod.GET)
	public ExpenseSummaryDTO getWaterAndDairyMonthlyExpense(@RequestParam String month, @RequestParam String year){
		
		// We initialize to the current month and year, in case we don't receive the parameters
		int monthSelected = LocalDate.now().getMonthValue();
		int yearSelected = LocalDate.now().getYear();
		
		if(validateMonthAndYear(month, year)) {
			monthSelected = Integer.parseInt(month);
			yearSelected = Integer.parseInt(year);
		}
		
		return expenseSummary.getWaterAndDairyMonthlyExpense(monthSelected, yearSelected);
	}
	
	/*
	 * Overall Expense through the input screen
	 * The reason for separating the water, dairy from monthly expense is that we could easily disable the water and dairy
	 * expense without much or  little impact.
	 */
	@RequestMapping(value = "/monthlyExpenseSummary", method = RequestMethod.GET)
	public ExpenseSummaryDTO getMonthlyExpense(@RequestParam String month, @RequestParam String year) {
		
		// We initialize to the current month and year, in case we don't receive the parameters
		int monthSelected = LocalDate.now().getMonthValue();
		int yearSelected = LocalDate.now().getYear();
		
		if(validateMonthAndYear(month, year)) {
			monthSelected = Integer.parseInt(month);
			yearSelected = Integer.parseInt(year);
		}		
		
		return expenseSummary.getMonthlyExpense(monthSelected, yearSelected);
		
	}
	
	private boolean validateMonthAndYear(String month, String year) {
		if(StringUtils.isNotBlank(month) && StringUtils.isNotBlank(year)) {
			return validateMonth(month) && validateYear(year);
		} else {
			return false;
		}
	}
	
	private boolean validateMonth(String month) {
		return month.chars().allMatch(x -> Character.isDigit(x)) && month.length() <= 2 && !month.contains("00");
	}
	
	private boolean validateYear(String year) {
		return year.chars().allMatch(x -> Character.isDigit(x)) && year.length() == 4 && !year.contains("0000");
	}
}
