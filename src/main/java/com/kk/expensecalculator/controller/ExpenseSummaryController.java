package com.kk.expensecalculator.controller;

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
	
	@RequestMapping(value = "/expenseSummary", method = RequestMethod.GET)
	public ExpenseSummaryDTO getExpenseSummaryForTheMonth(@RequestParam String summaryMonth){
		
		return expenseSummary.getExpenseSummaryForTheMonth(summaryMonth);
	}
}
