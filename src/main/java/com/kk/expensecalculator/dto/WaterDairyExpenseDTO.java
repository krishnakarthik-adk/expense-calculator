package com.kk.expensecalculator.dto;

public class WaterDairyExpenseDTO {
	private String item;
	private int quantity;
	private int unitPrice;
	private int totalPrice;
	private String comments;
	private String dateOfExpense;
	
	public WaterDairyExpenseDTO() {
		
	}
	
	public WaterDairyExpenseDTO(String item, int quantity, int unitPrice, int totalPrice, String comments,
			String dateOfExpense) {
		super();
		this.item = item;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = totalPrice;
		this.comments = comments;
		this.dateOfExpense = dateOfExpense;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(String dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}
}
