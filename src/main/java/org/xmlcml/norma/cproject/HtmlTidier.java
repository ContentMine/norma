package org.xmlcml.norma.cproject;

import java.io.File;

import org.xmlcml.norma.NormaArgProcessor;

public class HtmlTidier {

	private File cproject;

	public HtmlTidier(File cproject) {
		this.cproject = cproject;
	}
	
	/** uses XSL to remove publisher junk and tidy other infelicities.
	 * 
	 * @param tidier symbol pointing to stylesheet
	 */
	public void htmlTidy(String tidier) {
		new NormaArgProcessor("--project "+cproject+" -i fulltext.html -o fulltext.xhtml --html "+tidier).runAndOutput();
	}
	public void xslTidy(String style) {
		new NormaArgProcessor("--project "+cproject+" -i fulltext.xhtml -o scholarly.html --transform "+style).runAndOutput();
	}


}
