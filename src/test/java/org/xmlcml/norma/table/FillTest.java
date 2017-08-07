package org.xmlcml.norma.table;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.euclid.Int2Range;
import org.xmlcml.euclid.IntRange;
import org.xmlcml.euclid.IntRangeComparator;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.euclid.RealRange.Direction;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGGBox;
import org.xmlcml.graphics.svg.SVGLine;
import org.xmlcml.graphics.svg.SVGPath;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.graphics.svg.store.SVGStore;
import org.xmlcml.norma.NormaFixtures;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import junit.framework.Assert;

public class FillTest {
	private static final Logger LOG = Logger.getLogger(FillTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String FILLED = "filled";
	private static final File FILLED_DIR = new File(NormaFixtures.TEST_TABLE_DIR, FILLED);
	private static final File FILLED1007_2 = new File(FILLED_DIR, "/_10.1007.s00038-009-8028-2/tables/table2/table.svg");
	private static final File FILLED1007_3 = new File(FILLED_DIR, "/_10.1007.s00038-009-8028-2/tables/table3/table.svg");
	private static final File FILLED1007_4 = new File(FILLED_DIR, "/_10.1007.s00038-009-8028-2/tables/table4/table.svg");
	private static final File FILLED1016Y_1 = new File(FILLED_DIR, "/_10.1016.j.ypmed.2009.07.022/tables/table1/table.svg");
	private static final File FILLED1016Y_2 = new File(FILLED_DIR, "/_10.1016.j.ypmed.2009.07.022/tables/table2/table.svg");
	private static final File FILLED1016Y_3 = new File(FILLED_DIR, "/_10.1016.j.ypmed.2009.07.022/tables/table3/table.svg");
	private static final File FILLED1016_1 = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table1/table.svg");
	private static final File FILLED1016_2 = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table2/table.svg");
	private static final File FILLED1016_2MICRO = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table2/tablemicro.svg");
	private static final File FILLED1016_2MINI = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table2/tablemini.svg");
	private static final File FILLED1016_3 = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table3/table.svg");
	private static final File FILLED1136_2 = new File(FILLED_DIR, "/10.1136.bmjopen-2016-12335/tables/table2/table.svg");
	private static final File FILLED1136_5 = new File(FILLED_DIR, "/10.1136.bmjopen-2016-12335/tables/table5/table.svg");
	private static final File FILLED1136_6 = new File(FILLED_DIR, "/10.1136.bmjopen-2016-12335/tables/table6/table.svg");
	
	
	File[] FILLED_FILES = new File[] {
		FILLED1007_2,
		FILLED1007_3,
		FILLED1007_4,
		FILLED1016Y_1,
		FILLED1016Y_2,
		FILLED1016Y_3,
		FILLED1016_1,
		FILLED1016_2,
		FILLED1016_3,
		FILLED1136_2,
		FILLED1136_5,
		FILLED1136_6,
	};
	
	private static final double EPS_ANGLE = 0.001;


	@Test
	public void testStyles() throws FileNotFoundException {
		File svgFile = FILLED1016_2MICRO;
		SVGStore svgStore = new SVGStore();
		svgStore.readGraphicsComponents(svgFile);
		SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
		File svgOutFile = NormaFixtures.getCompactSVGFile(new File("target/"+FILLED), new File("target/"+FILLED+"/"+svgFile.getPath()+"micro"));
		LOG.debug(">>"+svgOutFile);
		SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1000., 1000.);
	}

