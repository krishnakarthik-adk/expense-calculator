package com.kk.expensecalculator.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseCalDateUtils {

	final static String DATE_PATTERN = "dd-MMM-yyyy";

	public static LocalDate convertStringDateToLocalDate(String strDate) {
		return LocalDate.parse(strDate);
	}

	public static String formatDate(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDate date = convertStringDateToLocalDate(strDate);

		return date.format(formatter);

	}
}
