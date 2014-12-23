package org.xmlcml.nhtml.tagger.plosone;

import java.io.File;

import org.apache.log4j.Logger;
import org.xmlcml.nhtml.tagger.DocumentTagger;

public class PLOSONETagger extends DocumentTagger {

	private final static Logger LOG = Logger.getLogger(PLOSONETagger.class);

	public final static String PLOSONE = "plosone";
	private static final File TAGGER_PLOSONE_DIR = new File(TAGGER_DIR, PLOSONE);
	public static final File PLOSONE_TAGDEFINITIONS_FILE = new File(TAGGER_PLOSONE_DIR, "tagDefinitions.xml");

	public PLOSONETagger() {
		super(PLOSONE_TAGDEFINITIONS_FILE);
	}

	public static String getTaggerName() {
		return PLOSONE;
	}

}
