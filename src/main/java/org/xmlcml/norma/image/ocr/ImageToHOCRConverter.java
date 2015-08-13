package org.xmlcml.norma.image.ocr;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.misc.CMineUtil;

public class ImageToHOCRConverter {

	private static final int TESSERACT_TIMEOUT_STEP = 100;

	private static final int NTRIES = 20;

	private final static Logger LOG = Logger.getLogger(ImageToHOCRConverter.class);
	static {LOG.setLevel(Level.DEBUG);}

	private static final String HOCR = "hocr";
	private static final String USR_LOCAL_BIN_TESSERACT = "/usr/local/bin/tesseract";
//	private static final String ENCODING = "-Dfile.encoding=UTF8";
	private static final String ENCODING = "";
	
	private int tryCount;
	private File outputHtmlFile;
	
	public ImageToHOCRConverter() {
		setDefaults();
	}
	
    private void setDefaults() {
    	tryCount = NTRIES;
	}

	public int getTryCount() {
		return tryCount;
	}

	public void setTryCount(int tryCount) {
		this.tryCount = tryCount;
	}

	/** converts Image to HOCR.
     * relies on Tesseract.
     * 
     * Note - creates a *.hocr.html file from output root.
     * 
     * @param inputImageFile
     * @return HOCR.HTML file created (null if failed to create)
     * @throws IOException // if Tesseract not present
     * @throws InterruptedException ??
     */
    public File convertImageToHOCR(File inputImageFile, File output) throws IOException, InterruptedException {

    	outputHtmlFile = null;
        // tesseract performs the initial Image => HOCR conversion,
    	
        output.getParentFile().mkdirs();
        ProcessBuilder tesseractBuilder = new ProcessBuilder(
        		USR_LOCAL_BIN_TESSERACT, inputImageFile.getAbsolutePath(), output.getAbsolutePath(), HOCR, ENCODING );
        tesseractBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
    	Process tesseractProc = null;
        try {
        	tesseractProc = tesseractBuilder.start();
        } catch (IOException e) {
        	CMineUtil.catchUninstalledProgram(e, USR_LOCAL_BIN_TESSERACT);
        	return null;
        }
        tesseractProc.getOutputStream().close();
        int exitValue = -1;
        int itry = 0;
        for (; itry < tryCount; itry++) {
			Thread.sleep(TESSERACT_TIMEOUT_STEP);
		    try {
		    	exitValue = tesseractProc.exitValue();
		    	if (exitValue == 0) {
		    		LOG.trace("tesseract terminated OK");
		    		break;
		    	}
			} catch (IllegalThreadStateException e) {
				LOG.trace("still not terminated after: "+itry+"; keep going");
			}
		}
		LOG.trace("tries: "+itry);

		if (exitValue != 0) {
			tesseractProc.destroy();
			LOG.error("Process failed to terminate after :"+tryCount);
		}
    	outputHtmlFile = createOutputHtmlFileDescriptorForHOCR_HTML(output);
    	LOG.trace("creating output "+outputHtmlFile);
		if (!outputHtmlFile.exists()) {
			File outputHocr = createOutputHtmlFileDescriptorForHOCR_HOCR(output);
			if (!outputHocr.exists()) {	
				LOG.debug("failed to create: "+outputHtmlFile+" or "+outputHocr);
				outputHtmlFile = null;
			} else {
				LOG.debug("copying "+outputHocr+" to "+outputHtmlFile);
				FileUtils.copyFile(outputHocr, outputHtmlFile);
			}
		} else {
			LOG.debug("created "+outputHtmlFile.getAbsolutePath()+"; size: "+ FileUtils.sizeOf(outputHtmlFile));
		}
		return outputHtmlFile;

    }

	private File createOutputHtmlFileDescriptorForHOCR_HTML(File output) {
		String filename = output.getAbsolutePath()+".html";
		LOG.debug("creating HTML output: "+filename);
		return new File(filename);
	}

	private File createOutputHtmlFileDescriptorForHOCR_HOCR(File output) {
		String filename = output.getAbsolutePath()+".hocr";
		LOG.debug("creating hocr.hocr name: "+filename);
		return new File(filename);
	}

    
//    public class ProcMon implements Runnable {
//
//    	  private final Process _proc;
//    	  private volatile boolean _complete;
//
//    	  public boolean isComplete() { return _complete; }
//
//    	  public void run() {
//    	    _proc.waitFor();
//    	    _complete = true;
//    	  }
//
//    	  public static ProcMon create(Process proc) {
//    	    ProcMon procMon = new ProcMon(proc);
//    	    Thread t = new Thread(procMon);
//    	    t.start();
//    	    return procMon;
//    	  }
//    	}
}
