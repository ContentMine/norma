package org.xmlcml.nhtml.journal.hindawi;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.nhtml.InputType;
import org.xmlcml.nhtml.journal.DocumentReader;
import org.xmlcml.xml.XMLUtil;

public class HtmlReadTest {

	@Test
	public void readRawHtmlSVGTest() throws Exception {
		String urlString = "http://www.hindawi.com/journals/ija/2014/507405/";
		DocumentReader hindawiReader = new HindawiReader(InputType.HTML);
		hindawiReader.readURL(urlString);
		HtmlElement rawHtml = hindawiReader.getOrCreateRawXHtml();
		Assert.assertNotNull("raw input", rawHtml);
		File file = new File("target/htmlsvg/");
		file.mkdirs();
		XMLUtil.debug(rawHtml, new FileOutputStream(new File(file, "507405.xml")), 1);
	}
}
