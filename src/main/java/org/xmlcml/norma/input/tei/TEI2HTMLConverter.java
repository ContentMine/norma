package org.xmlcml.norma.input.tei;

import java.io.File;

import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.grobid.GrobidElement;
import org.xmlcml.norma.grobid.GrobidTEIElement;

public class TEI2HTMLConverter {

	public TEI2HTMLConverter() {
		
	}
	
	public HtmlElement convertTEI2HtmlElement(File teiFile) {
		GrobidTEIElement teiElement = GrobidElement.readTEI(teiFile);
		return teiElement.createHTML();
	}

}
