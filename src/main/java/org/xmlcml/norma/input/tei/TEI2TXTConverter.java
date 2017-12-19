package org.xmlcml.norma.input.tei;

import org.xmlcml.norma.util.TransformerWrapper;

import javax.xml.transform.Transformer;
import java.io.*;

/**
 * Created by T Arrow on 18/12/17.
 */
public class TEI2TXTConverter {

    private InputStream stylesheet;

    public TEI2TXTConverter() {
        stylesheet = getClass().getResourceAsStream("tei-to-text.xsl");
    }

    public void convertTEI2TXTFile(File TEIFile) {

    }

    public String convertTEI2TXT(String XmlInput) throws Exception {
        TransformerWrapper transformerWrapper = new TransformerWrapper();
        Transformer transformer = transformerWrapper.createTransformer(stylesheet);
        InputStream inputStream = new ByteArrayInputStream(XmlInput.getBytes());
        String outputString = transformerWrapper.transformToXML(inputStream);
        return String.valueOf(outputString);
    }
}
