package org.xmlcml.norma;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.xmlcml.args.DefaultArgProcessor;

public class NormaArgProcessorTest {

	@Test
	public void testArgs() {
		String[] args = {
			"-i", "foo", "bar", 
			"-o", "plugh",
			"-h",
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
		Assert.assertEquals("input", 2, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo", argProcessor.getInputList().get(0));
		Assert.assertEquals("input", "bar", argProcessor.getInputList().get(1));
	}
	
	@Test
	public void testPDF() {
		String[] args = {
			"-i", new File(Fixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf").toString(),
			"-o", "plugh",
			"-h",
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
		Assert.assertEquals("input", 1, argProcessor.getInputList().size());
	}
	
	@Test
	public void testAutoDetect() {
		String[] args = {
			"-i", new File(Fixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf").toString(),
			"-o", "plugh",
			"-h",
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
		Assert.assertEquals("input", 1, argProcessor.getInputList().size());
	}
	
	@Test
	public void testMethod() {
		String inputFilename =  new File(Fixtures.TEST_BMC_DIR, "s12862-014-0277-x.pdf").toString();
		String[] args = {
			"-i", inputFilename,
			"-o", "plugh",
			"-p", "bmc",
		};
		NormaArgProcessor argProcessor = new NormaArgProcessor(args);
		Assert.assertEquals("input", 1, argProcessor.getInputList().size());
		Assert.assertEquals("input",  inputFilename, argProcessor.getInputList().get(0));
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
		Assert.assertEquals("pubstyle", "bmc", argProcessor.getPubstyle().toString());
	}
	
	@Test
	public void testHelp() {
		NormaArgProcessor argProcessor = new NormaArgProcessor(new String[]{"-h"});
	}
	
}
