package org.xmlcml.norma.tagger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** tags sections based on regex for titles and content.
 * 
 * @author pm286
 *
 */
public class SectionTaggerX {

	private static final Logger LOG = Logger.getLogger(SectionTaggerX.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private String filename;

	public SectionTaggerX(String filename) {
		this.filename = filename;
	}
	
}
