package com.kk.expensecalculator.controller;

import java.util.List;

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
}
