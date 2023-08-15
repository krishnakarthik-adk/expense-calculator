package com.kk.expensecalculator.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.domain.ExpenseRecordDO;
import com.kk.expensecalculator.dto.ExpenseRecordDTO;
import com.kk.expensecalculator.repository.ExpenseRecordRepo;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;

@Component
public class ExpenseRecordServiceImpl implements ExpenseRecordService {

	@Autowired
	private ExpenseRecordRepo expenseRecordRepo;
	
	@Override
	public void saveExpenseRecords(List<ExpenseRecordDTO> expenseRecords) {
		
		List<ExpenseRecordDO> expenseRecordDOList = convertDTOToDomainObject(expenseRecords);
		expenseRecordRepo.saveAll(expenseRecordDOList);

	}
	
	private List<ExpenseRecordDO> convertDTOToDomainObject(List<ExpenseRecordDTO> expenseRecordDTOList) {
		
		List<ExpenseRecordDO> expenseRecords = new ArrayList<>();
		
		expenseRecordDTOList.stream().forEach(expenseRecord -> {
			ExpenseRecordDO expenseRecordDO = new ExpenseRecordDO();
			
			expenseRecordDO.setItem(expenseRecord.getItem());
			expenseRecordDO.setAmount(Integer.parseInt(expenseRecord.getAmount()));
			expenseRecordDO.setExpenseCategory(expenseRecord.getExpenseCategory());
			expenseRecordDO.setNotes(expenseRecord.getNotes());	
			expenseRecordDO.setDateOfExpense(ExpenseCalDateUtils.convertStringDateToLocalDate(expenseRecord.getDateOfExpense(), ExpenseCalDateUtils.INPUT_DATE_PATTERN));
			
			expenseRecords.add(expenseRecordDO);
		});
		
		return expenseRecords;
	}

}
