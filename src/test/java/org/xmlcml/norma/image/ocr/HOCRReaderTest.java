package org.xmlcml.norma.image.ocr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.html.HtmlBody;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlMeta;
import org.xmlcml.html.HtmlSpan;
import org.xmlcml.norma.Norma;
import org.xmlcml.xml.XMLUtil;

public class HOCRReaderTest {
	
	
	private static final Logger LOG = Logger.getLogger(HOCRReaderTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public final static String IMAGES_RESOURCE = "/org/xmlcml/norma/image/";
	public final static String OCR_RESOURCE = IMAGES_RESOURCE+"ocr/";
	public final static File IMAGES_DIR = new File("src/test/resources/org/xmlcml/norma/image/");
	public final static File OCR_DIR = new File(IMAGES_DIR, "ocr/");
	public final static Pattern IJSEM = Pattern.compile("(?:([0-9]+[^~]*)~)*"
			+ "(?:(‘?[A-Z](?:[a-z]{2,}|[a-z]?\\.))~)"
			+ "(?:([a-z]+’?)~)"
			+ "(?:(?:(ATCC|DSM|HHQ|IFO|IMSNU|LMG|NBRC|NCDO|NCIMB|NRRL|YIM)~)?)"
			+ "(?:([A-Z0-9\\-]+T?)~?)"
			+ "(?:\\((.*)\\)).*");

	@Test
	public void testReadHOCR() throws IOException {
		String resource = OCR_RESOURCE+"ijs.0.003566-0-000.pbm.png.hocr";
		InputStream is = this.getClass().getResourceAsStream(resource);
		Assert.assertNotNull("input stream must not be null: "+resource, is);
		HOCRReader hocrReader = new HOCRReader();
		hocrReader.readHOCR(is);
		Assert.assertNotNull(hocrReader.getHocrElement());
		HtmlElement htmlHead = hocrReader.getHead();
		Assert.assertNotNull("head",  htmlHead);
		List<HtmlMeta> metaElements = hocrReader.getMetaElements();
		Assert.assertNotNull("meta",  metaElements);
		Assert.assertEquals("meta",  3, metaElements.size());
	}
	
	@Test
	@Ignore // fix expected file
	public void testReadHOCR2SVG() throws IOException {
		HOCRReader hocrReader = new HOCRReader();
		hocrReader.readHOCR(this.getClass().getResourceAsStream(OCR_RESOURCE+"ijs.0.003566-0-000.pbm.png.hocr"));
		SVGSVG svgSvg = (SVGSVG) hocrReader.getOrCreateSVG();
		Assert.assertNotNull("SVG not null", svgSvg);
		HtmlBody htmlBody = hocrReader.getOrCreateHtmlBody();
		new File("target/hocr/").mkdirs();
		XMLUtil.debug(htmlBody, new FileOutputStream("target/hocr/ijs.0.003566-0-000.pbm.png.hocr.html"),1);
		List<HtmlSpan> lines = hocrReader.getNonEmptyLines();
		matchSpecies(hocrReader, IJSEM);
		new File("target/hocr/").mkdirs();
		File resultsFile = new File("target/hocr/ijs.0.003566-0-000.pbm.png.hocr.svg");
		File expectedFile = new File(OCR_DIR, "ijs.0.003566-0-000.pbm.png.hocr.svg");
		XMLUtil.debug(svgSvg, new FileOutputStream(resultsFile), 1);
		String msg = XMLUtil.equalsCanonically(
	    		expectedFile, 
	    		resultsFile,
	    		true);
		if (msg != null) {
			LOG.debug(""+expectedFile+"; "+ FileUtils.readFileToString(expectedFile));
			LOG.debug(""+resultsFile+"; "+FileUtils.readFileToString(resultsFile));
		}
	    Assert.assertNull("message: "+msg, msg);
	}

	private void matchSpecies(HOCRReader hocrReader, Pattern IJSEM) {
		List<HtmlSpan> lines = hocrReader.getNonEmptyLines();
		for (HtmlSpan line : lines) {
			List<String> matchList = HOCRReader.matchPattern(line, IJSEM);
			LOG.trace((matchList.size() == 0 ? "?? "+HOCRReader.getSpacedValue(line).toString() : matchList));
		}
//		System.out.println();
	}
	
	@Test
	public void testReadHOCR2SVGFiles() throws IOException {
		
		String[] roots = {
				"ijs.0.003566-0-000",
				"ijs.0.003616-0-000",
				"ijs.0.003624-0-000",
				"ijs.0.003640-0-001",
				"ijs.0.003699-0-000",
				"ijs.0.003723-0-000",
				"ijs.0.003731-0-002",
				"ijs.0.003749-0-000",
				"ijs.0.003814-0-002",
				"ijs.0.003822-0-000",
		};

		for (String root : roots) {
			LOG.trace(root);
			HOCRReader hocrReader = new HOCRReader();
			hocrReader.readHOCR(this.getClass().getResourceAsStream(OCR_RESOURCE+"/"+root+".pbm.png.hocr"));
			SVGSVG svgSvg = (SVGSVG) hocrReader.getOrCreateSVG();
			Assert.assertNotNull("SVG not null", svgSvg);
			matchSpecies(hocrReader, IJSEM);
			new File("target/hocr/").mkdirs();
			File resultsFile = new File("target/hocr/"+root+".pbm.png.hocr.svg");
			File expectedFile = new File(OCR_DIR, root+".pbm.png.hocr.svg");
			XMLUtil.debug(svgSvg, new FileOutputStream(resultsFile), 1);
			if (true) continue; // use this if you want to copy the target svg into src/test/resources later
 			String msg = XMLUtil.equalsCanonically(
		    		expectedFile, 
		    		resultsFile,
		    		true);
			if (msg != null) {
				LOG.debug(""+expectedFile+"; "+ FileUtils.readFileToString(expectedFile));
				LOG.debug(""+resultsFile+"; "+FileUtils.readFileToString(resultsFile));
			}
		    Assert.assertNull("message: "+msg, msg);
		}
	}
	
	/** commandline conversion.
	 * 
	 */
	@Test
	public void testCommandLine() throws IOException {
		
		File cTreeTop = new File("target/hocr/ijsem_003566");
		if (cTreeTop.exists())FileUtils.forceDelete(cTreeTop);
		FileUtils.copyDirectory(new File(IMAGES_DIR, "ijsem_003566"), cTreeTop);
		CTree cTree = new CTree(cTreeTop);
//		Assert.assertNotNull("image", cTree.getExistingImageFile("ijs.0.003566-0-000.pbm.png"));
//		Assert.assertNotNull("image", cTree.getExistingImageFile("ijs.0.003566-0-000.pbm.png.hocr"));
		String args = "-q "+cTreeTop
				+ " --transform hocr2svg"
				+ " -i " + "image/ijs.0.003566-0-000.pbm.png.hocr"
				+ " -o " + "image/ijs.0.003566-0-000.pbm.png.hocr.svg";
		Norma norma = new Norma();
		norma.run(args);
		File hocrSvg = new File(cTreeTop, "svg/ijs.0.003566-0-000.pbm.png.hocr.svg");
		Assert.assertTrue(hocrSvg.exists());
	}
}
