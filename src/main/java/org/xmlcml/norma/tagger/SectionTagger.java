package org.xmlcml.norma.tagger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** tags sections based on regex for titles and content.
 * 
 * @author pm286
 *
 */
public class SectionTagger {

	private static final Logger LOG = Logger.getLogger(SectionTagger.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private String filename;

	public SectionTagger(String filename) {
		this.filename = filename;
	}
	
}
