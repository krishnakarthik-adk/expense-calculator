package com.kk.expensecalculator.dto;

public class ItemSummaryDTO {

	private String item;
	private int amountPayabalePerItem;
	private int quantity;
	private int unitPrice;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

}
