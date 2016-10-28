package org.xmlcml.norma.pubstyle.astrophysj;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGPolyline;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.graphics.svg.objects.SVGPlot;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

import junit.framework.Assert;

public class AstrophysTest {

	@Test
	@Ignore // too long
	public void testReadPDF() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(NormaFixtures.TEST_ASTROPHYS_DIR, "0004-637X_754_2_85.pdf"));
		new File("target/astrophys/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/astrophys/285.html"), 1);
	}
	
	@Test
	@Ignore // plot lost
	public void testExtractPlot() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(NormaFixtures.TEST_ASTROPHYS_DIR, "754_2_85.fig1.svg"));
		SVGPlot plot = new SVGPlot(rawChart);
		plot.createPlot();
		List<SVGText> textList = plot.getSVGTextList();
		List<SVGPolyline> lineList = plot.getSVGPolylineList();
		int[] sizes = {294, 178, 178};
		Assert.assertEquals(sizes.length,  lineList.size());
		int i = 0;
		for (SVGPolyline line : lineList) {
			Assert.assertEquals(sizes[i++], line.getReal2Array().size());
//			System.out.println(line.size()+"/"+line.getReal2Array().format(1));
		}
	}
}
