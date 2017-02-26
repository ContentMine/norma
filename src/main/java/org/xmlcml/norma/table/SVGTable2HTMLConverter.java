package org.xmlcml.norma.table;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.svg2xml.table.TableContentCreator;

public class SVGTable2HTMLConverter {
	private static final Logger LOG = Logger.getLogger(SVGTable2HTMLConverter.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}

	private File inputFile;
	private File outputFile;
	private TableContentCreator tableContentCreator;
	private File outputDir;
	private HtmlElement bodyElement;
	private HtmlElement titleElement;
	private HtmlElement headerElement;
	private HtmlElement footerElement;

	public SVGTable2HTMLConverter() {
		tableContentCreator = new TableContentCreator(); 
	}

	public void readInput(File inputFile) {
		this.inputFile = inputFile;
	}
	
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	
	public void convert() {
		getOrCreateOutputDir();
		tableContentCreator.markupAndOutputTable(inputFile, outputDir);
		bodyElement = tableContentCreator.getOrCreateTableBodySection().getOrCreatePhraseListList().toHtml();
		titleElement = tableContentCreator.getOrCreateTableTitleSection().getOrCreatePhraseListList().toHtml();
		headerElement = tableContentCreator.getOrCreateTableHeaderSection().getOrCreatePhraseListList().toHtml();
		footerElement = tableContentCreator.getOrCreateTableFooterSection().getOrCreatePhraseListList().toHtml();
	}

	private void getOrCreateOutputDir() {
		if (outputDir == null && inputFile != null) {
			outputDir = inputFile.getParentFile();
		}
	}

	public HtmlElement getBodyElement() {
		return bodyElement;
	}

	public HtmlElement getTitleElement() {
		return titleElement;
	}

	public HtmlElement getHeaderElement() {
		return headerElement;
	}

	public HtmlElement getFooterElement() {
		return footerElement;
	}
}
