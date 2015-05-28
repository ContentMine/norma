package org.xmlcml.norma.pubstyle.plosone;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.html.util.HtmlUtil;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.Norma;

public class PloSONETest {
	
	private static final Logger LOG = Logger.getLogger(PloSONETest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	/** there are problems with the XHTML from this one...
	 * 
	 */
	@Ignore // takes a minute
	public void testPlosonePDF() throws Exception {
		File outputFile = new File("target/plosone/0115884.html");
		String[] args = {
				"-i", new File(Fixtures.F0115884_DIR, "fulltext.pdf").toString(),
				"-o", outputFile.toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(outputFile.exists());
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
		Assert.assertEquals("p elements "+pElements.size(), 43, pElements.size()); 
		Collection<File> targetPngs = FileUtils.listFiles(new File("target/"), new String[]{"png"}, false);
		LOG.debug("targetPngs: "+targetPngs.size());
		Collection<File> homePngs = FileUtils.listFiles(new File("./"), new String[]{"png"}, false);
		LOG.debug("homePngs: "+homePngs.size());
		Collection<File> targetSvgs = FileUtils.listFiles(new File("target/"), new String[]{"svg"}, false);
		LOG.debug("targetSvgs: "+targetSvgs.size());
		Collection<File> homeSvgs = FileUtils.listFiles(new File("./"), new String[]{"svg"}, false);
		LOG.debug("homeSvgs: "+homeSvgs.size());
	}
	
	@Test
	/** this is a processed HTML
	 * 
	 */
	@Ignore // recast as a CMDir
	public void testPlosone() throws Exception {
		File outputFile = new File("target/plosone/0113556/");
		FileUtils.deleteQuietly(outputFile);
		String[] args = {
				"-i", Fixtures.F0113556_HTML.toString(),
				"--pubstyle", "src/main/resources/org/xmlcml/norma/pubstyle/plosone/htmlTagger.xml",
				"-o", outputFile.toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
		// FIXME this should create a CMDir 
		Assert.assertTrue(outputFile.exists());
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		Assert.assertNotNull("htmlelement should not be null", htmlElement);
		LOG.debug(htmlElement.toXML().substring(0, 200));
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
		Assert.assertEquals("div elements "+divElements.size(), 78, divElements.size()); 
	}
	
	@Test
	/** this is a raw HTML
	 * 
	 */
	@Ignore // we have to normalize HTML
	public void testPlosoneRawHTML() throws Exception {
		File outputFile = new File("target/plosone/0115884.html");
		String[] args = {
				"-i", Fixtures.F0115884_HTML.toString(),
				"--pubstyle", "src/main/resources/org/xmlcml/norma/pubstyle/plosone/htmlTagger.xml",
				"-o", outputFile.toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(outputFile.exists());
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
		Assert.assertEquals("div elements "+divElements.size(), 118, divElements.size()); 
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
//		Assert.assertEquals("p elements "+pElements.size(), 53, pElements.size()); 
	}
	
	@Test
	/** no pubstyle so remove the extraneous tags but not divs.
	 * 
	 * This test is fragile as we shall change the default tags
	 * 
	 */
	@Ignore // we have to normalize HTML
	public void testPlosoneRawHTMLNoPubstyle() throws Exception {
		File outputFile = new File("target/plosone/0115884.nopubstyle.html");
		String[] args = {
				"-i", Fixtures.F0115884_HTML.toString(),
				"-o", outputFile.toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(outputFile.exists());
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
		Assert.assertEquals("div elements "+divElements.size(), 118, divElements.size()); 
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
		Assert.assertEquals("p elements "+pElements.size(), 53, pElements.size()); 
	}
	
	@Test
	/** no pubstyle so remove the extraneous tags but not divs.
	 * 
	 * This test is fragile as we shall change the default tags
	 * 
	 */
	@Ignore // we have to normalize HTML
	public void testPlosoneRawHTMLNoDefaults() throws Exception {
		File outputFile = new File("target/plosone/0115884.defaults.html");
		String[] args = {
				"-i", Fixtures.F0115884_HTML.toString(),
				"-d", // don't delete any tags
				"-o", outputFile.toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(outputFile.exists());
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
		Assert.assertEquals("div elements "+divElements.size(), 118, divElements.size()); 
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
		Assert.assertEquals("p elements "+pElements.size(), 53, pElements.size()); 
	}
	
	@Test
	/** pubstyle remove some divs.
	 * 
	 * This test is fragile as we shall change the default tags and the default divs
	 * 
	 * The HTML is so broken that the nesting of the reference lists are wrong
	 * 
	 */
	@Ignore // we have to normalize HTML
	public void testPlosoneRawHTMLPubstyle() throws Exception {
		File outputFile = new File("target/plosone/0115884.pubstyle.html");
		String[] args = {
				"-i", Fixtures.F0115884_HTML.toString(),
				"-p", "plosone",
				"-o", outputFile.toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(outputFile.exists());
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
		Assert.assertEquals("div elements "+divElements.size(), 28, divElements.size()); 
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
		Assert.assertEquals("p elements "+pElements.size(), 38, pElements.size()); 
	}
	
	@Test
	/** basic XML for NLM.
	 * 
	 * @throws Exception
	 */
	@Ignore // FIXME
	public void testPlosoneXMLNoPubstyle() throws Exception {
		File outputFile = new File("target/plosone/0115884/");
		String[] args = {
				"-i", new File(Fixtures.F0115884A_DIR, "fulltext.nodtd.xml").toString(),
				"--pubstyle", "nlm",
				"--transform", "src/main/resources/org/xmlcml/norma/pubstyle/nlm/toHtml.xsl",
				"-o", outputFile.toString(),
		};
		if (1==1) throw new RuntimeException("Recast as CMDir");
		Norma norma = new Norma();
		norma.run(args);
		
		Assert.assertTrue(outputFile.exists());
		LOG.debug("size: "+FileUtils.sizeOf(outputFile));
		HtmlElement htmlElement = new HtmlFactory().parse(outputFile);
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
//		Assert.assertEquals("div elements "+divElements.size(), 28, divElements.size()); 
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
		Assert.assertEquals("p elements "+pElements.size(), 28, pElements.size()); 
	}
	

}
