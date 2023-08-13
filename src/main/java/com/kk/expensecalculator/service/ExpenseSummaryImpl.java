package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.dto.ExpenseSummaryDTO;
import com.kk.expensecalculator.dto.ItemSummaryDTO;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.repository.WaterAndDairyExpenseRepo;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;
import com.kk.expensecalculator.util.ExpenseCalculatorUtils;

@Component
public class ExpenseSummaryImpl implements ExpenseSummary {

	@Autowired
	private WaterAndDairyExpenseRepo expenseSummaryRepo;

	@Override
	public ExpenseSummaryDTO getExpenseSummaryForTheMonth(int month, int year) {
		
		LocalDate startDate = ExpenseCalDateUtils.getDateRangeForTheMonth(month, year).get(0);
		LocalDate endDate = ExpenseCalDateUtils.getDateRangeForTheMonth(month, year).get(1);
		ExpenseSummaryDTO expenseSummaryDTO = new ExpenseSummaryDTO();
		
		// To Do for the month - date range, for demo --> we're finding all.
		List<WaterDairyExpenseDTO> expenseDataList = ExpenseCalculatorUtils.toDTOObjectFromDO(expenseSummaryRepo.findByDateRange(startDate, endDate));
		
		if(!expenseDataList.isEmpty()) {
			// Summary
			Map<String, List<WaterDairyExpenseDTO>> expenseSummaryPerItem = expenseDataList.stream().collect(Collectors.groupingBy(WaterDairyExpenseDTO::getItem));
			
			List<ItemSummaryDTO> summaryDTOList = new ArrayList<>();
			
			expenseSummaryPerItem.forEach((item, expenseDataPerItem) -> {
				ItemSummaryDTO summaryDto = new ItemSummaryDTO();
				summaryDto.setItem(item);			
				summaryDto.setAmountPayabalePerItem( expenseDataPerItem.stream().map(WaterDairyExpenseDTO::getTotalPrice).reduce((i,j) -> i+j).get() );
				
				summaryDTOList.add(summaryDto);
			});
			
			expenseSummaryDTO.setExpenseSummaryDTOList(summaryDTOList);
			expenseSummaryDTO.setFinalAmountPayable(summaryDTOList.stream().map(ItemSummaryDTO::getAmountPayabalePerItem).reduce((i,j) -> i + j).get());
			
			return expenseSummaryDTO;
		} else {
			expenseSummaryDTO.setExpenseSummaryDTOList(Collections.emptyList());
			expenseSummaryDTO.setFinalAmountPayable(0);
			
			return expenseSummaryDTO;
		}
		
	}

}
