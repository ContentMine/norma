/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;
import org.junit.Assert;

/**
 *
 * @author jkb
 */
public class SupercolumnTest {
    	/** 
	 * Detect supercolumns from nested column headings
         * 
	 * @throws IOException 
	 */
	@Test
	public void testSimpleSupercolumn1() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "supercolumns/10.1080.21642850.2016.1256211_table1.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/supercolumns/10.1080.21642850.2016.1256211_table1.svg.html");
		XMLUtil.debug(htmlElement, file, 1);

	}
}
