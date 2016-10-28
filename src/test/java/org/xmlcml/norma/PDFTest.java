package org.xmlcml.norma;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.image.ImageProcessor;
import org.xmlcml.norma.image.ocr.NamedImage;
import org.xmlcml.norma.input.pdf.PDF2ImagesConverter;
import org.xmlcml.norma.input.pdf.PDF2TXTConverter;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;

public class PDFTest {

	private static Logger LOG = Logger.getLogger(PDFTest.class);
	static {LOG.setLevel(Level.DEBUG);}
	
	@Test
	// 16 pp
	@Ignore // too long
	public void testReadPDFNoTag() {
		String[] args = {
				"-i", new File(NormaFixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf").toString(),
				"-o", new File("target/BMC/s12862-014-0277-x/").toString(),
		};
		Norma norma = new Norma();
		norma.run(args);

	}
	
	@Test
	@Ignore // too long
	public void testReadPDF13054() {
		String[] args = {
				"-i", new File(NormaFixtures.TEST_BMC_DIR, "s13054-014-0721-8.pdf").toString(),
				"-o", new File("target/BMC/s13054-014-0721-8/").toString(),
		};
		Norma norma = new Norma();
		norma.run(args);

	}
		
	@Test
	@Ignore // SVG input not yet written
	public void testReadSedarSVG() {
		File inputFile = new File(NormaFixtures.TEST_SEDAR_DIR, "westernZagros.g.11.7.svg");
		Assert.assertTrue(inputFile.exists());
		String[] args = {
				"-i", inputFile.toString(),
				"-o", new File("target/sedar/westernZagros.g.11.7/").toString(),
		};
		Norma norma = new Norma();
		norma.run(args);

	}
	
	@Test
	@Ignore // too long
	public void testReadSedarBlackbirdPDF() {
		String[] args = {
				"-i", new File(NormaFixtures.TEST_SEDAR_DIR, "blackbird.pdf").toString(),
				"-o", new File("target/sedar/image.g.11.7/").toString(),
		};
		Norma norma = new Norma();
		norma.run(args);

	}
		
	@Test
	@Ignore // long
	public void testReadSedarSeveralPDF() {
		String[] args = {
				"-i", 
			       new File(NormaFixtures.TEST_SEDAR_DIR, "chelsea.pdf").toString(),
 			       new File(NormaFixtures.TEST_SEDAR_DIR, "enbridge.pdf").toString(),
 			       new File(NormaFixtures.TEST_SEDAR_DIR, "pennwest.pdf").toString(),
		       new File(NormaFixtures.TEST_SEDAR_DIR, "rooster.pdf").toString(),
			       new File(NormaFixtures.TEST_SEDAR_DIR, "solimar.pdf").toString(),
				"-o", new File("target/sedar/image.g.11.7/").toString(),
		};
		Norma norma = new Norma();
		norma.run(args);

	}
	
	
	@Test
	@Ignore
	/**
	 * // bad throws 		
Caused by: java.io.IOException: Error: Header doesn't contain versioninfo
	at org.apache.pdfbox.pdfparser.PDFParser.parseHeader(PDFParser.java:339)
	at org.apache.pdfbox.pdfparser.PDFParser.parse(PDFParser.java:177)
	at org.apache.pdfbox.pdmodel.PDDocument.load(PDDocument.java:1214)
	at org.apache.pdfbox.pdmodel.PDDocument.load(PDDocument.java:1181)
	at org.apache.pdfbox.pdmodel.PDDocument.load(PDDocument.java:1152)
	at org.xmlcml.pdf2svg.PDF2SVGConverter.readDocument(PDF2SVGConverter.java:323)
	*/

	public void testBadPDF() {
		String[] args = {
				"-i", 
			       new File(NormaFixtures.TEST_SEDAR_DIR, "madagascar.pdf").toString(),
				       new File(NormaFixtures.TEST_SEDAR_DIR, "roxi.pdf").toString(),
				"-o", new File("target/sedar/image.g.11.7/").toString(),
		};
		Norma norma = new Norma();
		norma.run(args);
	
	}
	
	@Test
	public void testPDF2TXT() throws FileNotFoundException, IOException {
		PDF2TXTConverter converter = new PDF2TXTConverter();
		File file0115884 = new File(NormaFixtures.TEST_PLOSONE_DIR, "journal.pone.0115884/fulltext.pdf");
		String text = converter.readPDF(new FileInputStream(file0115884), true);
		FileUtils.write(new File("target/pdf/file0115884.txt"), text, Charset.forName("UTF-8"));
	}
	
	@Test
	public void testPDF2TXT1() throws FileNotFoundException, IOException {
		FileUtils.copyDirectory(
//				new File("src/test/resources/org/xmlcml/norma/regressiondemos/quickscrapeDirs/bmc/1471-2148-14-70"), 
				new File("src/test/resources/org/xmlcml/norma/quickscrapeDirs/bmc/1471-2148-14-70"), 
				new File("target/pdftest"));
		String args = "-q target/pdftest/ -i fulltext.pdf -o fulltext.pdf.txt --transform pdf2txt";
		Norma norma = new Norma();
		norma.run(args);
	}
	
	@Test
	@Ignore // LONG
	public void testPDF2XHTML() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		converter.setSvgDirectory(new File("target/pdf/0115884/svg"));
		File file0115884 = new File(NormaFixtures.TEST_PLOSONE_DIR, "journal.pone.0115884/fulltext.pdf");
		HtmlElement htmlElement = converter.readAndConvertToXHTML(file0115884);
		FileUtils.write(new File("target/pdf/0115884/fulltext.html"), htmlElement.toXML(), Charset.forName("UTF-8"));
	}
	
