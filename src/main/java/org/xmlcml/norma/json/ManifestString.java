package org.xmlcml.norma.json;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** holds a string value with a key.
 *  
 * @author pm286
 *
 */
public class ManifestString {

	
	private static final Logger LOG = Logger.getLogger(ManifestString.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private String key;
	private String value;

	public ManifestString(String key) {
		this.setKey(key);
	}

	private void setKey(String key) {
		this.key = key;
	}

	public ManifestString(String key, String value) {
		this.setKey(key);
		this.setValue(value);
	}

	private void setValue(String value) {
		this.value = value;
	}


}
