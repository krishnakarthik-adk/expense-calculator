package com.kk.expensecalculator.util;

import java.time.LocalDate;

public class ExpenseCalDateUtils {
	public static LocalDate convertStringDateToLocalDate(String strDate) {
		return LocalDate.parse(strDate);
	}
}
