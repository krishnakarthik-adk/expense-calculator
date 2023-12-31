package com.kk.expensecalculator.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.kk.expensecalculator.util.ExpenseCalDateUtils;
import com.kk.expensecalculator.util.ExpenseCalculatorUtils;
import com.kk.expensecalculator.util.ReportUtils;

import jakarta.servlet.http.HttpServletResponse;

@Component
@PropertySource(value = "classpath:config/pdf.properties")
public class PDFGenerator implements ReportGenerator {
	
	private static final Logger log = LogManager.getLogger(PDFGenerator.class);
	
	@Value("${pdf.filename}")
	private String fileName;
	@Value("${pdf.generatedfile.location}")
	private String fileLocation;
	@Value("${pdf.doc.header}")
	private String header;
	@Value("${pdf.doc.subheader}")
	private String subHeader;
	@Value("${pdf.doc.summary}")
	private String summary;
	@Value("${pdf.doc.expenseDetails}")
	private String expenseDetails;
	@Value("${pdf.expensedetails.colheaders}")
	private String expenseDetailsColHeaders;
	@Value("${pdf.summary.colheaders}")
	private String summaryColHeaders;
	@Value("${pdf.expense.period}")
	private String expensePeriod;
	@Value("${pdf.total.amount}")
	private String totalAmount;
	
	@Override
	public void generateExpensePDFReport(List<WaterDairyExpenseDTO> waterDairyExpenseDTOList,
			String strStartDate, String strEndDate, HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		// To display the pdf in-line when the Generate PDF end-point is accessed.
		PdfWriter.getInstance(document, response.getOutputStream());
		// Save the generated PDF file to disk for sending the attachment in the email.
		PdfWriter.getInstance(document, new FileOutputStream(fileLocation + "\\" + fileName));
		document.open();

		Font fontTitle = fontConfig();
			
		// Create Object of Paragraph
		document.add(new Paragraph(header, fontTitle));
		document.add(new Paragraph(subHeader + ": " + LocalDateTime.now()));
		
		document.add(new Paragraph("\n"));
		
		// Summary table
		// Group by to eliminate the individual filtering of items. To populate the summary table
		Map<String, Integer> summaryMap = new HashMap<>();
		Map<String, List<WaterDairyExpenseDTO>> expenseSummaryMap = waterDairyExpenseDTOList.stream()
				.collect(Collectors.groupingBy(WaterDairyExpenseDTO::getItem));
		expenseSummaryMap.forEach((item, expenseList) -> {
			Integer itemExpesneSummary = ExpenseCalculatorUtils.calculateTotalPayable(
					expenseList.stream().map(WaterDairyExpenseDTO::getTotalPrice).toList());
			summaryMap.put(item, itemExpesneSummary);
		});
		
		int finalPayable = summaryMap.values().stream().reduce(0, (i, j) -> i + j);
		
		document.add(new Paragraph(summary, fontTitle));
		LocalDate startDate = ExpenseCalDateUtils.convertStringDateToLocalDate(strStartDate, ExpenseCalDateUtils.INPUT_DATE_PATTERN);
		LocalDate endDate = ExpenseCalDateUtils.convertStringDateToLocalDate(strEndDate, ExpenseCalDateUtils.INPUT_DATE_PATTERN);
		
		log.info("Date range after conversion to LocalDates for the pdf report, startDate: {}, endDate: {}", startDate, endDate);
		
		document.add(new Paragraph(expensePeriod + " " + ExpenseCalDateUtils.formatDateForOutput(startDate) + " to " + ExpenseCalDateUtils.formatDateForOutput(endDate)));
		document.add(new Paragraph(totalAmount + " " + finalPayable));
		document.add(new Paragraph("\n"));
		
		log.info("Initializing the config for the PDFTable.");
		
		PdfPTable summaryTable = summaryTableConfig();
		// Summary headers
		ReportUtils.addTableHeaders(ReportUtils.getColumnHeaders(summaryColHeaders), summaryTable);
		
		// Summary rows
		addSummaryRows(summaryMap, summaryTable);
		document.add(summaryTable);

		document.add(Chunk.NEWLINE);
		
		// Expense Details
		document.add(new Paragraph(expenseDetails + ":", fontTitle));
		document.add(new Paragraph("\n"));
		PdfPTable pdfPTable = setDetailsTableConfig();
		
		ReportUtils.addTableHeaders(ReportUtils.getColumnHeaders(expenseDetailsColHeaders), pdfPTable);
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
			pdfPTable.addCell(ExpenseCalDateUtils.formatDateForOutput(LocalDate.parse(data.getDateOfExpense()))); // To Format
			pdfPTable.addCell(data.getComments());
		});
	}
	
}
