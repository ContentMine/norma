package org.xmlcml.norma.pubstyle.bmc;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

public class BMCTest {


	@Test
	@Ignore // too long
	public void readProvisionalPDFTest() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(Fixtures.BMC_0277_PDF);
		new File("target/bmc/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/bmc/0277.html"), 1);
		
	}
}
