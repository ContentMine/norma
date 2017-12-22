package org.xmlcml.norma.input.tei;

import org.xmlcml.norma.util.TransformerWrapper;

import javax.xml.transform.Transformer;
import java.io.*;
import java.util.Scanner;

/**
 * Created by T Arrow on 18/12/17.
 */
public class TEI2TXTConverter {

    private InputStream stylesheet;

    public TEI2TXTConverter() {
        stylesheet = getClass().getResourceAsStream("tei-to-text.xsl");
    }

    public String convertTEI2TXTFile(File TEIFile) throws Exception {
        String inputString = new Scanner(new FileInputStream(TEIFile), "UTF-8").useDelimiter("\\A").next();
        String outputString = convertTEI2TXT(inputString);
        return outputString;
    }

    public String convertTEI2TXT(String XmlInput) throws Exception {
        TransformerWrapper transformerWrapper = new TransformerWrapper();
        Transformer transformer = transformerWrapper.createTransformer(stylesheet);
        InputStream inputStream = new ByteArrayInputStream(XmlInput.getBytes());
        String outputString = transformerWrapper.transformToXML(inputStream);
        return String.valueOf(outputString);
    }
}
