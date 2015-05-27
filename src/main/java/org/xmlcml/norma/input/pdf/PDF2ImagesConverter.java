package org.xmlcml.norma.input.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PDF2ImagesConverter {

	
	private static final Logger LOG = Logger
			.getLogger(PDF2ImagesConverter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public PDF2ImagesConverter() {
	}
	
	public List<BufferedImage> readPDF(InputStream filesInputStream, boolean sortByPosition) throws IOException {
		PDDocument doc=PDDocument.load(filesInputStream);
		List<BufferedImage> imageList = new ArrayList<BufferedImage>();
	    List pages = doc.getDocumentCatalog().getAllPages();
	    Iterator iter = pages.iterator(); 
	    int i =1;

	    while (iter.hasNext()) {
	        PDPage page = (PDPage) iter.next();
	        PDResources resources = page.getResources();
	        Map pageImages = resources.getImages();
	        if (pageImages != null) { 
	            Iterator imageIter = pageImages.keySet().iterator();
	            while (imageIter.hasNext()) {
	                String key = (String) imageIter.next();
	                PDXObjectImage image = (PDXObjectImage) pageImages.get(key);
	                imageList.add(image.getRGBImage());
	                i ++;
	            }
	        }
	    }
		doc.close();
		return imageList;
	}
		 
}
