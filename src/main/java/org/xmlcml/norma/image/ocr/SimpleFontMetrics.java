package org.xmlcml.norma.image.ocr;

import java.util.HashSet;
import java.util.Set;

/** a crude list to make layou better after hOCR.
 * 
 * @author pm286
 *
 */
public class SimpleFontMetrics {

	private final static String[] ASCENDERS = {
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
		"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
		"b", "d", "f", "h", "k", "l", "t",
	};
	private static Set<String> ascenderSet = new HashSet<String>();
	private final static String[] DESCENDERS = {
		"g", "j", "p", "q", "y", "(", ")", "[", "]"
	};
	private static Set<String> descenderSet = new HashSet<String>();
	private final static String[] MEDIAN = {
		"a", "c", "e", "m", "n", "o", "r", "s", "u", "v", "w", "x", "z"
	};
	private static Set<String> medianSet = new HashSet<String>();
	static {
		for (String s : ASCENDERS) {
			ascenderSet.add(s);
		}
		for (String s : DESCENDERS) {
			descenderSet.add(s);
		}
		for (String s : MEDIAN) {
			medianSet.add(s);
		}
	}
	public final static double DEFAULT_ASCENDER_FRACTION = 0.3;
	public final static double DEFAULT_DESCENDER_FRACTION = 0.3;
	private String ss;
	private Boolean hasAscenders;
	private Boolean hasDescenders;
	private double ascenderFraction = DEFAULT_ASCENDER_FRACTION;
	private double descenderFraction = DEFAULT_ASCENDER_FRACTION;
	
	public SimpleFontMetrics(String ss) {
		readString(ss);
	}
	
	public void readString(String s) {
		ss = s;
		hasAscenders = null;
		hasDescenders = null;
	}
	
	public boolean hasAscenders() {
		if (hasAscenders == null) {
			hasAscenders = hasCharacters(ss, ascenderSet);
		}
		return hasAscenders;
	}

	public boolean hasDescenders() {
		if (hasDescenders == null) {
			hasDescenders = hasCharacters(ss, descenderSet);
		}
		return hasDescenders;
	}
	
	public double getAscenderFraction() {
		return (hasAscenders()) ? ascenderFraction : 0.0;
	}

	public double getDescenderFraction() {
		return (hasDescenders()) ? descenderFraction : 0.0;
	}
	
	public double getFractionalExtent() {
		return 1.0 + getAscenderFraction() + getDescenderFraction();
	}

	public double getFractionalYOffset() {
		return getDescenderFraction() / getFractionalExtent();
	}

	private boolean hasCharacters(String s, Set<String> characterSet) {
		for (int i = 0; i < s.length(); i++) {
			String c = String.valueOf(s.charAt(i));
			if (characterSet.contains(c)) return true;
		}
		return false;
	}
}
