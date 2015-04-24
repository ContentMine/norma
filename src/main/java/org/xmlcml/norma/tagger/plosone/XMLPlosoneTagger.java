package org.xmlcml.norma.tagger.plosone;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.PubstyleTagger;

public class XMLPlosoneTagger extends PubstyleTagger {

	private final static Logger LOG = Logger.getLogger(XMLPlosoneTagger.class);

	public final static String PLOSONE = "plosone";
//	private static final File TAGGER_PLOSONE_DIR = new File(TAGGER_DIR, PLOSONE);
//	public static final File PLOSONE_TAGDEFINITIONS_FILE = new File(TAGGER_PLOSONE_DIR, "xmlTagger.xml");
	private static final String PLOSONE_RESOURCE = PUBSTYLE_RESOURCE+"/"+PLOSONE;
	public static final String PLOSONE_TAGDEFINITIONS_RESOURCE = PLOSONE_RESOURCE+"/"+"xmlTagger.xml";

	public XMLPlosoneTagger() {
		super(PLOSONE_TAGDEFINITIONS_RESOURCE);
	}

	public static String getTaggerName() {
		return PLOSONE;
	}

}
