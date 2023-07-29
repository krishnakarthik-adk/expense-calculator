package com.kk.expensecalculator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.service.WaterAndDairyExpenseService;

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
	
	@RequestMapping(value = "/getWaterAndExpenseData", method = RequestMethod.GET, produces = "application/json")
	public List<WaterDairyExpenseDTO> getWaterAndDairyExpense(){
		return waterAndDairyExpenseService.getWaterAndDairyExpenseData();
	}
	
}