	@Test
	@Ignore // LONG
	public void testPDF2CTree() throws Exception {
		CTree cTree = new CTree(new File("target/cmdir/0115884/"));
		cTree.readFulltextPDF(new File(NormaFixtures.TEST_PLOSONE_DIR, "journal.pone.0115884/fulltext.pdf"));
		// convert to XHTML using converter
		PDF2XHTMLConverter pdf2HtmlConverter = new PDF2XHTMLConverter(cTree);
		// will write SVG to svgDirectory
		pdf2HtmlConverter.readAndConvertToXHTML();
	}
	
	@Test
	@Ignore
	public void testBMCPDF2CTree() throws Exception {
		CTree cTree = new CTree(new File("target/cmdir/bmc/1471-2148-14-70"));
		cTree.readFulltextPDF(new File(NormaFixtures.TEST_BMC_DIR, "1471-2148-14-70/fulltext.pdf"));
		PDF2XHTMLConverter pdf2HtmlConverter = new PDF2XHTMLConverter(cTree);
		pdf2HtmlConverter.readAndConvertToXHTML();
	}
	
	@Test
	@Ignore // too long
	public void testCGIAR2CTree() throws Exception {
		CTree cTree = new CTree(new File("target/cmdir/cgiar/345"));
		cTree.readFulltextPDF(new File(NormaFixtures.TEST_PUBSTYLE_DIR, "cgiar/345.pdf"));
		PDF2XHTMLConverter pdf2HtmlConverter = new PDF2XHTMLConverter(cTree);
		pdf2HtmlConverter.readAndConvertToXHTML();
	}
	
	@Test
	@Ignore // closed PDF // LONG
	public void testNeuroFigures() throws Exception {
		CTree cTree = new CTree(new File("target/cmdir/neuro/Chen"));
		File pdfFile = new File(NormaFixtures.TEST_PUBSTYLE_DIR, "neuro/Chen2005.pdf");
		cTree.readFulltextPDF(pdfFile);
		PDF2XHTMLConverter pdf2HtmlConverter = new PDF2XHTMLConverter(cTree);
//		pdf2HtmlConverter.readAndConvertToXHTML();
		PDF2ImagesConverter imagesConverter = new PDF2ImagesConverter();
		List<NamedImage> labelledImages = imagesConverter.readPDF(new FileInputStream(pdfFile));
		File imageDir = cTree.getOrCreateExistingImageDir();
		for (NamedImage labelledImage : labelledImages) {
			BufferedImage image = labelledImage.getImage();
			ImageProcessor imageProcessor = ImageProcessor.createDefaultProcessorAndProcess(image);
			image = imageProcessor.getImage();
			String imageName = labelledImage.getKey();
			ImageIO.write(image, "png", new File(imageDir, imageName+".png"));
		}
	}
	
}