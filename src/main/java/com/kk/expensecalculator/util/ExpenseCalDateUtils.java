package com.kk.expensecalculator.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpenseCalDateUtils {

	final static String INPUT_DATE_PATTERN = "M/d/yyyy";
	final static String OUTPUT_DATE_PATTERN = "dd-MMM-yyyy";

	public static LocalDate convertStringDateToLocalDate(String strDate) {
		return LocalDate.parse(strDate);
	}

	public static String formatDate(String strDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_PATTERN);
		LocalDate date = convertStringDateToLocalDate(strDate);

		return date.format(formatter);

	}
	
	public static LocalDate convertStringDateToLocalDateFinal(String dateString, DateTimeFormatter formatter) {
		return LocalDate.parse(dateString, formatter);
	}
	
	public static LocalDate convertInputStringDateToLocalDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(INPUT_DATE_PATTERN);
		return LocalDate.parse(dateString, formatter);
	}
	
	public static List<LocalDate> convertStringDateRangeToLocalDateRange(List<String> selectedDateRange) {
		
		List<LocalDate> dateRange = new ArrayList<>();
		selectedDateRange.stream().forEach(d -> {
			dateRange.add(convertInputStringDateToLocalDate(d));
		});
		
		return dateRange;
	}
}
