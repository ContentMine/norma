package org.xmlcml.norma.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.xmlcml.cmine.misc.CMineUtil;
import org.xmlcml.norma.input.tex.TEX2HTMLConverter;

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
