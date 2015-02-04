package org.xmlcml.norma.tagger.elsevier;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.PubstyleTagger;

public class HTMLElsevierTagger extends PubstyleTagger {

	private final static Logger LOG = Logger.getLogger(HTMLElsevierTagger.class);

	public final static String ELSEVIER = "elsevier";
//	private static final File TAGGER_ELSEVIER_DIR = new File(TAGGER_DIR, ELSEVIER);
//	public static final File ELSEVIER_TAGDEFINITIONS_FILE = new File(TAGGER_ELSEVIER_DIR, "htmlTagger.xml");
	private static final String ELSEVIER_TAGGER_DIR_RESOURCE = PUBSTYLE_RESOURCE+"/"+ELSEVIER;
	public static final String ELSEVIER_TAGGER_RESOURCE = ELSEVIER_TAGGER_DIR_RESOURCE+"/"+"htmlTagger.xml";

	public HTMLElsevierTagger() {
//		super(ELSEVIER_TAGDEFINITIONS_FILE);
		super(ELSEVIER_TAGGER_RESOURCE);
	}

	public static String getTaggerName() {
		return ELSEVIER;
	}

}
