package org.xmlcml.norma.txt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.euclid.IntArray;
import org.xmlcml.norma.Fixtures;

public class TxtReaderTest {
	
	public static final Logger LOG = Logger.getLogger(TxtReaderTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}


	@Test
	public void testReadHal2() throws Exception {
		File file = new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt");
		List<String> lines = FileUtils.readLines(file);
		Assert.assertEquals("lines", 3548, lines.size());
		IntArray pageLines = readPages(lines, "EVEN_ODD");
	}

	private IntArray readPages(List<String> lines, String evenOdd) {
		Pattern EVEN = Pattern.compile("(\\d+)\\s+.*");
		Pattern ODD = Pattern.compile(".*\\s+(\\d+)");
		Pattern currentPattern = ODD;
		IntArray lineNumbers = new IntArray();
		int currentNumber = -1;
		int currentPage = -1;
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			Matcher matcher =currentPattern.matcher(line);
			if (matcher.matches()) {
//				LOG.debug(line);
				lineNumbers.addElement(i);
				currentPattern = (currentPattern.equals(ODD) ? EVEN : ODD);
				int page = Integer.parseInt(matcher.group(1));
				LOG.debug(page);
				currentNumber = i;
				if (currentPage == page-1) {
					LOG.debug("PAGE");
				}
				currentPage = page;
			}
		}
		LOG.debug(lineNumbers);
		return null;
	}
	
	@Test
	public void testReadHal2Pattern() throws Exception {
		File file = new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt");
		List<String> lines = FileUtils.readLines(file);
		DecimalSectionContainer decimalSectionContainer = new DecimalSectionContainer();
		decimalSectionContainer.addLines(lines);
	}

	@Test
	public void testReadHal2Pages() throws Exception {
		File file = new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt");
		List<String> lines = FileUtils.readLines(file);
		DecimalSectionContainer decimalSectionContainer = new DecimalSectionContainer();
		decimalSectionContainer.addLines(lines);
		List<DecimalSection> pages = decimalSectionContainer.extractPages();
		for (DecimalSection page : pages) {
			LOG.debug(page);
		}
	}

	@Test
	public void testReadHal2Toc() throws Exception {
		File file = new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt");
		List<String> lines = FileUtils.readLines(file);
		DecimalSectionContainer decimalSectionContainer = new DecimalSectionContainer();
		decimalSectionContainer.addLines(lines);
		List<DecimalSection> chapters = decimalSectionContainer.extractToc();
		LOG.debug("=======================");
		for (DecimalSection chapter : chapters) {
			LOG.debug(chapter);
		}
	}

	@Test
	public void testReadHal2Chapter() throws Exception {
		File file = new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt");
		List<String> lines = FileUtils.readLines(file);
		DecimalSectionContainer decimalSectionContainer = new DecimalSectionContainer();
		decimalSectionContainer.addLines(lines);
		List<DecimalSection> chapters = decimalSectionContainer.extractChapters();
		LOG.debug("=======================");
		for (DecimalSection chapter : chapters) {
			LOG.debug(chapter);
		}
	}

}
