package org.xmlcml.norma.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.xmlcml.cmine.util.CMineUtil;

public class RunCommand {

    /** converts TeX to HTML.
     * relies on LatexML.
     * 
     * @param input
     * @return
     * @throws IOException // if LatexML not present
     * @throws InterruptedException
     */
    public static String runCommand(String command) throws IOException, InterruptedException {
        ProcessBuilder commandRunner = new ProcessBuilder(command);
        commandRunner.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = null;
        try {
        	process = commandRunner.start();
        } catch (IOException e) {
        	CMineUtil.catchUninstalledProgram(e, command);
        }
        return IOUtils.toString(process.getInputStream());
    }

}
