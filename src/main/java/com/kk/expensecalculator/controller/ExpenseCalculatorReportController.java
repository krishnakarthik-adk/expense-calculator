package com.kk.expensecalculator.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.report.PDFGenerator;
import com.kk.expensecalculator.service.WaterAndDairyExpenseService;
import com.kk.expensecalculator.util.ExpenseCalDateUtils;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1")
public class ExpenseCalculatorReportController {

	private static final Logger log = LogManager.getLogger(ExpenseCalculatorReportController.class);
	
	@Autowired
	WaterAndDairyExpenseService waterAndDairyExpenseService;

	@Autowired
	PDFGenerator pdfGenerator;

	public enum HTTP_HEADERS {
		HEADER_KEY("Content-Disposition"), HEADER_VALUE("attachment; inline;filename=ExpenseReport.pdf");

		public final String headers;

		HTTP_HEADERS(String headers) {
			this.headers = headers;
		}
	}

	@GetMapping(value = "/generatePDFReport")
	public void generatePDFReport(HttpServletResponse response, @RequestParam String strStartDate, @RequestParam String strEndDate) throws DocumentException, IOException {

		log.info("Generating PDF report for the data range strStartDate: {}, strEndDate: {}", strStartDate, strStartDate);
		
		response.setContentType("application/pdf");
		response.setHeader(HTTP_HEADERS.HEADER_KEY.toString(), HTTP_HEADERS.HEADER_VALUE.toString());
		
		// We initialize the startDate & endDate to LocalDate.now() if we don't receive the date range
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		List<LocalDate> dateRange = new ArrayList<>();
		
		if(StringUtils.isNotBlank(strStartDate) && StringUtils.isNotBlank(strEndDate)) {
			dateRange = ExpenseCalDateUtils.convertStringDateRangeToLocalDateRange(Arrays.asList(strStartDate, strEndDate), ExpenseCalDateUtils.INPUT_DATE_PATTERN);
			startDate = dateRange.get(0);
			endDate = dateRange.get(1);
		}		

		List<WaterDairyExpenseDTO> waterDairyExpenseDTOList = waterAndDairyExpenseService.getWaterAndDairyExpenseDataForDateRange(startDate, endDate);

		pdfGenerator.generateExpensePDFReport(waterDairyExpenseDTOList, strStartDate, strEndDate, response);
		
		log.info("Successfully generated the PDF report for the data range strStartDate: {}, strEndDate: {}", strStartDate, strStartDate);

	}
}
