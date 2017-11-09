/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.html.HtmlBody;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlHtml;
import org.xmlcml.html.HtmlTable;
import org.xmlcml.html.HtmlThead;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

/**
 *
 * @author jkb
 */
public class ColumnTreeHeaderTest {
           
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable1() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1080.21642850.2016.1256211_table1.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile); 
                HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1080.21642850.2016.1256211_table1.html");
		XMLUtil.debug(htmlElement, file, 1);
                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable2() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1093.alcalc.ags133_table2.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1093.alcalc.ags133_table2.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
                     
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable4() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1093.alcalc.ags133_table3.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1093.alcalc.ags133_table3.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable5() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1186.s12966-017-0535-6_table3.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1186.s12966-017-0535-6_table3.svg.html");
		XMLUtil.debug(htmlElement, file, 1);

                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with more than one level of spanning column headers 
        public void testColumnTreeHeaderTable6() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1186.1471-2458-14-563_table3.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1186.1471-2458-14-563_table3.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 3 header rows
                Assert.assertEquals(3, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable7() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1093.alcalc.ags133_table5.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1093.alcalc.ags133_table5.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable8() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1177.1029864916682822_table5.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1177.1029864916682822_table5.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with nested and spanning column headers 
        public void testColumnTreeHeaderTable9() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1136.bmjopen-2016-12335_table2.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1136.bmjopen-2016-12335_table2.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        public void testColumnTreeHeaderTable10() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1177.1029864916682822_table3.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1177.1029864916682822_table3.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 2 header rows
                Assert.assertEquals(2, restructuredTableHeaderRowCount);
        }
        
        @Test
        // Table with column headers on multiple lines but without partial horizontal rules which indicate a column tree
        public void testLineWrappedColumnHeaderTable1() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1016.j.pec.2005.10.002_table2.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1016.j.pec.2005.10.002_table2.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
                
                int restructuredTableHeaderRowCount = (htmlElement == null ? 0 : getRestructuredHeaderRowCount(htmlElement));
                
                // Restructured table has 1 header row -- regression test
                Assert.assertEquals(1, restructuredTableHeaderRowCount);
        }
        
        /**
         * Helper method
         * 
         * @return The number of header rows in the main table's thead element
         */
        private int getRestructuredHeaderRowCount(HtmlElement htmlElement) {
                int restructuredTableRowCount = 0;
                HtmlHtml html = (HtmlHtml)htmlElement;
                
                HtmlBody body = html.getBody();
                
                if (body != null) {
                    HtmlTable table = (HtmlTable)body.getChild(0);
                    
                    if (table != null) {
                        HtmlThead thead = table.getThead();
                        
                        if (thead != null) {
                            restructuredTableRowCount = thead.getChildCount();
                        }
                    }
                }   
                
                return restructuredTableRowCount;
        }
}
