package org.xmlcml.norma.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFSplitter {
	private static final Logger LOG = Logger.getLogger(PDFSplitter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private PDDocument document;
	private int noOfPages;
	private File file;
	private String fileRoot;

	public PDFSplitter() {
	}
	
	public void loadFile(File file) throws IOException {
	      document = PDDocument.load(file);
	      this.file = file;
	      fileRoot = file.getName();
	}
	
	public void splitDocs(int range) throws IOException {
		noOfPages = document.getNumberOfPages();
		for (int i = 0; i <= noOfPages; i+= range) {
			int start = i + 1;
			int end = Math.min(i + range, noOfPages);
			splitDoc(start, end);
		    File outfile = new File(file.getParentFile(), fileRoot + "_" + start + "_" + end + ".pdf");
		    LOG.debug(outfile);
		    saveFile(outfile);
			// crude but this seems fast (I can't find a transfer/move function)
		    document = PDDocument.load(file);
			noOfPages = document.getNumberOfPages();
			LOG.debug("reread "+noOfPages);
		}
	}

    public void splitDoc(int start, int end) throws IOException {
		
		noOfPages = document.getNumberOfPages();
    	int start1 = end + 1;
    	int end1 = Math.min(end, noOfPages);
    	LOG.debug("removing1 "+start1+" to "+ end);
		for (int i = start1; i < end; i++) {
			document.removePage(start1);
		}
		noOfPages = document.getNumberOfPages();
		LOG.debug("pages1 "+noOfPages);
		int end0 = Math.min(start - 1, noOfPages);
		for (int i = 0; i < end0; i++) {
			document.removePage(0);
		}
		noOfPages = document.getNumberOfPages();
		LOG.debug("pages0 "+noOfPages);
	}

    public void saveFile(File outfile) throws IOException {
		try {
			document.save(outfile);
		} catch (COSVisitorException e) {
			throw new RuntimeException("cannot save file", e);
		}
		document.close();
    }
}
