package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.domain.ExpenseRecordDO;
import com.kk.expensecalculator.dto.ExpenseRecordDTO;
import com.kk.expensecalculator.dto.ExpenseSummaryDTO;
import com.kk.expensecalculator.dto.ItemSummaryDTO;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.repository.ExpenseRecordRepo;
import com.kk.expensecalculator.repository.WaterAndDairyExpenseRepo;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;
import com.kk.expensecalculator.util.ExpenseCalculatorUtils;

@Component
public class ExpenseSummaryImpl implements ExpenseSummary {

	@Autowired
	private WaterAndDairyExpenseRepo waterAndDairyExpenseRepo;
	
	@Autowired
	private ExpenseRecordRepo expenseRecordRepo;

	@Override
	public ExpenseSummaryDTO getWaterAndDairyMonthlyExpense(int month, int year) {
		
		LocalDate startDate = ExpenseCalDateUtils.getDateRangeForTheMonth(month, year).get(0);
		LocalDate endDate = ExpenseCalDateUtils.getDateRangeForTheMonth(month, year).get(1);
		ExpenseSummaryDTO expenseSummaryDTO = new ExpenseSummaryDTO();
		
		List<WaterDairyExpenseDTO> expenseDataList = ExpenseCalculatorUtils.toDTOObjectFromDO(waterAndDairyExpenseRepo.findByDateRange(startDate, endDate));
		
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

	@Override
	public ExpenseSummaryDTO getMonthlyExpense(int month, int year) {
		
		LocalDate startDate = ExpenseCalDateUtils.getDateRangeForTheMonth(month, year).get(0);
		LocalDate endDate = ExpenseCalDateUtils.getDateRangeForTheMonth(month, year).get(1);
		
		List<ExpenseRecordDTO> expenseRecordDTOList = expenseRecordDOToDTO(expenseRecordRepo.findByDateOfExpense(startDate, endDate));
		
		// For summary
		Map<String, List<ExpenseRecordDTO>> recordsByExpenseCategory = expenseRecordDTOList.stream().collect(Collectors.groupingBy(ExpenseRecordDTO::getExpenseCategory));
		
		List<ItemSummaryDTO> itemExpenseSummaryDTOList = new ArrayList<>();
		// For total expense calculation
		List<Integer> expensesPerItem = new ArrayList<>();
		
		recordsByExpenseCategory.forEach((item, itemExpense) -> {
			
			ItemSummaryDTO itemSummaryDTO = new ItemSummaryDTO();
			int expensePerItem = itemExpense.stream().map(r -> r.getAmount()).reduce(0, (i,j) -> i+j);
			
			itemSummaryDTO.setItem(item);			
			itemSummaryDTO.setAmountPayabalePerItem(expensePerItem);
			
			itemExpenseSummaryDTOList.add(itemSummaryDTO);
			expensesPerItem.add(expensePerItem);
			
		});
		
		ExpenseSummaryDTO expenseSummaryDTO = new ExpenseSummaryDTO();
		expenseSummaryDTO.setExpenseSummaryDTOList(itemExpenseSummaryDTOList);
		expenseSummaryDTO.setFinalAmountPayable(expensesPerItem.stream().reduce(0, (i,j) -> i + j));
		
		
		return expenseSummaryDTO;
	}
	
	private List<ExpenseRecordDTO> expenseRecordDOToDTO(List<ExpenseRecordDO> expenseRecords){
		
		List<ExpenseRecordDTO> expenseRecordDTOList = new ArrayList<>();
		
		expenseRecords.stream().forEach(er -> {
			ExpenseRecordDTO expenseRecordDTO = new ExpenseRecordDTO();
			expenseRecordDTO.setAmount(er.getAmount());
			expenseRecordDTO.setExpenseCategory(er.getExpenseCategory());
			expenseRecordDTO.setNotes(er.getNotes());
			expenseRecordDTO.setDateOfExpense(er.getDateOfExpense().toString());
			
			expenseRecordDTOList.add(expenseRecordDTO);
		});
		
		return expenseRecordDTOList;
	}
	

}
