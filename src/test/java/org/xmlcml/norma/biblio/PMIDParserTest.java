package org.xmlcml.norma.biblio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.cmine.util.CMineUtil;
import org.xmlcml.html.HtmlDiv;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/** this may be possible in a hacked RISParser
 * 
 * @author pm286
 *
 */
public class PMIDParserTest {

	public static final Logger LOG = Logger.getLogger(PMIDParserTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testRISParserPubMed() throws FileNotFoundException, IOException {
		RISParser parser = new RISParser();
		parser.read(new FileInputStream("src/test/resources/org/xmlcml/norma/biblio/pmid/pmidsmall.txt"));
		List<RISEntry> bibChunks = parser.getEntries();
		Assert.assertEquals(13,  bibChunks.size());
		for (RISEntry chunk : bibChunks) {
//			System.out.println(">>"+chunk);
		}
	}

	@Test
	public void testPubMed2JSON() throws FileNotFoundException, IOException {
		RISParser parser = new RISParser();
		parser.read(new FileInputStream("src/test/resources/org/xmlcml/norma/biblio/pmid/pmidsmall.txt"));
		List<RISEntry> bibChunks = parser.getEntries();
		Assert.assertEquals(13,  bibChunks.size());
		for (RISEntry chunk : bibChunks) {
//			System.out.println(">>"+chunk);
		}
	}

	@Test
	public void testPubMedResult6() throws FileNotFoundException, IOException {
		RISParser parser = new RISParser();
		parser.read(new FileInputStream("src/test/resources/org/xmlcml/norma/biblio/pmid/pubmed_result6.txt"));
		List<RISEntry> bibEntries = parser.getEntries();
		Assert.assertEquals(9507,  bibEntries.size());
		int i = 0;
		BiblioAbstractAnalyzer abstractAnalyzer = new BiblioAbstractAnalyzer();
		for (RISEntry entry : bibEntries) {
			abstractAnalyzer.analyze(entry);
			if (i++ < 100) {
				LOG.trace(">>"+entry.createAbstractHtml().toXML());
			}
//			if (i++ > 10) break;
		}
		HtmlDiv abstractList = abstractAnalyzer.createAbstractList(parser);
		File risFile = new File("target/ris/");
		FileUtils.write(new File(risFile, "pubmed6.html"), abstractList.toXML(), Charset.forName("UTF-8"));
		Multiset<String> countedSet = abstractAnalyzer.getSectionMultiset();
		LOG.trace("abstract terms: "+countedSet.size());
		Iterable<Multiset.Entry<String>> sectionByCount = CMineUtil.getEntriesSortedByCount(countedSet);

		LOG.trace(sectionByCount);
		Iterator<Entry<String>> iterator = sectionByCount.iterator();
		i = 0;
		while (iterator.hasNext()) {
			LOG.trace(">>"+"entry: "+iterator.next());
			if (i++ > 100) break;
		}
		LOG.trace("end");
		
	}

}
