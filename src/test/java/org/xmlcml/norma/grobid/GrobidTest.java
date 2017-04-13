package org.xmlcml.norma.grobid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidConstants;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.xmlcml.graphics.svg.SVGElement;
//import org.xmlcml.graphics.svg.SVGG;
//import org.xmlcml.graphics.svg.SVGSVG;
//import org.xmlcml.norma.NormaFixtures;
//import org.xmlcml.xml.XMLUtil;
//
//import junit.framework.Assert;
//
public class GrobidTest {
	private static final Logger LOG = Logger.getLogger(GrobidTest.class);
	
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
    private String testPath = null;
    private String newTrainingPath = null;

    @BeforeClass
    public static void init() {
    	try {
    		GrobidProperties.getInstance();
    	} catch (Exception e) {
    		System.out.println("ERROR "+e);
    	}
    }

    @Before
    public void setUp() {
        newTrainingPath = GrobidProperties.getTempPath().getAbsolutePath();
    }

    

	@Test
	public void testGrobidCoords() {
		
		File grobidTEIXml = new File(NormaFixtures.TEST_NORMA_DIR, "grobid/fulltext.grobid.xml");
		Assert.assertTrue(grobidTEIXml.exists());
		
		GrobidTEIElement grobidTEI = GrobidElement.readTEI(grobidTEIXml);
		List<GrobidElement> coordElements = grobidTEI.getCoordElements();
		Assert.assertEquals(142,  coordElements.size());
	}
	
	@Test
	public void testGrobid2Coords() {
		
		File grobidTEIXml = new File(NormaFixtures.TEST_NORMA_DIR, "grobid/fulltext.grobid.xml");
		GrobidTEIElement grobidTEI = GrobidElement.readTEI(grobidTEIXml);
		List<List<GrobidCoords>> coordsListList = grobidTEI.getCoordListList();
		Assert.assertEquals(142, coordsListList.size());
	}
	
	@Test
	public void testGrobid2SVG() throws IOException {
		int page = 0;
		List<SVGG> gList = null;
		File grobidTEIXml = new File(NormaFixtures.TEST_GROBID_DIR, "fulltext.grobid.xml");
		File svgDir = new File(NormaFixtures.TEST_GROBID_DIR, "svg/");
		GrobidTEIElement grobidTEI = GrobidElement.readTEI(grobidTEIXml);
		List<List<GrobidCoords>> coordsListList = grobidTEI.getCoordListList();
		for (List<GrobidCoords> coordsList : coordsListList) {
			SVGG g = GrobidCoords.createSVG(coordsList);
			int page1 = GrobidCoords.getPage(coordsList);
			if (page != page1) {
				flushSVG(page, gList, svgDir);
				gList = new ArrayList<SVGG>();
				page = page1;
			}
			gList.add(g);
		}
		flushSVG(page, gList, svgDir);
		
	}

	@Test
	@Ignore // more debug required
	public void testTEI2HTML() throws IOException {
		File grobidTEIXml = new File(NormaFixtures.TEST_GROBID_DIR, "sample2");
		GrobidTEIElement grobidTEI = GrobidElement.readTEI(grobidTEIXml);
		HtmlElement htmlElement = grobidTEI.createHTML();
		XMLUtil.debug(htmlElement, new File("target/grobid/html/fulltext.html"), 1);
		
	}


	// ================================================
	

	public void train() throws Exception {
        String testPath = GrobidConstants.TEST_RESOURCES_PATH;
        MockContext.setInitialContext();
        Engine engine = GrobidFactory.getInstance().createEngine();

        String pdfPath = testPath + File.separator + "ApplPhysLett_98_082505.pdf";
        engine.createTrainingFullText(new File(pdfPath), newTrainingPath, newTrainingPath, 4);
        
        LOG.debug("first training finished");
	}
	
	@Test
	@Ignore // ELSEWHERE
	public void testGrobidNative() throws Exception {
		train();
	    File pdfPath = new File("../../cm-ucl/corpus-oa-pmr-v01/10.1007_s00213-015-4198-1/fulltext.pdf");
	    Object tei = GrobidFactory.getInstance().createEngine().fullTextToTEIDoc(pdfPath, GrobidAnalysisConfig.defaultInstance());
	    LOG.debug(tei.getClass());
	}

	//=================================

	private void flushSVG(int page, List<SVGG> gList, File svgDir) throws IOException {
		if (page != 0) {
			SVGSVG svgPage = (SVGSVG) SVGElement.readAndCreateSVG(new File(svgDir, "fulltext-page"+page+".svg"));
			for (SVGG g : gList) {
				svgPage.appendChild(g);
			}
			XMLUtil.debug(svgPage, new File("target/grobid/svg/fulltext"+page+".svg"), 1);
		}
	}
}
