package org.xmlcml.nhtml.tagger.elsevier;

import java.io.File;

import org.apache.log4j.Logger;
import org.xmlcml.nhtml.tagger.DocumentTagger;

public class HTMLElsevierTagger extends DocumentTagger {

	private final static Logger LOG = Logger.getLogger(HTMLElsevierTagger.class);

	public final static String ELSEVIER = "elsevier";
	private static final File TAGGER_ELSEVIER_DIR = new File(TAGGER_DIR, ELSEVIER);
	public static final File ELSEVIER_TAGDEFINITIONS_FILE = new File(TAGGER_ELSEVIER_DIR, "htmlTagger.xml");

	public HTMLElsevierTagger() {
		super(ELSEVIER_TAGDEFINITIONS_FILE);
	}

	public static String getTaggerName() {
		return ELSEVIER;
	}

}
