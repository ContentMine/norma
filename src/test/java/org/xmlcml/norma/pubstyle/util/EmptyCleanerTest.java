package org.xmlcml.norma.pubstyle.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.norma.NormaFixtures;

public class EmptyCleanerTest {

	@Test
	public void testCleanEmpty() throws Exception {
		HtmlElement html = new HtmlFactory().parse(new File(NormaFixtures.TEST_PUBSTYLE_DIR, "util/cup_scholarly.html"));
		XMLCleaner cleaner = new XMLCleaner(html);
		cleaner.remove("//*[local-name()='div' and normalize-space(.)='']");
		FileUtils.write(new File("target/cleaner/cup.html"), cleaner.getElement().toXML());
	}
}
