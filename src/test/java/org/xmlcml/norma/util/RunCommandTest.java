package org.xmlcml.norma.util;

import org.junit.Assert;
import org.junit.Test;

public class RunCommandTest {
	
	@Test
	public void testRunSimple() throws Exception {
		String command = "pwd";
		String output = RunCommand.runCommand(command);
		String userHome = System.getProperty("user.home");
		Assert.assertEquals("pwd"+output, userHome+"/workspace/norma", output.trim());
	}

}
