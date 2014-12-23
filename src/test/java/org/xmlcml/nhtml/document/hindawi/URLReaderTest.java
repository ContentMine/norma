package org.xmlcml.nhtml.document.hindawi;

import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.nhtml.InputType;
import org.xmlcml.nhtml.RawInput;
import org.xmlcml.nhtml.document.DocumentReader;
import org.xmlcml.nhtml.document.hindawi.HindawiReader;

public class URLReaderTest {

	@Test
	public void testReadURL() throws Exception {
		String urlString = "http://www.hindawi.com/journals/ija/2014/507405/";
		DocumentReader hindawiReader = new HindawiReader(InputType.HTML);
		hindawiReader.readURL(urlString);
		RawInput rawInput = hindawiReader.getRawInput();
		Assert.assertNotNull("raw input", rawInput);
		byte[] bytes = rawInput.getRawBytes();
		Assert.assertNotNull("read bytes", bytes);
		Assert.assertEquals("bytes read", 111681, bytes.length);
	}
	
}
