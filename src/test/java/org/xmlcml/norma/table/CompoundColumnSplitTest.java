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
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

/**
 *
 * @author jkb
 */
public class CompoundColumnSplitTest {
        @Test
        // Table without any subtables just simple top-level rows -- regression test
        public void testSplitColumnsWithIntegersAndFloats1() throws IOException {
					File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "compoundcolumns/10.1186.1471-2407-12-560_table1.svg");
					SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
					converter.readInput(inputFile);
					HtmlElement htmlElement = converter.convert();
					File file = new File(NormaFixtures.TARGET_DIR, "table/compoundcolumns/10.1186.1471-2407-12-560_table1.html");
					XMLUtil.debug(htmlElement, file, 1);
				}
}
