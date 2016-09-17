package org.xmlcml.norma.input.html;

import java.io.File;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.norma.NormaArgProcessor;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class HtmlCleaner {

	
	private static final Logger LOG = Logger.getLogger(HtmlCleaner.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String JSOUP = "jsoup";
	private static final String JTIDY = "jtidy";
	private static final String HTMLUNIT = "htmlunit";
	
	private NormaArgProcessor normaArgProcessor;
	private HtmlElement htmlElement;
	private HtmlFactory htmlFactory;
	private boolean testWellFormed;

	public HtmlCleaner() {
		this.testWellFormed = false;
		createHtmlFactory();
	}

	public HtmlCleaner(NormaArgProcessor argProcessor) {
		this();
		this.normaArgProcessor = argProcessor;
	}

	private void createHtmlFactory() {
		htmlFactory = new HtmlFactory();
		// change these to take input from args.xml
		htmlFactory.setContentList(Arrays.asList(new String[]{
				"noscript", "script", "style", "iframe", "button", "fieldset", "label"}));
		htmlFactory.setNoContentList(Arrays.asList(new String[]{"input", "link", "form"}));
		htmlFactory.setBalanceList(Arrays.asList(new String[]{"meta"}));
		htmlFactory.setUseJsoup(true);
	}

	/**
	 * 
	 * @param optionValue
	 * @return
	 */
	public HtmlElement cleanHTML2XHTML(String optionValue) {
		
		if (!JSOUP.equalsIgnoreCase(optionValue)) {
			LOG.warn("tidying option not supported:"+optionValue);
		}
		CTree currentCMTree = normaArgProcessor.getCurrentCMTree();
		File inputFile = normaArgProcessor.checkAndGetInputFile(currentCMTree);

		return cleanHtmlFile(inputFile);
	}

	public HtmlElement cleanHtmlFile(File inputFile) {
		htmlElement = null;
		// assume it's well formed already?
		// this may cause more problems than it solves
		if (testWellFormed) {
			try {
				Element element = XMLUtil.parseQuietlyToDocumentWithoutDTD(inputFile).getRootElement();
				htmlElement = HtmlElement.create(element);
			} catch (Exception e) {
				
			}
		}
		// only do this if not well formed
		if (htmlElement == null && inputFile != null) {
			try {
				htmlElement = htmlFactory.parse(inputFile);
			} catch (Exception e) {
				throw new RuntimeException("Cannot transform HTML "+inputFile, e);
			}
		}
		return htmlElement;
	}

	public HtmlElement getHtmlElement() {
		return htmlElement;
	}

	public boolean isTestWellFormed() {
		return testWellFormed;
	}

	/** initailly parse as well-formed and only tidy if not.
	 * this may cause as many problems as it solves.
	 * 
	 * @param testWellFormed
	 */
	public void setTestWellFormed(boolean testWellFormed) {
		this.testWellFormed = testWellFormed;
	}



}
