package com.kk.expensecalculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.dto.ExpenseSummaryDTO;
import com.kk.expensecalculator.dto.ItemSummaryDTO;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.repository.WaterAndDairyExpenseRepo;
import com.kk.expensecalculator.util.ExpenseCalculatorUtils;

@Component
public class ExpenseSummaryImpl implements ExpenseSummary {

	@Autowired
	private WaterAndDairyExpenseRepo expenseSummaryRepo;

	@Override
	public ExpenseSummaryDTO getExpenseSummaryForTheMonth(String summaryMonth) {
		
		// To Do for the month - date range, for demo --> we're finding all.
		List<WaterDairyExpenseDTO> expenseDataList = ExpenseCalculatorUtils.toDTOObjectFromDO(expenseSummaryRepo.findAll());
		
		// Summary
		Map<String, List<WaterDairyExpenseDTO>> groupedExpenseData = expenseDataList.stream().collect(Collectors.groupingBy(WaterDairyExpenseDTO::getItem));
		
		List<ItemSummaryDTO> summaryDTOList = new ArrayList<>();
		
		groupedExpenseData.forEach((item, expenseDataPerItem) -> {
			ItemSummaryDTO summaryDto = new ItemSummaryDTO();
			summaryDto.setItem(item);			
			summaryDto.setAmountPayabalePerItem( expenseDataPerItem.stream().map(WaterDairyExpenseDTO::getTotalPrice).reduce((i,j) -> i+j).get() );
			
			summaryDTOList.add(summaryDto);
		});
		
		ExpenseSummaryDTO expenseSummaryDTO = new ExpenseSummaryDTO();
		expenseSummaryDTO.setExpenseSummaryDTOList(summaryDTOList);
		expenseSummaryDTO.setFinalAmountPayable(summaryDTOList.stream().map(ItemSummaryDTO::getAmountPayabalePerItem).reduce((i,j) -> i + j).get());
		
		return expenseSummaryDTO;
	}

}
