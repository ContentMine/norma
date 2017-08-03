/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;
import nu.xom.Elements;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;
import org.junit.Assert;

/**
 *
 * @author jkb
 */
public class SubtableTest {
    	/** 
	 * Detect subtables from indentation
         * 
	 * @return
	 * @throws IOException 
	 */
	@Test
	public void testSimpleSubtable() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "subtables/10.1007_s00213-015-4198-1.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/subtables/10.1007_s00213-015-4198-1.svg.html");
		XMLUtil.debug(htmlElement, file, 1);

                int restructuredTableRowCount = getRestructuredRowCount(htmlElement);
                
                // Restructured table has children: 
                // caption, header row and 3 subtables, no top-level observation rows
                Assert.assertEquals(5, restructuredTableRowCount);
	}
        
        @Test
        // Table has 1 header row and 2 subtables
	public void testSimpleSubtable2() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "subtables/10.1136.bmjopen-2016-12335_table5.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/subtables/10.1136.bmjopen-2016-12335_table5.html");
		XMLUtil.debug(htmlElement, file, 1);

                int restructuredTableRowCount = getRestructuredRowCount(htmlElement);
                
                // Restructured table has children: 
                // caption, header row and 2 subtables, no top-level observation rows
                Assert.assertEquals(4, restructuredTableRowCount);
	}
        
        @Test
        // Note: This table is not fully cell resolved as some cells are in he wrong grid position
	public void testSimpleSubtable3() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "subtables/10.1016.j.pec.2005.10.002_table3.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/subtables/10.1016.j.pec.2005.10.002_table3.html");
		XMLUtil.debug(htmlElement, file, 1);

                int restructuredTableRowCount = getRestructuredRowCount(htmlElement);
                
                // Restructured table has children: 
                // caption, header row and 2 subtables, no top-level observation rows
                // FIXME currently fails to be fully correctly cell-resolved due to superscripts
                // pushing cells onto the next row in the wrong column
                Assert.assertEquals(4, restructuredTableRowCount);
	}
        
        @Test
        // Table without any subtables just simple top-level rows -- regression test
        public void testNoSubtablesTable() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "subtables/10.1136.bmjopen-2016-12335_table6.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/subtables/10.1136.bmjopen-2016-12335_table6.html");
		XMLUtil.debug(htmlElement, file, 1);

                int restructuredTableRowCount = getRestructuredRowCount(htmlElement);
                
                // Restructured table has children: 
                // caption, header row and 8 content rows
                Assert.assertEquals(10, restructuredTableRowCount);
	}
        
        @Test
        // Table without any subtables just simple top-level rows -- regression test
        public void testNoSubtablesTable2() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "subtables/10.1080.15504263.2015.1113842_table3.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/subtables/10.1080.15504263.2015.1113842_table3.html");
		XMLUtil.debug(htmlElement, file, 1);
                
                int restructuredTableRowCount = getRestructuredRowCount(htmlElement);
                
                // Restructured table has children: 
                // caption, header row and 4 content rows
                Assert.assertEquals(6, restructuredTableRowCount); 
	}
        
        /// 10.1186.1471-2407-12-560_table2.svg
        @Test
        // Table mixing subtables and top-level rows 
        public void testMixedSubtablesTable1() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "subtables/10.1186.1471-2407-12-560_table2.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/subtables/10.1186.1471-2407-12-560_table2.html");
		XMLUtil.debug(htmlElement, file, 1);
                
                int restructuredTableRowCount = getRestructuredRowCount(htmlElement);
                
                // Restructured table has children: 
                // caption, header row and 6 content rows, including a subtable
                Assert.assertEquals(8, restructuredTableRowCount); 
        }
        
        /**
         * Helper method
         * 
         * @return The number of children of the main (outermost) table element
         */
        private int getRestructuredRowCount(HtmlElement htmlElement) {
                int restructuredTableRowCount = 0;
                
                HtmlElement body = (HtmlElement) htmlElement.getChild(0);
                if (body != null) {
                    HtmlElement table = (HtmlElement) body.getChild(0);
                    if (table != null) {
                        restructuredTableRowCount = table.getChildCount();
                    }
                }   
                
                return restructuredTableRowCount;
        }
}
