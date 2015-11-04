package org.xmlcml.norma.input.html;

import java.io.File;
import java.util.Arrays;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CMDir;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.norma.NormaArgProcessor;

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

	public HtmlCleaner(NormaArgProcessor argProcessor) {
		this.normaArgProcessor = argProcessor;
		createHtmlFactory();
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
		
		if (!JSOUP.equals(optionValue)) {
			LOG.warn("tidying option not supported:"+optionValue);
		}
		CMDir currentCMDir = normaArgProcessor.getCurrentCMDir();
		File inputFile = normaArgProcessor.checkAndGetInputFile(currentCMDir);

		htmlElement = null;
		try {
			htmlElement = htmlFactory.parse(inputFile);
		} catch (Exception e) {
			throw new RuntimeException("Cannot transform HTML "+inputFile, e);
		}
		return htmlElement;
	}

	public HtmlElement getHtmlElement() {
		return htmlElement;
	}


}
