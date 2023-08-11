package com.kk.expensecalculator.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCalDateUtils {

	public final static String INPUT_DATE_PATTERN = "M/d/yyyy";
	public final static String OUTPUT_DATE_PATTERN = "dd-MMM-yyyy";

	public static String formatDateForOutput(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_PATTERN);
		return date.format(formatter);
	}
	
	public static LocalDate convertStringDateToLocalDate(String dateString, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateString, formatter);
	}
	
	public static List<LocalDate> convertStringDateRangeToLocalDateRange(List<String> selectedDateRange, String pattern) {
		
		List<LocalDate> dateRange = new ArrayList<>();
		selectedDateRange.stream().forEach(d -> {
			dateRange.add(convertStringDateToLocalDate(d, pattern));
		});
		
		return dateRange;
	}
}
