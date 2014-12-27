package org.xmlcml.nhtml.document.hindawi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.nhtml.InputType;
import org.xmlcml.nhtml.document.DocumentReader;
import org.xmlcml.xml.XMLUtil;

public class HtmlReadTest {

	@Test
	@Ignore // remote read
	public void readRawHtmlSVGTest() throws Exception {
		String urlString = "http://www.hindawi.com/journals/ija/2014/507405/";
		DocumentReader hindawiReader = new HindawiReader(InputType.HTML);
		hindawiReader.readURL(urlString);
		HtmlElement rawHtml = hindawiReader.getOrCreateRawXHtml();
		Assert.assertNotNull("raw input", rawHtml);
		File file = new File("target/htmlsvg/507405.xml");
		FileUtils.touch(file);
		XMLUtil.debug(rawHtml, new FileOutputStream(file), 1);
		long size = FileUtils.sizeOf(file);
		Assert.assertTrue("size "+size, /*(207900 < size) && */(size < 207940));
	}
}
