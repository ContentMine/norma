package org.xmlcml.norma;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

public class DefaultArgProcessorTest {

	
	private static final Logger LOG = Logger.getLogger(DefaultArgProcessorTest.class);
	static {LOG.setLevel(org.apache.log4j.Level.DEBUG);}

	@Test
	public void testArgs() {
		String[] args = {
			"-i", "foo", "bar", 
			"-o", "plugh",
			"-h",
		};
		DefaultArgProcessor argProcessor = new DefaultArgProcessor();
		argProcessor.parseArgs(args);
		Assert.assertEquals("input", 2, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo", argProcessor.getInputList().get(0));
		Assert.assertEquals("input", "bar", argProcessor.getInputList().get(1));
		Assert.assertEquals("output", "plugh", argProcessor.getOutput());
	}

	@Test
	public void testSingleWildcards() {
		String[] args = {
			"-i", "foo{1:3}bof", "bar{a|b|zzz}plugh", 
		};
		DefaultArgProcessor argProcessor = new DefaultArgProcessor();
		argProcessor.parseArgs(args);
		Assert.assertEquals("input", 2, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo{1:3}bof", argProcessor.getInputList().get(0));
		Assert.assertEquals("input", "bar{a|b|zzz}plugh", argProcessor.getInputList().get(1));
		argProcessor.expandWildcardsExhaustively();
		Assert.assertEquals("input", 6, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo1bof", argProcessor.getInputList().get(0));
		Assert.assertEquals("input", "foo2bof", argProcessor.getInputList().get(1));
		Assert.assertEquals("input", "foo3bof", argProcessor.getInputList().get(2));
		Assert.assertEquals("input", "baraplugh", argProcessor.getInputList().get(3));
		Assert.assertEquals("input", "barbplugh", argProcessor.getInputList().get(4));
		Assert.assertEquals("input", "barzzzplugh", argProcessor.getInputList().get(5));
	}
	
	
	@Test
	public void testMultipleWildcards() {
		String[] args = {
			"-i", "foo{1:3}bof{3:6}plugh",
		};
		DefaultArgProcessor argProcessor = new DefaultArgProcessor();
		argProcessor.parseArgs(args);
		Assert.assertEquals("input", 1, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo{1:3}bof{3:6}plugh", argProcessor.getInputList().get(0));
		argProcessor.expandWildcardsExhaustively();
		Assert.assertEquals("input", 12, argProcessor.getInputList().size());
		Assert.assertEquals("input", "foo1bof3plugh", argProcessor.getInputList().get(0));
	}
}
