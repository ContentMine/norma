package org.xmlcml.norma;


import java.io.File;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.euclid.Angle;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.Real2Range.BoxDirection;
import org.xmlcml.euclid.Transform2;
import org.xmlcml.graphics.svg.SVGCircle;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.xml.XMLUtil;

import junit.framework.Assert;
import nu.xom.Element;

public class RotatedTest {
	private static final Logger LOG = Logger.getLogger(RotatedTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final File ROTATED1186_2= new File(NormaFixtures.TEST_TABLE_DIR, "rotated/10.1186.s12966-017-0535-6/tables/table2/table.svg");
	private static final File ROTATED1186_1= new File(NormaFixtures.TEST_TABLE_DIR, "rotated/10.1186.s12966-017-0535-6/tables/table1/table.svg");
	private static final File ROTATED1177_6= new File(NormaFixtures.TEST_TABLE_DIR, "rotated/10.1177.1029864916682822/tables/table6/table.svg");
	private static final File ROTATED1177_4= new File(NormaFixtures.TEST_TABLE_DIR, "rotated/10.1177.1029864916682822/tables/table4/table.svg");
	private static final File ROTATED_TABLE3 = new File(NormaFixtures.TEST_TABLE_DIR, "rotated/10.1111.add.12677/tables/table3/table.svg");
	File[] ROT_FILES = new File[]{
			ROTATED1177_4,
			ROTATED1177_6,
			ROTATED1186_1,
			ROTATED1186_2,
			ROTATED_TABLE3,
	};
	private static final File ROTATED_TABLE3A = new File(NormaFixtures.TEST_TABLE_DIR, "rotated/10.1111.add.12677/tables/table3/tablea.svg");
	private static final double EPS_ANGLE = 0.001;

	/** unrotate characters
	 * this is for visual checking
	 */
	@Test
	public void testRotateCharactersOnly() {
		Assert.assertTrue("table3 exists", ROTATED_TABLE3.exists());
		SVGElement tableElement = SVGElement.readAndCreateSVG(ROTATED_TABLE3);
		List<SVGText> textLists = SVGText.extractSelfAndDescendantTexts(tableElement);
		SVGG g = new SVGG();
		for (SVGText text : textLists) {
			Angle rot = text.getAngleOfRotation();
			Assert.assertEquals("rot", Math.PI/2, rot.getRadian(), EPS_ANGLE);
			// removing the transform "unrotates" the character
			text.removeAttribute(SVGElement.TRANSFORM);
			g.appendChild(text.copy());
		}
		SVGSVG.wrapAndWriteAsSVG(g, new File("target/rotate/table3.svg"));
		
	}
	/** rotate element positions position
	 * 
	 */
	@Test
	public void testRotateCoordinatesAboutSquareCentreAndReOrientChars() {
		for (File svgFile : ROT_FILES) {
			Assert.assertTrue("table3 exists", svgFile.exists());
			SVGElement tableElement = SVGElement.readAndCreateSVG(svgFile);
			Real2Range bbox = tableElement.getBoundingBox();
			SVGG g = new SVGG();
			
			List<SVGText> textLists = SVGText.extractSelfAndDescendantTexts(tableElement);
			
			Real2 centre = bbox.getSquareBoxCentre(BoxDirection.LEFT);
			g.appendChild(new SVGCircle(centre, 3.0));
			Angle clock90 = new Angle(-Math.PI/2.);
			Transform2 t90 = new Transform2(clock90);
			for (SVGText text : textLists) {
				SVGText text1 = new SVGText(text);
				text1.rotateTextAboutPoint(centre, t90);
				g.appendChild(text1);
			}
			SVGSVG.wrapAndWriteAsSVG(g, new File("target/rotate"+"/"+svgFile.getPath()), 1000., 1000.);
		}
		
	}
	
	/** rotate element positions position
	 * 
	 */
	@Test
	public void testRotateRot90TablesAndOutput() {
		File cProjectDir = new File(NormaFixtures.TEST_TABLE_DIR, "rotated");
		Assert.assertTrue(cProjectDir.exists());
		for (File svgFile : ROT_FILES) {
			File svgOutFile = new File("target/rotate"+"/"+svgFile.getPath());
			SVGElement svgElementIn = SVGElement.readAndCreateSVG(svgFile);
			SVGElement g = svgElementIn.createElementWithRotatedDescendants(new Angle(Math.PI/2.));
			SVGSVG.wrapAndWriteAsSVG(g, svgOutFile, 1000., 1000.);
		}
		
	}

}
