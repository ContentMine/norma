package org.xmlcml.norma.pubstyle.bmc;

import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.norma.tagger.bmc.HTMLBmcTagger;

public class BmcReader extends PubstyleReader {

	public BmcReader() {
		super();
	}

	public BmcReader(InputFormat type) {
		super(type);
	}

	@Override
	protected void addTaggers() {
		this.addTagger(InputFormat.HTML, new HTMLBmcTagger());
//		this.addTagger(InputFormat.XML, new XMLBmcTagger());
	}

}
