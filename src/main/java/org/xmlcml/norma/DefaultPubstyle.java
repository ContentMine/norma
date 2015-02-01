package org.xmlcml.norma;

import org.xmlcml.norma.pubstyle.DefaultPubstyleReader;

public class DefaultPubstyle extends Pubstyle {
	
	public DefaultPubstyle() {
		super("default", "defaultTaggerLocation", new DefaultPubstyleReader());
	}

}
