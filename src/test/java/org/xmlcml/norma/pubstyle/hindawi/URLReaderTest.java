package org.xmlcml.norma.pubstyle.hindawi;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.RawInput;
import org.xmlcml.norma.pubstyle.PubstyleReader;

public class URLReaderTest {

	@Test
	@Ignore // uses URL
	public void testReadURL() throws Exception {
		String urlString = "http://www.hindawi.com/journals/ija/2014/507405/";
		PubstyleReader hindawiReader = new HindawiReader(InputFormat.HTML);
		hindawiReader.readURL(urlString);
		RawInput rawInput = hindawiReader.getRawInput();
		Assert.assertNotNull("raw input", rawInput);
		byte[] bytes = rawInput.getRawBytes();
		Assert.assertNotNull("read bytes", bytes);
		Assert.assertEquals("bytes read", 111681, bytes.length);
	}
	
}
