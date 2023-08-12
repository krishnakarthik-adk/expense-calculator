package com.kk.expensecalculator.dto;

import java.util.List;

public class ExpenseSummaryDTO {

	private List<ItemSummaryDTO> expenseSummaryDTOList;
	private int finalAmountPayable;

	public List<ItemSummaryDTO> getExpenseSummaryDTOList() {
		return expenseSummaryDTOList;
	}

	public void setExpenseSummaryDTOList(List<ItemSummaryDTO> expenseSummaryDTOList) {
		this.expenseSummaryDTOList = expenseSummaryDTOList;
	}

	public int getFinalAmountPayable() {
		return finalAmountPayable;
	}

	public void setFinalAmountPayable(int finalAmountPayable) {
		this.finalAmountPayable = finalAmountPayable;
	}

}
