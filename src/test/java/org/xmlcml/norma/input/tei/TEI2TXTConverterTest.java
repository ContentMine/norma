package org.xmlcml.norma.input.tei;

import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by tom on 18/12/17.
 */
public class TEI2TXTConverterTest {
    private static String newline = System.getProperty("line.separator");

    @Test
    public void convertTEI2TXT() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("minimal.xml");
        String inputString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
        // System.out.print(inputString);
        TEI2TXTConverter converter = new TEI2TXTConverter();
        String outputString = converter.convertTEI2TXT(inputString);
        Assert.assertEquals("Test Test"+newline, outputString);
        System.out.print(outputString);
    }

}