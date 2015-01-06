package org.xmlcml.norma;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class NormaArgProcessorTest {

	@Test
	public void testArgs() {
		String[] args = {
			"-i", "foo", "bar", 
			"-o", "plugh",
			"-h",
		};
		NormaArgProcessor argProcessor = new NormaArgProcessor(args);
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
		Assert.assertEquals("input", 2, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo", argProcessor.getInputList().get(0));
		Assert.assertEquals("input", "bar", argProcessor.getInputList().get(1));
	}
	
//	@Test
//	public void testFoo() {
//		Shard shard;
//	}
	
	@Test
	public void testPDF() {
		String[] args = {
			"-i", new File(Fixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf").toString(),
			"-o", "plugh",
			"-h",
		};
		NormaArgProcessor argProcessor = new NormaArgProcessor(args);
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
		Assert.assertEquals("input", 1, argProcessor.getInputList().size());
	}
	
}
