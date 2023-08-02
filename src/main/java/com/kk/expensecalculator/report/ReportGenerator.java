package com.kk.expensecalculator.report;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportGenerator {

	void generateExpensePDFReport(List<WaterDairyExpenseDTO> waterDairyExpenseDTOList, String strStartDate,
			String strEndDate, HttpServletResponse response) throws DocumentException, IOException;
}
