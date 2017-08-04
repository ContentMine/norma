package org.xmlcml.norma.table;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.RealRange.Direction;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGGBox;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.store.SVGStore;
import org.xmlcml.norma.NormaFixtures;

import junit.framework.Assert;

public class GriddedTest {
	private static final Logger LOG = Logger.getLogger(GriddedTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	private static final File GRIDDED = new File(NormaFixtures.TEST_TABLE_DIR, "gridded");
	private static final File GRIDDED1007_2 = new File(GRIDDED, "/_10.1007.s00038-009-8028-2/tables/table2/table.svg");
	private static final File GRIDDED1007_3 = new File(GRIDDED, "/_10.1007.s00038-009-8028-2/tables/table3/table.svg");
	private static final File GRIDDED1007_4 = new File(GRIDDED, "/_10.1007.s00038-009-8028-2/tables/table4/table.svg");
	private static final File GRIDDED1016Y_1 = new File(GRIDDED, "/_10.1016.j.ypmed.2009.07.022/tables/table1/table.svg");
	private static final File GRIDDED1016Y_2 = new File(GRIDDED, "/_10.1016.j.ypmed.2009.07.022/tables/table2/table.svg");
	private static final File GRIDDED1016Y_3 = new File(GRIDDED, "/_10.1016.j.ypmed.2009.07.022/tables/table3/table.svg");
	private static final File GRIDDED1016_1 = new File(GRIDDED, "/10.1016.S2213-2600_14_70195-X/tables/table1/table.svg");
	private static final File GRIDDED1016_2 = new File(GRIDDED, "/10.1016.S2213-2600_14_70195-X/tables/table2/table.svg");
	private static final File GRIDDED1016_2MICRO = new File(GRIDDED, "/10.1016.S2213-2600_14_70195-X/tables/table2/tablemicro.svg");
	private static final File GRIDDED1016_2MINI = new File(GRIDDED, "/10.1016.S2213-2600_14_70195-X/tables/table2/tablemini.svg");
	private static final File GRIDDED1016_3 = new File(GRIDDED, "/10.1016.S2213-2600_14_70195-X/tables/table3/table.svg");
	private static final File GRIDDED1136_2 = new File(GRIDDED, "/10.1136.bmjopen-2016-12335/tables/table2/table.svg");
	private static final File GRIDDED1136_5 = new File(GRIDDED, "/10.1136.bmjopen-2016-12335/tables/table5/table.svg");
	private static final File GRIDDED1136_6 = new File(GRIDDED, "/10.1136.bmjopen-2016-12335/tables/table6/table.svg");
	
	
	File[] GRIDDED_FILES = new File[] {
		GRIDDED1007_2,
		GRIDDED1007_3,
		GRIDDED1007_4,
		GRIDDED1016Y_1,
		GRIDDED1016Y_2,
		GRIDDED1016Y_3,
		GRIDDED1016_1,
		GRIDDED1016_2,
		GRIDDED1016_3,
		GRIDDED1136_2,
		GRIDDED1136_5,
		GRIDDED1136_6,
	};
	
	private static final double EPS_ANGLE = 0.001;


	@Test
	public void testStyles() throws FileNotFoundException {
		File svgFile = GRIDDED1016_2MICRO;
		SVGStore svgStore = new SVGStore();
		svgStore.readGraphicsComponents(svgFile);
		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
		File svgOutFile = NormaFixtures.getCompactSVGFile(new File("target/gridded"), new File("target/gridded"+"/"+svgFile.getPath()+"micro"));
		LOG.debug(">>"+svgOutFile);
		SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1000., 1000.);
	}
	
	/** rotate element positions position
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testReadGraphicsComponents() throws FileNotFoundException {
		File cProjectDir = new File(NormaFixtures.TEST_TABLE_DIR, "rotated");
		Assert.assertTrue(cProjectDir.exists());
		int count = 0;
		for (File svgFile : GRIDDED_FILES) {
			File svgOutFile = new File("target/gridded"+"/"+svgFile.getPath());
			SVGStore svgStore = new SVGStore();
			svgStore.readGraphicsComponents(svgFile);
			SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
			// this is inefficient but OK for now
			List<SVGElement> descendants = SVGElement.extractSelfAndDescendantElements(svgElement);
			List<SVGRect> rectList = SVGRect.extractSelfAndDescendantRects(svgElement);
			List<SVGGBox> boxList = new ArrayList<SVGGBox>();
			boolean change = true;
			String[] color = {"yellow", "pink", "gray", "cyan"};
			int icolor = 0;
			while (change) {
				change = false;
				for (int i = rectList.size() - 1; i >= 0; i--) {
					SVGRect rect = rectList.get(i);
					rect.setBoundingBoxCached(true);
					// this is to search. We extend because fonts may cross boundaries
					Real2Range rectBox = makeResizedBbox(rect, 3);
					// this is for display only
					Real2Range rectBox0 = makeResizedBbox(rect, -2);
					for (int j = descendants.size() - 1; j >= 0; j--) {
						SVGElement elem = descendants.get(j);
						elem.setBoundingBoxCached(true);
						if (elem == rect) continue; // don't insert into self
						Real2Range elemBox = elem.getBoundingBox();
						if (rectBox.includes(elemBox)) {
							if (elem instanceof SVGRect) {
								LOG.info("nested boxes "+elemBox+" in "+rectBox);
							}
							LOG.trace(rectBox+" includes "+elem.toString());
							SVGRect rr = SVGRect.createFromReal2Range(elemBox);
							rr.setClassName("rr");
							rr.setCSSStyle("stroke:red;stroke-width:0.5;fill:none;");
							svgElement.appendChild(rr);
							descendants.remove(j);
							rect.setCSSStyle("opacity:0.3;fill:"+color[icolor++ % 3]+";stroke:blue;stroke-width:2.0;");
							SVGRect innerRectBox = SVGRect.createFromReal2Range(rectBox0);
							innerRectBox.setCSSStyle("opacity:0.2;fill:none;stroke:green;stroke-width:0.5;");
							svgElement.appendChild(innerRectBox);
							SVGGBox box = new SVGGBox();
							change = true;
						}
					}
// ?					if (change) break;
				}
			}
			
//			List<SVGContainer> containerList = SVGContainer.makeContainerList(svgElement);
			
//			SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1200., 1000.);
			SVGSVG.wrapAndWriteAsSVG(svgElement, NormaFixtures.getCompactSVGFile(new File("target/gridded"), svgOutFile), 1000., 1000.);
		}
	}

	private Real2Range makeResizedBbox(SVGRect rect, int delta) {
		Real2Range rectBox = rect.getBoundingBox();
		rectBox.extendBothEndsBy(Direction.HORIZONTAL, delta, delta);
		rectBox.extendBothEndsBy(Direction.VERTICAL, delta, delta);
		return rectBox;
	}
}
