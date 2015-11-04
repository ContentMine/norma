package org.xmlcml.norma.image.ocr;

import org.junit.Assert;
import org.junit.Test;

public class SimpleFontMetricTest {
	
	@Test
	public void testAscenders() {
		SimpleFontMetrics fontMetrics = new SimpleFontMetrics("abcd");
		Assert.assertTrue("ascenders", fontMetrics.hasAscenders());
		fontMetrics.readString("ABCD");
		Assert.assertTrue("ascenders", fontMetrics.hasAscenders());
		fontMetrics.readString("acvwx");
		Assert.assertFalse("ascenders", fontMetrics.hasAscenders());
		fontMetrics.readString("acgy");
		Assert.assertFalse("ascenders", fontMetrics.hasAscenders());
		fontMetrics.readString("abcdgy");
		Assert.assertTrue("ascenders", fontMetrics.hasAscenders());
	}
	
	@Test
	public void testDescenders() {
		SimpleFontMetrics fontMetrics = new SimpleFontMetrics("abcd");
		Assert.assertFalse("descenders", fontMetrics.hasDescenders());
		fontMetrics.readString("ABCD");
		Assert.assertFalse("descenders", fontMetrics.hasDescenders());
		fontMetrics.readString("acvwx");
		Assert.assertFalse("descenders", fontMetrics.hasDescenders());
		fontMetrics.readString("acgy");
		Assert.assertTrue("descenders", fontMetrics.hasDescenders());
		fontMetrics.readString("abcdgy");
		Assert.assertTrue("descenders", fontMetrics.hasDescenders());
	}
	
	@Test
	public void testRatios() {
		SimpleFontMetrics fontMetrics = new SimpleFontMetrics("abcd");
		Assert.assertEquals("ascender ascender", 0.3, fontMetrics.getAscenderFraction(), 0.01);
		fontMetrics.readString("acev");
		Assert.assertEquals("median ascender", 0.0, fontMetrics.getAscenderFraction(), 0.01);
		fontMetrics.readString("apqo");
		Assert.assertEquals("descender ascender", 0.0, fontMetrics.getAscenderFraction(), 0.01);
		fontMetrics.readString("apbqto");
		Assert.assertEquals("both ascender", 0.3, fontMetrics.getAscenderFraction(), 0.01);
		fontMetrics.readString("abcd");
		Assert.assertEquals("descender descender", 0.0, fontMetrics.getDescenderFraction(), 0.01);
		fontMetrics.readString("acev");
		Assert.assertEquals("median descender", 0.0, fontMetrics.getDescenderFraction(), 0.01);
		fontMetrics.readString("apqo");
		Assert.assertEquals("descender descender", 0.3, fontMetrics.getDescenderFraction(), 0.01);
		fontMetrics.readString("apbqto");
		Assert.assertEquals("both descender", 0.3, fontMetrics.getDescenderFraction(), 0.01);
	}

}
