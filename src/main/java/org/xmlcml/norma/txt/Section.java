package org.xmlcml.norma.txt;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.html.HtmlDiv;
import org.xmlcml.graphics.html.HtmlElement;

public class Section extends AbstractSection {

	private static final Logger LOG = Logger.getLogger(Section.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private List<AnnotatedLine> lines;

	public Section(AnnotatedLineContainer lineContainer) {
		this.parentLineContainer = lineContainer;
	}

	public int getFirstLineNumber() {
		return localLineContainer.getFirstLineNumber();
	}

	public HtmlElement getOrCreateHtmlElement() {
		HtmlDiv htmlDiv = new HtmlDiv();
//		for (int i = 0; i < loc)
		return htmlDiv;
	}

}
