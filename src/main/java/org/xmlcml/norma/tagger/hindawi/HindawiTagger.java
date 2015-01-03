package org.xmlcml.norma.tagger.hindawi;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.DocumentTagger;

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
