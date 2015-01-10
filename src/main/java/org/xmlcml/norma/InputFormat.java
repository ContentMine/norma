package org.xmlcml.norma;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** input types (not all are implemented yet)
 * 
 * @author pm286
 *
 */
public enum InputFormat {
	DOC,
	DOCX,
	HTML,
	LATEX,
	PDF,
	PNG,
	PPT,
	SVG,
	XHTML,
	XML;
	
	private static final Logger LOG = Logger.getLogger(InputFormat.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static boolean is(InputFormat type, String name) {
		return name.toLowerCase().endsWith(type.toString().toLowerCase());
	}
	
	
	public static InputFormat getInputType(String inputName) {
		inputName = inputName.toUpperCase();
		if (is(InputFormat.DOC, inputName)) {
			LOG.error("Cannot parse DOC");
			return null;
//			return InputType.DOC;
		}
		if (is(InputFormat.DOCX, inputName)) {
			LOG.error("Cannot parse DOCX");
			return null;
//			return InputType.DOCX;
		}
		if (is(InputFormat.HTML, inputName) || inputName.endsWith(".htm")) {
			return InputFormat.HTML;
		}
		if (is(InputFormat.SVG, inputName)) {
			return InputFormat.SVG;
		}
		if (is(InputFormat.PDF, inputName)) {
			return InputFormat.PDF;
		}
		if (is(InputFormat.XML, inputName)) {
			return InputFormat.XML;
		}
		if (is(InputFormat.XHTML, inputName)) {
			return InputFormat.XHTML;
		}
		return null;
	}
}


