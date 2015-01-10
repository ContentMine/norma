package org.xmlcml.norma.document.hindawi;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.document.hindawi.HindawiReader;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.xml.XMLUtil;

public class HtmlReadTest {

	@Test
	@Ignore // remote read
	public void readRawHtmlTest() throws Exception {
		String urlString = "http://www.hindawi.com/journals/ija/2014/507405/";
		PubstyleReader hindawiReader = new HindawiReader(InputFormat.HTML);
		hindawiReader.readURL(urlString);
		HtmlElement rawHtml = hindawiReader.getOrCreateXHtmlFromRawHtml();
		Assert.assertNotNull("raw input", rawHtml);
		File file = new File("target/htmlsvg/507405.xml");
		FileUtils.touch(file);
		XMLUtil.debug(rawHtml, new FileOutputStream(file), 0);
		long size = FileUtils.sizeOf(file);
		Assert.assertTrue("size "+size, /*(207900 < size) && */(size < 207940));
	}
}
