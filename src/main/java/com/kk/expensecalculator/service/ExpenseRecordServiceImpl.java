package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kk.expensecalculator.domain.ExpenseRecordDO;
import com.kk.expensecalculator.dto.ExpenseRecordDTO;
import com.kk.expensecalculator.repository.ExpenseRecordRepo;
import com.kk.expensecalculator.repository.ExpenseRecordSelectRepo;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;

@Component
public class ExpenseRecordServiceImpl implements ExpenseRecordService {

	@Autowired
	private ExpenseRecordRepo expenseRecordRepo;

	@Autowired
	private ExpenseRecordSelectRepo expenseRecordSelectRepo;

	@Override
	public void saveExpenseRecords(List<ExpenseRecordDTO> expenseRecords, String dateOfExpense) {

		List<ExpenseRecordDO> expenseRecordDOList = convertDTOToDomainObject(expenseRecords, dateOfExpense);
		expenseRecordRepo.saveAll(expenseRecordDOList);

	}

	@Override
	public List<String> getExpenseRecordSelectOptions() {
		String selectOptions = expenseRecordSelectRepo.findBySelectName();
		List<String> sortedSelectOpntions = Arrays.asList(selectOptions.split(","));
		Collections.sort(sortedSelectOpntions);

		return sortedSelectOpntions;
	}

	@Override
	public List<ExpenseRecordDTO> getMonthlyExpenseRecordsForDateRange(LocalDate startDate, LocalDate endDate) {
		return convertDomainObjectToDTO(expenseRecordRepo.findByDateOfExpense(startDate, endDate));
	}

	private List<ExpenseRecordDO> convertDTOToDomainObject(List<ExpenseRecordDTO> expenseRecordDTOList,
			String dateOfExpense) {

		List<ExpenseRecordDO> expenseRecords = new ArrayList<>();

		expenseRecordDTOList.stream().forEach(expenseRecord -> {
			ExpenseRecordDO expenseRecordDO = new ExpenseRecordDO();
			expenseRecordDO.setAmount(expenseRecord.getAmount());
			expenseRecordDO.setTransactionType(expenseRecord.getTransactionType());
			expenseRecordDO.setExpenseCategory(expenseRecord.getExpenseCategory());
			expenseRecordDO.setNotes(expenseRecord.getNotes());
			// We set the date from the parameter received
			expenseRecordDO.setDateOfExpense(ExpenseCalDateUtils.convertStringDateToLocalDate(dateOfExpense,
					ExpenseCalDateUtils.INPUT_DATE_PATTERN));

			expenseRecords.add(expenseRecordDO);
		});

		return expenseRecords;
	}
	
	private List<ExpenseRecordDTO> convertDomainObjectToDTO(List<ExpenseRecordDO> expenseRecords) {
		
		List<ExpenseRecordDTO> expesneRecordDTOList = new ArrayList<>();
		
		expenseRecords.stream().forEach(expenseRecord -> {
			ExpenseRecordDTO expenseRecordDTO = new ExpenseRecordDTO();
			
			expenseRecordDTO.setAmount(expenseRecord.getAmount());
			expenseRecordDTO.setTransactionType(expenseRecord.getTransactionType());
			expenseRecordDTO.setExpenseCategory(expenseRecord.getExpenseCategory());
			expenseRecordDTO.setNotes(expenseRecord.getNotes());
			expenseRecordDTO.setDateOfExpense(expenseRecord.getDateOfExpense().toString());
			
			expesneRecordDTOList.add(expenseRecordDTO);
			
		});
		
		return expesneRecordDTOList;
	}

}
