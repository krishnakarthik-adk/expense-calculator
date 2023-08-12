package com.kk.expensecalculator.dto;

public class ItemSummaryDTO {

	private String item;
	private int amountPayabalePerItem;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getAmountPayabalePerItem() {
		return amountPayabalePerItem;
	}

	public void setAmountPayabalePerItem(int amountPayabalePerItem) {
		this.amountPayabalePerItem = amountPayabalePerItem;
	}

}
