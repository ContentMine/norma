package org.xmlcml.norma.input.pdf;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDF2TXTConverter {

	public PDF2TXTConverter() {
		
	}
	
	public String readPDF(InputStream filesInputStream, boolean sortByPosition) throws IOException {
		PDDocument doc=PDDocument.load(filesInputStream);
		PDFTextStripper pdfStripper=new PDFTextStripper();
		pdfStripper.setSortByPosition(sortByPosition);
		String s = pdfStripper.getText(doc);
//		char[] origText=pdfStripper.getText(doc).toCharArray();
		doc.close();
		return s;
//		return new String(origText);
	}
		 
}
