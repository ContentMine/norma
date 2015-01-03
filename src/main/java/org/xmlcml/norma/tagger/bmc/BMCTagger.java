package org.xmlcml.norma.tagger.bmc;

import java.io.File;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.DocumentTagger;

public class BMCTagger extends DocumentTagger {

	private final static Logger LOG = Logger.getLogger(BMCTagger.class);

	public final static String BMC = "bmc";
	private static final File TAGGER_BMC_DIR = new File(TAGGER_DIR, BMC);
	public static final File BMC_TAGGER_FILE = new File(TAGGER_BMC_DIR, "documentTagger.xml");

	public BMCTagger() {
		super(BMC_TAGGER_FILE);
	}

	public static String getTaggerName() {
		return BMC;
	}

}
