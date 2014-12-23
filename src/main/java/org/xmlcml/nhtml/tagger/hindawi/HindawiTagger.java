package org.xmlcml.nhtml.tagger.hindawi;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.xmlcml.nhtml.tagger.DocumentTagger;
import org.xmlcml.nhtml.tagger.MetadataElement;

public class HindawiTagger extends DocumentTagger {

	private final static Logger LOG = Logger.getLogger(HindawiTagger.class);

	public final static String HINDAWI = "hindawi";
	private static final File TAGGER_HINDAWI_DIR = new File(TAGGER_DIR, HINDAWI);
	public static final File HINDAWI_TAGGER_FILE = new File(TAGGER_HINDAWI_DIR, "documentTagger.xml");

	public HindawiTagger() {
		super(HINDAWI_TAGGER_FILE);
	}

	public static String getTaggerName() {
		return HINDAWI;
	}


}
