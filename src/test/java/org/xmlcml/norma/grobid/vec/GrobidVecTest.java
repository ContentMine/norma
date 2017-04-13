package org.xmlcml.norma.grobid.vec;

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
import org.junit.Test;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

public class GrobidVecTest {
	private static final Logger LOG = Logger.getLogger(GrobidVecTest.class);
	
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testGrobidVec() throws IOException {
		
		File grobidVecXml = new File(NormaFixtures.TEST_NORMA_DIR, "grobid/vec/image-2.vec");
		Assert.assertTrue(grobidVecXml.exists());
		
		GrobidVecElement grobidVec = GrobidVecElement.readFile(grobidVecXml);
		SVGElement svgElement = grobidVec.createSVG();
		LOG.debug("SVG: "+svgElement.toXML());
		XMLUtil.debug(svgElement, new File("target/grobid/vec/grobid1.svg"), 1);
	}
	
	@Test
	public void testGrobidVecProblem() throws IOException {
		
		File grobidVecXml = new File(NormaFixtures.TEST_NORMA_DIR, "grobid/vec/image-1a.xml");
		Assert.assertTrue(grobidVecXml.exists());
		
		GrobidVecElement grobidVec = GrobidVecElement.readFile(grobidVecXml);
		SVGElement svgElement = grobidVec.createSVG();
		LOG.debug("SVG: "+svgElement.toXML());
		XMLUtil.debug(svgElement, new File("target/grobid/vec/grobid1a.svg"), 1);
	}
	
	
}
