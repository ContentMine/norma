package org.xmlcml.norma.biblio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class RISParserTest {

	@Test
	public void testRISParser() throws FileNotFoundException, IOException {
		RISParser parser = new RISParser();
		parser.read(new FileInputStream("src/test/resources/org/xmlcml/norma/biblio/ris/Citations.txt"));
		List<RISEntry> bibChunks = parser.getEntries();
		Assert.assertEquals(20,  bibChunks.size());
	}
	
}
