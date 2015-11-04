package org.xmlcml.norma.input.tex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;
import org.xmlcml.cmine.misc.CMineUtil;

/**
 * Converts TeX input to HTML 5 using LaTeXML
 */
public class TEX2HTMLConverter {
	
    public static final String LATEXMLPOST = "latexmlpost";
	public static final String LATEXML = "latexml";
	private static final Logger LOG = Logger.getLogger(TEX2HTMLConverter.class);

    static {
        LOG.setLevel(Level.DEBUG);
    }

    private String convertHTMLToXHTML(String html) {
        Document htmlDocument = Parser.parse(html, "");
        Document.OutputSettings outputSettings = htmlDocument.outputSettings().clone();
        outputSettings.escapeMode(Entities.EscapeMode.xhtml);
        outputSettings.syntax(Document.OutputSettings.Syntax.xml);
        htmlDocument.outputSettings(outputSettings);
        return htmlDocument.html();
    }

    /** converts TeX to HTML.
     * relies on LatexML.
     * 
     * @param input
     * @return
     * @throws IOException // if LatexML not present
     * @throws InterruptedException
     */
    public String convertTeXToHTML(File input) throws IOException, InterruptedException {
        FileInputStream inputStream = new FileInputStream(input);

        // latexml performs the initial TeX => XML conversion,
        ProcessBuilder latexMLBuilder = new ProcessBuilder(LATEXML, "-", "--quiet");
        latexMLBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process latexMLProc = null;
        try {
        	latexMLProc = latexMLBuilder.start();
        } catch (IOException e) {
        	CMineUtil.catchUninstalledProgram(e, TEX2HTMLConverter.LATEXML);
        	return null;
        }

        IOUtils.copy(inputStream, latexMLProc.getOutputStream());
        latexMLProc.getOutputStream().close();

        LOG.debug("Processing input with LaTeXML");

        // latexmlpost transforms the resulting XML to HTML 5
        ProcessBuilder latexMLPostBuilder = new ProcessBuilder(LATEXMLPOST, "-", "--format=html5");
        latexMLPostBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process latexMLPostProc = latexMLPostBuilder.start();
        IOUtils.copy(latexMLProc.getInputStream(), latexMLPostProc.getOutputStream());
        latexMLPostProc.getOutputStream().close();

        String html = IOUtils.toString(latexMLPostProc.getInputStream(), StandardCharsets.UTF_8);
        return convertHTMLToXHTML(html);
    }
}