	/**
<defs>
    <radialGradient id="grad1" cx="50%" cy="50%" r="50%" fx="50%" fy="50%">
      <stop offset="0%" style="stop-color:rgb(255,255,255);
      stop-opacity:0" />
      <stop offset="100%" style="stop-color:rgb(0,0,255);stop-opacity:1" />
    </radialGradient>
  </defs>
  <ellipse cx="200" cy="70" rx="85" ry="55" fill="url(#grad1)" />
  
  	 */
	/** rotate element positions position
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testReadGraphicsComponents() throws FileNotFoundException {
		for (File svgFile : FILLED_FILES) {
			SVGStore svgStore = new SVGStore();
			svgStore.readGraphicsComponents(svgFile);
			SVGElement svgElement = (SVGElement) svgStore.getExtractedSVGElement();
			// this is inefficient but OK for now
			List<SVGElement> descendants = SVGElement.extractSelfAndDescendantElements(svgElement);
			List<SVGRect> rectList = SVGRect.extractSelfAndDescendantRects(svgElement);
			List<SVGGBox> boxList = new ArrayList<SVGGBox>();
			SVGSVG.wrapAndWriteAsSVG(svgElement, new File("target/"+FILLED+"/"+svgFile.getPath()+".elems.svg"));
		}
	}
	
	private static final File FILLED1007_2_ELEM = new File(FILLED_DIR, "/_10.1007.s00038-009-8028-2/tables/table2/table.svg.elems.svg");
	private static final File FILLED1007_3_ELEM = new File(FILLED_DIR, "/_10.1007.s00038-009-8028-2/tables/table3/table.svg.elems.svg");
	private static final File FILLED1007_4_ELEM = new File(FILLED_DIR, "/_10.1007.s00038-009-8028-2/tables/table4/table.svg.elems.svg");
	private static final File FILLED1016Y_1_ELEM = new File(FILLED_DIR, "/_10.1016.j.ypmed.2009.07.022/tables/table1/table.svg.elems.svg");
	private static final File FILLED1016Y_2_ELEM = new File(FILLED_DIR, "/_10.1016.j.ypmed.2009.07.022/tables/table2/table.svg.elems.svg");
	private static final File FILLED1016Y_3_ELEM = new File(FILLED_DIR, "/_10.1016.j.ypmed.2009.07.022/tables/table3/table.svg.elems.svg");
	private static final File FILLED1016_1_ELEM = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table1/table.svg.elems.svg");
	private static final File FILLED1016_2_ELEM = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table2/table.svg.elems.svg");
	private static final File FILLED1016_3_ELEM = new File(FILLED_DIR, "/10.1016.S2213-2600_14_70195-X/tables/table3/table.svg.elems.svg");
	private static final File FILLED1136_2_ELEM = new File(FILLED_DIR, "/10.1136.bmjopen-2016-12335/tables/table2/table.svg.elems.svg");
	private static final File FILLED1136_5_ELEM = new File(FILLED_DIR, "/10.1136.bmjopen-2016-12335/tables/table5/table.svg.elems.svg");
	private static final File FILLED1136_6_ELEM = new File(FILLED_DIR, "/10.1136.bmjopen-2016-12335/tables/table6/table.svg.elems.svg");

	File[] FILLED_ELEM_FILES = new File[] {
			FILLED1007_2_ELEM,
			FILLED1007_3_ELEM,
			FILLED1007_4_ELEM,
			FILLED1016Y_1_ELEM,
			FILLED1016Y_2_ELEM,
			FILLED1016Y_3_ELEM,
			FILLED1016_1_ELEM,
			FILLED1016_2_ELEM,
			FILLED1016_3_ELEM,
			FILLED1136_2_ELEM,
			FILLED1136_5_ELEM,
			FILLED1136_6_ELEM,
		};

	/** rotate element positions position
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testElemFiles() throws FileNotFoundException {
		List<List<Integer>> countListList = new ArrayList<List<Integer>>();
		
		for (File svgFile : FILLED_ELEM_FILES) {
			SVGElement svgElement = SVGElement.readAndCreateSVG(svgFile);
			List<SVGElement> descendants = SVGElement.extractSelfAndDescendantElements(svgElement);
			List<SVGRect> rectList = SVGRect.extractSelfAndDescendantRects(svgElement);
			List<SVGLine> lineList = SVGLine.extractSelfAndDescendantLines(svgElement);
			List<SVGText> textList = SVGText.extractSelfAndDescendantTexts(svgElement);
			List<SVGPath> pathList = SVGPath.extractSelfAndDescendantPaths(svgElement);
			List<Integer> row = new ArrayList<Integer>();
			row.add(descendants.size());
			row.add(rectList.size());
			row.add(lineList.size());
			row.add(textList.size());
			row.add(pathList.size());
			countListList.add(row);
			LOG.debug(">>"+row);
		}
		LOG.debug(">>"+countListList);
	}
	
	@Test
	public void testCreateRow() {
		SVGElement svgElement = SVGElement.readAndCreateSVG(FILLED1007_2_ELEM);
		List<SVGRect> rectList = SVGRect.extractSelfAndDescendantRects(svgElement);
		Multimap<IntRange, SVGRect> rectByIntYRange = ArrayListMultimap.create();
		for (SVGRect rect : rectList) {
			rect.setBoundingBoxCached(true);
			Real2Range r2range = rect.getBoundingBox();
			Int2Range i2range = new Int2Range(r2range);
			IntRange irange = new IntRange(r2range.getRealRange(Direction.VERTICAL));
			rectByIntYRange.put(irange, rect);
		}
		SVGG g = new SVGG();
		Set<IntRange> keySet = rectByIntYRange.keySet();
		List<IntRange> keyList = new ArrayList<IntRange>(keySet);
		Comparator<IntRange> lowEndComparator = new IntRangeComparator(IntRangeComparator.End.MIN);
		Collections.sort(keyList, lowEndComparator);
		Multiset<IntRange> rangeSet = HashMultiset.create();
		for (IntRange yrange : keyList) {
			LOG.debug(">>"+yrange+"; "+yrange.getRange());
			SVGLine line = new SVGLine(new Real2(10., yrange.getMin()), new Real2(10., yrange.getMax()));
			line.setCSSStyle("stroke-width:2.;stroke:blue;");
			g.appendChild(line);
			List<SVGRect> rowList = new ArrayList<SVGRect>(rectByIntYRange.get(yrange));
			for (SVGRect rowRect : rowList) {
				SVGRect r1 = new SVGRect(rowRect);
				g.appendChild(r1);
				Real2Range b1 = r1.getBoundingBox();
				IntRange range = new IntRange(b1.getXRange());
				rangeSet.add(range);
				Real2Range b2 = b1.getReal2RangeExtendedInX(-2.0, -2.0).getReal2RangeExtendedInY(-2.0,  -2.0);
				SVGRect r2 = SVGRect.createFromReal2Range(b1);
				r2.setCSSStyle("stroke:green;stroke-width:0.5;fill:none;");
				g.appendChild(r2);
			}
		}
		List<SVGText> textList = SVGText.extractSelfAndDescendantTexts(svgElement);
		for (SVGText text : textList) {
			g.appendChild(text.copy());
		}
		SVGSVG.wrapAndWriteAsSVG(g, new File("target/"+FILLED+"/rows.svg"));
		LOG.debug(">ranges>"+rangeSet);
	}


	/** rotate element positions position
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testNestedNYI() throws FileNotFoundException {
		File cProjectDir = new File(NormaFixtures.TEST_TABLE_DIR, FILLED);
		Assert.assertTrue(cProjectDir.exists());
		int count = 0;
		for (File svgFile : FILLED_FILES) {
			File svgOutFile = new File("target/"+FILLED+"/"+svgFile.getPath());
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
			SVGSVG svg = new SVGSVG();
//			SVGDefs defs = svg.getOrCreateDefs();
//			SVGElement gradient = SVGRadialGradient.getDefaultRadialGradient();
//			defs.appendChild(gradient);
//			String defsId = gradient.createURLIdRef();
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
							} else if (elem instanceof SVGText) {
//								((SVGText)elem).
							}
							LOG.trace(rectBox+" includes "+elem.toString());
//							SVGRect containedRect0 = SVGRect.createFromReal2Range(elemBox);
//							containedRect.setClassName("containedRect");
//							containedRect.setCSSStyle("stroke:red;stroke-width:0.5;fill:"+defsId+";");
//							svgElement.appendChild(containedRect);
							descendants.remove(j);
							rect.setCSSStyle("opacity:0.3;fill:"+color[icolor++ % 3]+";stroke:blue;stroke-width:2.0;");
//							SVGRect innerRectBox = SVGRect.createFromReal2Range(rectBox0);
//							innerRectBox.setCSSStyle("opacity:0.2;fill:none;stroke:green;stroke-width:0.5;");
//							svgElement.appendChild(innerRectBox);
							SVGGBox box = new SVGGBox();
							change = true;
						}
					}
// ?					if (change) break;
				}
			}
			
//			List<SVGContainer> containerList = SVGContainer.makeContainerList(svgElement);
			
//			SVGSVG.wrapAndWriteAsSVG(svgElement, svgOutFile, 1200., 1000.);
			File outfile = NormaFixtures.getCompactSVGFile(new File("target/"+FILLED), svgOutFile);
			LOG.debug(">>>"+outfile);
			svg.appendChild(svgElement);
			svg.writeQuietly(outfile);
		}
	}


	private Real2Range makeResizedBbox(SVGRect rect, int delta) {
		Real2Range rectBox = rect.getBoundingBox();
		rectBox.extendBothEndsBy(Direction.HORIZONTAL, delta, delta);
		rectBox.extendBothEndsBy(Direction.VERTICAL, delta, delta);
		return rectBox;
	}
}
