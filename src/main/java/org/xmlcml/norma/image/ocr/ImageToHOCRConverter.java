package org.xmlcml.norma.image.ocr;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.misc.CMineUtil;
import org.xmlcml.norma.input.tex.TEX2HTMLConverter;

public class ImageToHOCRConverter {

	private static final int NTRIES = 20;

	private final static Logger LOG = Logger.getLogger(ImageToHOCRConverter.class);
	static {LOG.setLevel(Level.DEBUG);}

	private static final String HOCR = "hocr";
	private static final String USR_LOCAL_BIN_TESSERACT = "/usr/local/bin/tesseract";
	
	private int tryCount;
	
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
     * @param input
     * @return
     * @throws IOException // if Tesseract not present
     * @throws InterruptedException
     */
    public boolean convertImageToHOCR(File input, File output) throws IOException, InterruptedException {
    	
        // tesseract performs the initial Image => HOCR conversion,
    	
        output.getParentFile().mkdirs();
        ProcessBuilder tesseractBuilder = new ProcessBuilder(
        		USR_LOCAL_BIN_TESSERACT, input.getAbsolutePath(), output.getAbsolutePath(), HOCR);
        tesseractBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
    	Process tesseractProc = null;
        try {
        	tesseractProc = tesseractBuilder.start();
        } catch (IOException e) {
        	CMineUtil.catchUninstalledProgram(e, USR_LOCAL_BIN_TESSERACT);
        	return false;
        }
        tesseractProc.getOutputStream().close();
        int exitValue = -1;
		for (int itry = 0; itry < tryCount; itry++) {
			Thread.sleep(100);
		    try {
		    	exitValue = tesseractProc.exitValue();
		    	if (exitValue == 0) {
		    		LOG.trace("terminated");
		    		break;
		    	}
			} catch (IllegalThreadStateException e) {
				LOG.trace("not terminated: "+itry);
			}
		}
		if (exitValue != 0) {
			tesseractProc.destroy();
			LOG.error("Process failed to terminate after :"+tryCount);
			return false;
		}
		return true;
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
