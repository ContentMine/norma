package org.xmlcml.norma.input.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
	
	public List<Pair<String, BufferedImage>> readPDF(InputStream filesInputStream, boolean sortByPosition) throws IOException {
		PDDocument doc=PDDocument.load(filesInputStream);
		List<Pair<String, BufferedImage>> pageSerialAndImageList = new ArrayList<Pair<String, BufferedImage>>();
	    List<PDPage> pages =  (List<PDPage>) doc.getDocumentCatalog().getAllPages();
	    int serial = 1;
	    for (int pageNumber = 0; pageNumber< pages.size(); pageNumber++) {
	        PDPage page = (PDPage) pages.get(pageNumber);
	        PDResources resources = page.getResources();
//	        Map<String, PDXObject> pageObjects = resources.getXObjects(); // we should use this
	        Map<String, PDXObjectImage> pageImages = resources.getImages();
	        if (pageImages != null) { 
	            Iterator<String> imageIter = pageImages.keySet().iterator();
	            while (imageIter.hasNext()) {
	                String key = (String) imageIter.next();
	                LOG.debug("key "+key+"; "+(pageNumber+1));
//	                PDXObject object = (PDXObject) pageImages.get(key); // and this, when we know how to extract images
	                PDXObjectImage image = (PDXObjectImage) pageImages.get(key);
	                String id = "image."+pageNumber+"."+serial+"."+key;
	                pageSerialAndImageList.add(new ImmutablePair<String, BufferedImage>(id, image.getRGBImage()));
	                serial++;
	            }
	        }
	    }
		doc.close();
		return pageSerialAndImageList;
	}
		 
}
