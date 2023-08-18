package com.kk.expensecalculator.service;

import com.kk.expensecalculator.dto.ExpenseSummaryDTO;

public interface ExpenseSummary {
	
	public ExpenseSummaryDTO getWaterAndDairyMonthlyExpense(int month, int year);

	public ExpenseSummaryDTO getMonthlyExpense(int month, int year);

	
}
