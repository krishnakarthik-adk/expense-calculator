package com.kk.expensecalculator.report;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportGenerator {
	public void generateExpensePDFReport(List<WaterDairyExpenseDTO> waterDairyExpenseDTOList,
			HttpServletResponse response) throws DocumentException, IOException;
}
