package com.kk.expensecalculator.service;

import java.util.List;

import com.kk.expensecalculator.dto.ExpenseRecordDTO;

public interface ExpenseRecordService {
	public void saveExpenseRecords(List<ExpenseRecordDTO> expenseRecords, String dateOfExpense);
}
