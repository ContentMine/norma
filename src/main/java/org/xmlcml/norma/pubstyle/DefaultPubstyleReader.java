package org.xmlcml.norma.pubstyle;

import java.util.ArrayList;
import java.util.List;

import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.norma.tagger.plosone.HTMLPlosoneTagger;
import org.xmlcml.norma.tagger.plosone.XMLPlosoneTagger;

public class DefaultPubstyleReader extends PubstyleReader {

	public DefaultPubstyleReader() {
		super();
	}

	public DefaultPubstyleReader(InputFormat type) {
		super(type);
	}

	@Override
	protected void addTaggers() {
		this.addTagger(InputFormat.HTML, new HTMLPlosoneTagger());
		this.addTagger(InputFormat.XML, new XMLPlosoneTagger());
	}

	@Override
	protected List<String> getExtraneousXPaths() {
		return new ArrayList<String>();
	}

}
