package org.xmlcml.norma.document.sedar;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.BoxChart;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

public class SedarTest {

	private static final Logger LOG = Logger.getLogger(SedarTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
}

	@Test
	@Ignore // too long
	public void testReadPDF() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(Fixtures.TEST_SEDAR_DIR, "WesternZagros.pdf"));
		new File("target/sedar/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/sedar/WesternZagros.html"), 1);
	}
	
	@Test
	public void testExtractOrgChart() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_SEDAR_DIR, "westernZagros.g.11.7.svg"));
		BoxChart boxChart = new BoxChart(rawChart);
		boxChart.createChart();
		
	}
	
	@Test
	public void testExtractOrgChartBlackbird() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_SEDAR_DIR, "blackbird.g.8.8.svg"));
		BoxChart boxChart = new BoxChart(rawChart);
		boxChart.createChart();
		
	}
	
	@Test
	public void testExtractOrgChartPennWest() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_SEDAR_DIR, "pennwest.g.11.1.svg"));
		BoxChart boxChart = new BoxChart(rawChart);
		boxChart.createChart();
		
	}
	
	@Test
	public void testExtractOrgChartRooster() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_SEDAR_DIR, "rooster.g.7.6.svg"));
		BoxChart boxChart = new BoxChart(rawChart);
		boxChart.createChart();
		
	}
}
