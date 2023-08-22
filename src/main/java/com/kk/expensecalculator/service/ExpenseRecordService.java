package com.kk.expensecalculator.service;

import java.time.LocalDate;
import java.util.List;

import com.kk.expensecalculator.dto.ExpenseRecordDTO;

public interface ExpenseRecordService {
	public void saveExpenseRecords(List<ExpenseRecordDTO> expenseRecords, String dateOfExpense);

	public List<String> getExpenseRecordSelectOptions();

	public List<ExpenseRecordDTO> getMonthlyExpenseRecordsForDateRange(LocalDate startDate, LocalDate endDate);
}
