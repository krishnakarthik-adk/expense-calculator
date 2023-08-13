package com.kk.expensecalculator.service;

import com.kk.expensecalculator.dto.ExpenseSummaryDTO;

public interface ExpenseSummary {
	
	public ExpenseSummaryDTO getExpenseSummaryForTheMonth(int month, int year);
}
