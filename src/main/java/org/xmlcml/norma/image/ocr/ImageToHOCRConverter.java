package org.xmlcml.norma.image.ocr;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ImageToHOCRConverter {

	private final static Logger LOG = Logger.getLogger(ImageToHOCRConverter.class);
	static {LOG.setLevel(Level.DEBUG);}

	private static final String HOCR = "hocr";
	private static final String USR_LOCAL_BIN_TESSERACT = "/usr/local/bin/tesseract";
	
    /** converts Image to HOCR.
     * relies on Tesseract.
     * 
     * @param input
     * @return
     * @throws IOException // if Tesseract not present
     * @throws InterruptedException
     */
    public void convertImageToHOCR(File input, File output) throws IOException, InterruptedException {
        // tesseract performs the initial Image => HOCR conversion,
        output.getParentFile().mkdirs();
        ProcessBuilder tesseractBuilder = new ProcessBuilder(
        		USR_LOCAL_BIN_TESSERACT, input.getAbsolutePath(), output.getAbsolutePath(), HOCR);
        tesseractBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process tesseractProc = tesseractBuilder.start();
        tesseractProc.getOutputStream().close();
    }

}
