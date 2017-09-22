package org.xmlcml.norma.pubstyle.astrophysj;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.graphics.svg.GraphicsElement;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGPolyline;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.graphics.svg.objects.SVGPlot;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

public class AstrophysTest {

	@Test
	@Ignore // too long
	public void testReadPDF() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(NormaFixtures.TEST_ASTROPHYS_DIR, "0004-637X_754_2_85.pdf"));
		new File("target/astrophys/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/astrophys/285.html"), 1);
	}
	
}
