package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SpaceElement extends AbstractEditorElement implements RegexComponent {

	private static final String COUNT = "count";
	public static final Logger LOG = Logger.getLogger(SpaceElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "space";

	public SpaceElement() {
		super(TAG);
	}

	public String createRegex() {
		String s = "\\s";
		String count = this.getAttributeValue(COUNT);
		if (count != null) {
			s += count;
		}
		LOG.trace(this.toXML()+":"+s);
		return s;
	}

	public String getCount() {
		String count = this.getAttributeValue(COUNT);
		return count;
	}



}
