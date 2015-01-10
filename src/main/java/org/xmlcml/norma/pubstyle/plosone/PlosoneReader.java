package org.xmlcml.norma.pubstyle.plosone;

import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.norma.tagger.plosone.HTMLPlosoneTagger;
import org.xmlcml.norma.tagger.plosone.XMLPlosoneTagger;

public class PlosoneReader extends PubstyleReader {

	public PlosoneReader() {
		super();
	}

	public PlosoneReader(InputFormat type) {
		super(type);
	}

	@Override
	protected void addTaggers() {
		this.addTagger(InputFormat.HTML, new HTMLPlosoneTagger());
		this.addTagger(InputFormat.XML, new XMLPlosoneTagger());
	}


}
