package org.xmlcml.norma.image.ocr;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.tuple.ImmutablePair;

public class NamedImage {
	private String key;
	private BufferedImage image;
	
	
	public NamedImage(ImmutablePair<String, BufferedImage> immutablePair) {
		image  = immutablePair.getRight();
		key = immutablePair.getLeft();
	}

	public BufferedImage getImage() {
		return image;
	}

	public String getKey() {
		return key;
	}
	
	

}
