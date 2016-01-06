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
	public final static File TEST_JSON_DIR = new File(Fixtures.TEST_NORMA_DIR, "json");
	public final static File TEST_BIBLIO_DIR = new File(Fixtures.TEST_NORMA_DIR, "biblio");
	public final static File TEST_PUBSTYLE_DIR = new File(Fixtures.TEST_NORMA_DIR, "pubstyle");
	public final static File TEST_PATENTS_DIR = new File(Fixtures.TEST_NORMA_DIR, "patents");
	public final static File TEST_TEX_DIR = new File(Fixtures.TEST_NORMA_DIR, "tex");
	
	public final static File TEST_HINDAWI_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "hindawi");
	
	public final static File TEST_BMC_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "bmc/");
	public final static File BMC_MISC_DIR = new File(Fixtures.TEST_BMC_DIR, "misc/");
	public static final File BMC_0277_PDF = new File(Fixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf");
	public final static File BMC_15_1_511_DIR = new File(Fixtures.TEST_BMC_DIR, "http_www.trialsjournal.com_content_15_1_511");

	public final static File TEST_SEDAR_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "sedar/");
	
	public final static File TEST_ASTROPHYS_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "astrophysj/");

	public final static File TEST_USPTO_DIR = new File(Fixtures.TEST_PATENTS_DIR, "uspto");
	public final static File TEST_USPTO08978_DIR = new File(Fixtures.TEST_USPTO_DIR, "UTIL08978");

	
	public final static File F507405_DIR = new File(Fixtures.TEST_HINDAWI_DIR, "507405/");
	public final static File F507405_HTML = new File(Fixtures.F507405_DIR, "fulltext.html");
	public final static File F507405_XML = new File(Fixtures.F507405_DIR, "fulltext.xml");
	public final static File F507405_GROUPED_XHTML = new File(Fixtures.F507405_DIR, "grouped.html");
	public final static File F247835_DIR = new File(Fixtures.TEST_HINDAWI_DIR, "247835/");
	public final static File F247835_GROUPED_XHTML = new File(Fixtures.F247835_DIR, "grouped.html");
	public final static File F247835_TAGGED_XHTML = new File(Fixtures.F247835_DIR, "tagged.xhtml");

	public final static File TEST_PLOSONE_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "plosone/");
	public final static File F0113556_DIR = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0113556");
	public final static File F0113556_XML = new File(Fixtures.F0113556_DIR, "fulltext.xml");
	public final static File F0113556_TAGGED_XML = new File(Fixtures.F0113556_DIR, "fulltext.tagged.xml");
	public final static File F0113556_HTML = new File(Fixtures.F0113556_DIR, "fulltext.html");
	public final static File F0113556_TAGGED_HTML = new File(Fixtures.F0113556_DIR, "fulltext.tagged.html");
	
	public final static File F0115884_DIR = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0115884");
	public final static File F0115884A_DIR = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0115884a");
	public final static File F0115884_HTML = new File(Fixtures.F0115884_DIR, "fulltext.html");

	public final static File TEST_STYLE_DIR = new File(Fixtures.TEST_NORMA_DIR, "style/");
	public final static File XSLT2_TEST1_XML = new File(Fixtures.TEST_STYLE_DIR, "xslt2Test1.xml");
	public final static File XSLT2_TEST1_XSL = new File(Fixtures.TEST_STYLE_DIR, "xslt2Test1.xsl");
	
	// input test
	public final static File TEST_MISC_DIR = new File(Fixtures.TEST_NORMA_DIR, "miscfiles/");
	// edited copies of NLM-compliant XML files, numbered "nlm1.xml, etc"
	public final static File TEST_NUMBERED_DIR = new File(Fixtures.TEST_MISC_DIR, "numbered/");
	
	public final static File TEST_ELIFE_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "elife");
	public final static File TEST_ELIFE_CTREE0 = new File(Fixtures.TEST_ELIFE_DIR, "e04407");
	
	public final static File TEST_F1000_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "f1000research");
	public final static File TEST_F1000_CTREE0 = new File(Fixtures.TEST_F1000_DIR, "3-190");
	
	public final static File TEST_FRONTIERS_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "frontiers");
	public final static File TEST_FRONTIERS_CTREE0 = new File(Fixtures.TEST_FRONTIERS_DIR, "fpsyg-05-01582");
	
	public final static File TEST_MDPI_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "mdpi");
	public final static File TEST_MDPI_CTREE0 = new File(Fixtures.TEST_MDPI_DIR, "04-00932");
	
	public final static File TEST_PEERJ_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "peerj");
	public final static File TEST_PEERJ_CTREE0 = new File(Fixtures.TEST_PEERJ_DIR, "727");
	
	public final static File TEST_PENSOFT_DIR = new File(Fixtures.TEST_PUBSTYLE_DIR, "pensoft");
	public final static File TEST_PENSOFT_CTREE0 = new File(Fixtures.TEST_PENSOFT_DIR, "4478");
	
	public final static File TEST_PLOSONE_CTREE0 = new File(Fixtures.TEST_PLOSONE_DIR, "journal.pone.0115884");
	
	
}
