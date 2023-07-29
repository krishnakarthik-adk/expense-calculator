package com.kk.expensecalculator.util;

import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class ReportUtils {
	
	public static void addTableHeaders(List<String> tableHeaders, PdfPTable pdfPTable) {
		tableHeaders.stream().forEach(header -> {
			PdfPCell columnHeader = new PdfPCell();
			columnHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
			
			columnHeader.setPhrase(new Phrase(header));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			columnHeader.setVerticalAlignment(Element.ALIGN_CENTER);
			
			pdfPTable.addCell(columnHeader);
		});
	}
	
	public static void addFooter(Document document) throws DocumentException {
		Paragraph footerNoteParagraph = new Paragraph("-------------------------------------------------- End of Report --------------------------------------------------");
		footerNoteParagraph.setAlignment(Element.ALIGN_MIDDLE);
		document.add(Chunk.NEWLINE);
		document.add(footerNoteParagraph);
	}
	
}
