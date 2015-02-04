package org.xmlcml.norma.tagger.bmc;

import java.io.File;

import org.apache.log4j.Logger;
import org.xmlcml.norma.tagger.PubstyleTagger;

public class HTMLBmcTagger extends PubstyleTagger {

	private final static Logger LOG = Logger.getLogger(HTMLBmcTagger.class);

	public final static String BMC = "bmc";
	
	private static final String BMC_TAGGER_RESOURCE = PUBSTYLE_RESOURCE+"/"+BMC;
	public static final String BMC_TAGGER_HTML = BMC_TAGGER_RESOURCE+"/"+"htmlTagger.xml";

	public HTMLBmcTagger() {
		super(BMC_TAGGER_HTML);
	}

	public static String getTaggerName() {
		return BMC;
	}

}
