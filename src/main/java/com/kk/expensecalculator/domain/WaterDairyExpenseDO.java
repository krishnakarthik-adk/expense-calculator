package com.kk.expensecalculator.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "WATER_DAIRY_EXPENSE")
public class WaterDairyExpenseDO {

	@Id
	@GeneratedValue(generator = "WATER_DAIRY_EXPENSE")
	@SequenceGenerator(name = "WATER_DAIRY_EXPENSE", sequenceName = "WATER_DAIRY_EXPENSE_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	@Column(name = "ITEM_NAME")
	private String item;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "UNIT_PRICE")
	private int unitPrice;
	@Column(name = "TOTAL_PRICE")
	private int totalPrice;
	@Column(name = "COMMENTS")
	private String comments;
	@Column(name = "DATE_OF_EXPENSE")
	private LocalDate dateOfExpense;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(LocalDate dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

}
