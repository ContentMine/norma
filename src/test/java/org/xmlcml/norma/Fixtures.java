package org.xmlcml.norma;

import java.io.File;

public class Fixtures {

	public final static File MAIN_NORMA_DIR = new File("src/main/resources/org/xmlcml/norma/");
	public final static File MAIN_STYLE_DIR = new File(Fixtures.MAIN_NORMA_DIR, "style");
	public final static File MINI_XSL = new File(Fixtures.MAIN_STYLE_DIR, "miniTest.xsl");
	public final static File MAIN_DOCUMENT_DIR = new File(Fixtures.MAIN_NORMA_DIR, "pubstyle");
	public final static File MAIN_HINDAWI_DIR = new File(Fixtures.MAIN_DOCUMENT_DIR, "hindawi");
	public final static File GROUP_MAJOR_SECTIONS_XSL = new File(Fixtures.MAIN_HINDAWI_DIR, "groupMajorSections.xsl");

	public final static File TEST_NORMA_DIR = new File("src/test/resources/org/xmlcml/norma/");
	public final static File TEST_DOCUMENT_DIR = new File(Fixtures.TEST_NORMA_DIR, "pubstyle");
	public final static File TEST_HINDAWI_DIR = new File(Fixtures.TEST_DOCUMENT_DIR, "hindawi");
	public final static File TEST_BMC_DIR = new File(Fixtures.TEST_DOCUMENT_DIR, "bmc");
	public final static File TEST_SEDAR_DIR = new File(Fixtures.TEST_DOCUMENT_DIR, "sedar");
	public final static File TEST_ASTROPHYS_DIR = new File(Fixtures.TEST_DOCUMENT_DIR, "astrophysj");
	
	public static final File BMC_0277_PDF = new File(Fixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf");
	
	public final static File F507405_HTML = new File(Fixtures.TEST_HINDAWI_DIR, "507405.html");
	public final static File F507405_XML = new File(Fixtures.TEST_HINDAWI_DIR, "507405.xml");
	public final static File F507405_GROUPED_XHTML = new File(Fixtures.TEST_HINDAWI_DIR, "507405.grouped.html");
	public final static File F247835_XML = new File(Fixtures.TEST_HINDAWI_DIR, "247835.xml");
	public final static File F247835_GROUPED_XHTML = new File(Fixtures.TEST_HINDAWI_DIR, "247835.grouped.html");
	public final static File F247835_TAGGED_XHTML = new File(Fixtures.TEST_HINDAWI_DIR, "247835.tagged.xhtml");

	public final static File TEST_PLOSONE_DIR = new File(Fixtures.TEST_DOCUMENT_DIR, "plosone");
	public final static File F0113556_XML = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0113556.xml");
	public final static File F0113556_TAGGED_XML = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0113556.tagged.xml");
	public final static File F0113556_HTML = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0113556.html");
	public final static File F0113556_TAGGED_HTML = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0113556.tagged.html");

	public final static File TEST_STYLE_DIR = new File(Fixtures.TEST_NORMA_DIR, "style");
	public final static File XSLT2_TEST1_XML = new File(Fixtures.TEST_STYLE_DIR, "xslt2Test1.xml");
	public final static File XSLT2_TEST1_XSL = new File(Fixtures.TEST_STYLE_DIR, "xslt2Test1.xsl");
	
	
	
}
