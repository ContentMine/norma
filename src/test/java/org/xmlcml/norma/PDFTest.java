package org.xmlcml.norma;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

public class PDFTest {

	@Test
	public void testReadPDFNoTag() {
		String[] args = {
				"-i", new File(Fixtures.TEST_SEDAR_DIR, "WesternZagros.pdf").toString(),
				"-o", new File("target/WesternZagros/").toString(),
		};
		Norma norma = new Norma();
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(Fixtures.TEST_SEDAR_DIR, "WesternZagros.pdf"));
		new File("target/sedar/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/sedar/WesternZagros.html"), 1);

	}
}
