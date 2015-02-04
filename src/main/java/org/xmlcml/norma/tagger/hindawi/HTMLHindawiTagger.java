package org.xmlcml.norma.tagger.hindawi;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.PubstyleTagger;

public class HTMLHindawiTagger extends PubstyleTagger {

	private final static Logger LOG = Logger.getLogger(HTMLHindawiTagger.class);

	public final static String HINDAWI = "hindawi";
//	private static final File TAGGER_HINDAWI_DIR = new File(TAGGER_DIR, HINDAWI);
//	public static final File HINDAWI_TAGGER_FILE = new File(TAGGER_HINDAWI_DIR, "htmlTagger.xml");
	private static final String HINDAWI_TAGGER_DIR_RESOURCE = PUBSTYLE_RESOURCE+"/"+HINDAWI;
	public static final String HINDAWI_TAGGER_RESOURCE = HINDAWI_TAGGER_DIR_RESOURCE+"/"+"htmlTagger.xml";

	public HTMLHindawiTagger() {
//		super(HINDAWI_TAGGER_FILE);
		super(HINDAWI_TAGGER_RESOURCE);
	}

	
	public static String getTaggerName() {
		return HINDAWI;
	}



}
