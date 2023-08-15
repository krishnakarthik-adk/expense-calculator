package com.kk.expensecalculator.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPENSE_RECORD")
public class ExpenseRecordDO {

	@Id
	@GeneratedValue(generator = "EXPENSE_RECORD")
	@SequenceGenerator(name = "EXPENSE_RECORD", sequenceName = "EXPENSE_RECORD_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ITEM")
	private String item;
	@Column(name = "AMOUNT")
	private int amount;
	@Column(name = "EXPENSE_CATEGORY")
	private String expenseCategory;

	@Column(name = "NOTES")
	private String notes;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(String expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(LocalDate dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

}
