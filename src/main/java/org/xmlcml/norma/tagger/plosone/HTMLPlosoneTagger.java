package org.xmlcml.norma.tagger.plosone;

import java.io.File;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.PubstyleTagger;

public class HTMLPlosoneTagger extends PubstyleTagger {

	private final static Logger LOG = Logger.getLogger(HTMLPlosoneTagger.class);

	public final static String PLOSONE = "plosone";
	private static final String PLOSONE_TAGGER_DIR_RESOURCE = TAGGER_DIR_RESOURCE+"/"+PLOSONE;
	public static final String PLOSONE_TAGGER_RESOURCE = PLOSONE_TAGGER_DIR_RESOURCE+"/"+"htmlTagger.xml";

	public HTMLPlosoneTagger() {
		super(PLOSONE_TAGGER_RESOURCE);
	}

	public static String getTaggerName() {
		return PLOSONE;
	}

}
