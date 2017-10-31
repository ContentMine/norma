package org.xmlcml.norma.output;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.graphics.html.HtmlScript;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

public class HtmlButtonDisplayTest {
	private static final Logger LOG = Logger.getLogger(HtmlButtonDisplayTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testFiles() throws IOException {
		String cTreeName = "html5/10.1016_j.pain.2014.08.023";
		File file = new File(NormaFixtures.TEST_TABLE_DIR, cTreeName);
		LOG.debug("filex "+file.getAbsolutePath());
		List<File> files = new RegexPathFilter(".*tables/table\\d+/tableRow.html").listNonDirectoriesRecursively(file);
		Assert.assertEquals(4, files.size());
		HtmlTabbedButtonDisplay tabbedButtonDisplay = new HtmlTabbedButtonDisplay();
		XMLUtil.debug(tabbedButtonDisplay, new File("target/tabs/tabbed0.html"), 1);
		tabbedButtonDisplay.setTitle("tables for "+cTreeName);
		tabbedButtonDisplay.createButtonsFromHtmlFiles(files);
		XMLUtil.debug(tabbedButtonDisplay, new File("target/tabs/tabbed1a.html"), 1);
		HtmlScript script = tabbedButtonDisplay.getOrCreateHead().getOrCreateScript();
		XMLUtil.debug(script, new File("target/tabs/script.html"), 1);
		XMLUtil.debug(tabbedButtonDisplay, new File("target/tabs/tabbed1.html"), 1);
	}
}
