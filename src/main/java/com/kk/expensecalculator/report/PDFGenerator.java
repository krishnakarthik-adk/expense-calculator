package com.kk.expensecalculator.report;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.kk.expensecalculator.dto.WaterDairyExpenseDTO;
import com.kk.expensecalculator.util.ReportUtils;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PDFGenerator implements ReportGenerator {
	
	@Override
	public void generateExpensePDFReport(List<WaterDairyExpenseDTO> waterDairyExpenseDTOList,
			HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		Font fontTitle = fontConfig();
			
		// Create Object of Paragraph
		document.add(new Paragraph("Water and Dairy Expense Report", fontTitle));
		document.add(new Paragraph("Report generated on " + LocalDateTime.now()));
		
		document.add(new Paragraph("\n"));
		
		// Summary
		document.add(new Paragraph("Summary: ", fontTitle));
		document.add(new Paragraph("\n"));
		PdfPTable summaryTable = summaryTableConfig();
		ReportUtils.addTableHeaders(Arrays.asList("ITEM", "TOTAL PAYABLE"), summaryTable);
		
		// Group by to eliminate the individual filtering of items. To populate the summary table
		Map<String, Integer> summaryMap = new HashMap<>();
		Map<String, List<WaterDairyExpenseDTO>> expenseSummaryMap = waterDairyExpenseDTOList.stream().collect(Collectors.groupingBy(WaterDairyExpenseDTO::getItem));
		expenseSummaryMap.forEach((item, expenseList) -> {
			Integer itemExpesneSummary = calculateTotalPayable(expenseList.stream().map(WaterDairyExpenseDTO::getTotalPrice).toList());
			summaryMap.put(item, itemExpesneSummary);
		});
		
		addSummaryRows(summaryMap, summaryTable);

		document.add(summaryTable);
		document.add(Chunk.NEWLINE);
		
		// Details
		document.add(new Paragraph("Expense Details: ", fontTitle));
		document.add(new Paragraph("\n"));
		PdfPTable pdfPTable = setDetailsTableConfig();

		ReportUtils.addTableHeaders(Arrays.asList("ITEM", "QTY", "UNIT PRICE", "TOTAL PRICE", "DATE", "COMMENTS"), pdfPTable);
		addExpesneDetailRows(waterDairyExpenseDTOList, pdfPTable);
		document.add(pdfPTable);
		
		// Add footer
		ReportUtils.addFooter(document);
		
		document.close();
	}

	private void addSummaryRows(Map<String, Integer> summaryMap, PdfPTable summaryTable) {
		summaryMap.forEach((item, expense) -> {
			summaryTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			summaryTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			summaryTable.addCell(item);
			summaryTable.addCell(expense.toString());
		});
		
	}

	private PdfPTable summaryTableConfig() {
		PdfPTable summaryTable = new PdfPTable(2); // 2 Columns
		summaryTable.setWidthPercentage(100f);
		
		summaryTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		summaryTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		return summaryTable;
	}

	private Font fontConfig() {
		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(20);
		return fontTitle;
	}

	private PdfPTable setDetailsTableConfig() throws DocumentException {
		PdfPTable pdfPTable = new PdfPTable(6); // 6 columns
		pdfPTable.setWidthPercentage(100f);
		float[] columnWidths = new float[]{12f, 8f, 15f, 20f, 15f, 30f};
		pdfPTable.setWidths(columnWidths);
		return pdfPTable;
	}
	
	private void addExpesneDetailRows(List<WaterDairyExpenseDTO> waterDairyExpenseDTOList, PdfPTable pdfPTable) {
		waterDairyExpenseDTOList.stream().forEach(data -> {
			pdfPTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			pdfPTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			pdfPTable.addCell(data.getItem());
			pdfPTable.addCell(String.valueOf(data.getQuantity()));
			pdfPTable.addCell(String.valueOf(data.getUnitPrice()));
			pdfPTable.addCell(String.valueOf(data.getTotalPrice()));
			pdfPTable.addCell(data.getDateOfExpense().toString());
			pdfPTable.addCell(data.getComments());
		});
	}
	
	private Integer calculateTotalPayable(List<Integer> expenseList) {
		return expenseList.stream().reduce(0, (i, j) -> i+j);
	}

}