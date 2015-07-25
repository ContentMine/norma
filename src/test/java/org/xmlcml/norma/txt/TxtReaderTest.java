package org.xmlcml.norma.txt;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.euclid.IntArray;
import org.xmlcml.html.HtmlElement;
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
	public void testReadHal2Pages() throws Exception {
		AnnotatedLineContainer pageHeadingContainer = createAnnotatedLineContainer(new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt"));
		AnnotatedLineContainer pages = pageHeadingContainer.extractPageHeadings();
		for (AnnotatedLine page : pages) {
			LOG.debug(">p>"+page);
		}
	}

	@Test
	public void testReadHal2Chapter() throws Exception {
		AnnotatedLineContainer annotatedLineContainer = createAnnotatedLineContainer(new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt"));
		AnnotatedLineContainer chapterHeadings = annotatedLineContainer.extractChapterHeadings(0, annotatedLineContainer.size());
		LOG.debug("=======================");
		for (AnnotatedLine chapterHeading : chapterHeadings) {
			LOG.debug(chapterHeading);
		}
		List<Chapter> chapterList = annotatedLineContainer.extractChapters(chapterHeadings);
		Assert.assertEquals("chapters", 8, chapterList.size());
		Assert.assertEquals("chapters", 707, chapterList.get(0).size());
		Assert.assertEquals("chapters", 396, chapterList.get(7).size());
		LOG.debug("-----------------------");
	}

	@Test
	public void testReadHal2ChapterSections() throws Exception {
		AnnotatedLineContainer annotatedLineContainer = createAnnotatedLineContainer(new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt"));
		List<Chapter> chapterList = annotatedLineContainer.extractChapters();
		for (Chapter chapter : chapterList) {
			LOG.debug("-----------------------");
			List<AnnotatedLine> sectionHeadings = chapter.getDecimalSectionHeadings();
			Set<String> used = new HashSet<String>();
			
			for (int i = 0; i < sectionHeadings.size(); i++) {
				AnnotatedLine sectionHeading = sectionHeadings.get(i);
				AnnotatedLine lastSectionHeading =  (i == 0) ? null : sectionHeadings.get(i - 1);
				IntArray section = sectionHeading.getLeftSection();
				IntArray lastSection = lastSectionHeading == null ? null : lastSectionHeading.getLeftSection();
				if (section.size() == 1 || used.contains(section.toString())) {
					// probable page or running header
					continue;
				}
				if (!AnnotatedLine.checkArrayIncrement(i, lastSection, section)) {
					LOG.trace("skipping "+lastSection+"=>"+section);
					continue;
				}
				used.add(section.toString());
				LOG.debug("sec>"+section);
			}
		}
		Chapter chapter0 = chapterList.get(0);
		HtmlElement element = chapter0.getOrCreateHtmlElement();
		element.debug("html");
		LOG.debug("-----------------------");
	}

	@Test
	public void testReadHal2Toc() throws Exception {
		AnnotatedLineContainer annotatedLineContainer = createAnnotatedLineContainer(
				new File(Fixtures.TEST_NORMA_DIR, "txt/hal2.pdf.txt"));
		int startToc = annotatedLineContainer.readToL1R1(0);
		int startChapter = annotatedLineContainer.readToChapter(startToc);
		Toc toc = annotatedLineContainer.readToc(startToc, startChapter);
		toc.checkToc();
		for (AnnotatedLine tocLine : toc) {
			LOG.trace(">t>"+tocLine);
		}
//		List<AnnotatedLine> chapterLines = annotatedLineContainer.extractChapterHeadingList(startChapter, annotatedLineContainer.size());
	}

	private AnnotatedLineContainer createAnnotatedLineContainer(File file) throws IOException {
		List<String> lines = FileUtils.readLines(file);
		AnnotatedLineContainer annotatedLineContainer = new AnnotatedLineContainer();
		annotatedLineContainer.addLines(lines);
		return annotatedLineContainer;
	}

}
