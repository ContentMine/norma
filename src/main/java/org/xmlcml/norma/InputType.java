package org.xmlcml.norma;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** input types (not all are implemented yet)
 * 
 * @author pm286
 *
 */
public enum InputType {
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
	
	private static final Logger LOG = Logger.getLogger(InputType.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static boolean is(InputType type, String name) {
		return name.toLowerCase().endsWith(type.toString().toLowerCase());
	}
	
	
	public static InputType getInputType(String inputName) {
		inputName = inputName.toUpperCase();
		if (is(InputType.DOC, inputName)) {
			LOG.error("Cannot parse DOC");
			return null;
//			return InputType.DOC;
		}
		if (is(InputType.DOCX, inputName)) {
			LOG.error("Cannot parse DOCX");
			return null;
//			return InputType.DOCX;
		}
		if (is(InputType.HTML, inputName) || inputName.endsWith(".htm")) {
			return InputType.HTML;
		}
		if (is(InputType.SVG, inputName)) {
			return InputType.SVG;
		}
		if (is(InputType.PDF, inputName)) {
			return InputType.PDF;
		}
		if (is(InputType.XML, inputName)) {
			return InputType.XML;
		}
		if (is(InputType.XHTML, inputName)) {
			return InputType.XHTML;
		}
		return null;
	}
}


