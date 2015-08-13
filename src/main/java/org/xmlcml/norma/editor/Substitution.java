package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Substitution {

	public static final Logger LOG = Logger.getLogger(Substitution.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private String original;
	private String edited;

	public Substitution() {
		
	}
	
	public Substitution(String original, String edited) {
		this.original = original;
		this.edited = edited;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	
}
