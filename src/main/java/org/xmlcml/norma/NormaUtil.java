package org.xmlcml.norma;

public class NormaUtil {

	public static boolean isPDF(String name) {
		return name.toLowerCase().endsWith(InputFormat.PDF.toString().toLowerCase());
	}
}
