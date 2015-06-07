package org.xmlcml.norma.input.tex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;

/**
 * Converts TeX input to HTML 5 using LaTeXML
 */
public class TEX2HTMLConverter {
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
        ProcessBuilder latexMLBuilder = new ProcessBuilder("latexml", "-", "--quiet");
        latexMLBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process latexMLProc = latexMLBuilder.start();
        IOUtils.copy(inputStream, latexMLProc.getOutputStream());
        latexMLProc.getOutputStream().close();

        LOG.debug("Processing input with LaTeXML");

        // latexmlpost transforms the resulting XML to HTML 5
        ProcessBuilder latexMLPostBuilder = new ProcessBuilder("latexmlpost", "-", "--format=html5");
        latexMLPostBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process latexMLPostProc = latexMLPostBuilder.start();
        IOUtils.copy(latexMLProc.getInputStream(), latexMLPostProc.getOutputStream());
        latexMLPostProc.getOutputStream().close();

        String html = IOUtils.toString(latexMLPostProc.getInputStream(), StandardCharsets.UTF_8);
        return convertHTMLToXHTML(html);
    }
}
