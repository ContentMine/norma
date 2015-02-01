package org.xmlcml.norma.tagger.bmc;

import java.io.File;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.PubstyleTagger;

public class HTMLBmcTagger extends PubstyleTagger {

	private final static Logger LOG = Logger.getLogger(HTMLBmcTagger.class);

	public final static String BMC = "bmc";
	
	private static final File TAGGER_BMC_DIR = new File(TAGGER_DIR, BMC);
	private static final String BMC_TAGGER_DIR_RESOURCE = TAGGER_DIR_RESOURCE+"/"+BMC;
	public static final String BMC_TAGGER_RESOURCE = BMC_TAGGER_DIR_RESOURCE+"/"+"htmlTagger.xml";

	public HTMLBmcTagger() {
		super(BMC_TAGGER_RESOURCE);
	}

	public static String getTaggerName() {
		return BMC;
	}

}
