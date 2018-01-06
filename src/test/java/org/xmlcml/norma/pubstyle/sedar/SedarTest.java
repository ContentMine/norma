package org.xmlcml.norma.pubstyle.sedar;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.jempbox.impl.XMLUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.graphics.html.HtmlElement;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.objects.SVGBoxChart;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;

public class SedarTest {

	private static final Logger LOG = Logger.getLogger(SedarTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
}

	@Test
	@Ignore // too long
	public void testReadPDF() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(NormaFixtures.TEST_SEDAR_DIR, "WesternZagros.pdf"));
		new File("target/sedar/").mkdirs();
//		XMLUtil.debug(htmlElement, new FileOutputStream("target/sedar/WesternZagros.html"), 1);
	}
	
	@Test
	@Ignore // file // throw NPE somewhere
	public void testExtractOrgChart() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(NormaFixtures.TEST_SEDAR_DIR, "westernZagros.g.11.7.svg"));
		Assert.assertNotNull(rawChart);
		SVGBoxChart boxChart = new SVGBoxChart(rawChart);
		boxChart.createChart();
		
	}
	
	@Test
	@Ignore
	public void testExtractOrgChartBlackbird() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(NormaFixtures.TEST_SEDAR_DIR, "blackbird.g.8.8.svg"));
		SVGBoxChart boxChart = new SVGBoxChart(rawChart);
		boxChart.createChart();
		
	}
	
	@Test
	@Ignore
	public void testExtractOrgChartPennWest() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(NormaFixtures.TEST_SEDAR_DIR, "pennwest.g.11.1.svg"));
		SVGBoxChart boxChart = new SVGBoxChart(rawChart);
		boxChart.createChart();
		
	}
	
	@Test
	@Ignore
	public void testExtractOrgChartRooster() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(NormaFixtures.TEST_SEDAR_DIR, "rooster.g.7.6.svg"));
		SVGBoxChart boxChart = new SVGBoxChart(rawChart);
		boxChart.createChart();
		
	}
}
