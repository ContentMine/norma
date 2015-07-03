package org.xmlcml.norma.input.pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.xmlcml.norma.image.ocr.NamedImage;

public class PDF2ImagesConverter {

	
	private static final Logger LOG = Logger
			.getLogger(PDF2ImagesConverter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public PDF2ImagesConverter() {
	}

	@Deprecated // sort does nothing
	public List<NamedImage> readPDF(InputStream filesInputStream, boolean sortByPosition) throws IOException {
		return readPDF(filesInputStream);
	}
	
	public List<NamedImage> readPDF(InputStream filesInputStream) throws IOException {
		PDDocument doc=PDDocument.load(filesInputStream);
		List<NamedImage> namedImageList = new ArrayList<NamedImage>();
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
	                PDXObjectImage objectImage = (PDXObjectImage) pageImages.get(key);
	                String id = "image."+pageNumber+"."+serial+"."+key;
	                // There seems to be a problem with alpha channel...
	                BufferedImage bufferedImage = objectImage.getRGBImage();
	                int width = bufferedImage.getWidth();
	                int height = bufferedImage.getHeight();
	                BufferedImage bufferedImage1 = new BufferedImage(
	                		width, height, BufferedImage.TYPE_INT_RGB);
	                for (int i = 0; i < width; i++) {
		                for (int j = 0; j < height; j++) {
		                	bufferedImage1.setRGB(i,  j,  bufferedImage.getRGB(i, j));
		                }
	                }
	                NamedImage namedImage = new NamedImage(new ImmutablePair<String, BufferedImage>(id, bufferedImage1));
	                namedImageList.add(namedImage);
	                serial++;
	            }
	        }
	    }
		doc.close();
		return namedImageList;
	}
		 
}
