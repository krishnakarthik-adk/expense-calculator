package com.kk.expensecalculator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPENSE_RECORD_SELECT_OPTIONS")
public class ExpenseRecordSelectDO {
	
	@Id
	@Column(name = "ID")
	private long id;
	@Column(name = "SELECT_NAME")
	private String selectName;
	@Column(name = "SELECT_OPTIONS")
	private String selectOptions;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getSelectOptions() {
		return selectOptions;
	}
	public void setSelectOptions(String selectOptions) {
		this.selectOptions = selectOptions;
	}
	
	

}
