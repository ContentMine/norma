package org.xmlcml.norma.txt;

import java.util.List;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class SentenceSplitterTest {

	;
	private static final Logger LOG = Logger.getLogger(SentenceSplitterTest.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testSplit() {
		String test = ""
				+ "Hello World! And hello.\n"
				+ "This is a new\n"
				+ " sentence. And so is "
				+ "this one.";
		SentenceSplitter sentenceSplitter = new SentenceSplitter();
		sentenceSplitter.read(test);
		sentenceSplitter.split();
		List<String> sentences = sentenceSplitter.getSentenceList();
		LOG.trace(sentences);
		Assert.assertEquals(4, sentences.size());
	}
}
