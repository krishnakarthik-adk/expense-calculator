package com.kk.expensecalculator.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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

		response.setContentType("application/pdf");
		response.setHeader(HTTP_HEADERS.HEADER_KEY.toString(), HTTP_HEADERS.HEADER_VALUE.toString());
		
		LocalDate startDate = ExpenseCalDateUtils.convertStringDateToLocalDate(strStartDate);
		LocalDate endDate = ExpenseCalDateUtils.convertStringDateToLocalDate(strEndDate);

		List<WaterDairyExpenseDTO> waterDairyExpenseDTOList = waterAndDairyExpenseService.getWaterAndDairyExpenseDataForDateRange(startDate, endDate);

		pdfGenerator.generateExpensePDFReport(waterDairyExpenseDTOList, strStartDate, strEndDate, response);

	}
}
