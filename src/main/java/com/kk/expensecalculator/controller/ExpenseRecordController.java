package com.kk.expensecalculator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kk.expensecalculator.dto.ExpenseRecordDTO;
import com.kk.expensecalculator.service.ExpenseRecordService;

@RestController
@RequestMapping("/api/expensecalculator/v1")
public class ExpenseRecordController {

	@Autowired
	private ExpenseRecordService expenseRecordService;
	
	@RequestMapping(value = "/saveExpenseRecords", method = RequestMethod.GET)
	public ResponseEntity<String> saveExpenseRecords(@RequestBody List<ExpenseRecordDTO> expenseRecords) {
		expenseRecordService.saveExpenseRecords(expenseRecords);
		
		return ResponseEntity.ok(""); // To check the diff between normal return and Response Entity
	}
}
