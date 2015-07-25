package org.xmlcml.norma.txt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.IntArray;

public class AnnotatedLine {

	private static final Logger LOG = Logger.getLogger(AnnotatedLine.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static Map<String, Pattern> PATTERN_MAP;
	public final static Pattern Li_PATTERN      = Pattern.compile("\\s*([ivxlc]+)\\s+.*");
	public final static Pattern L1_PATTERN      = Pattern.compile("\\s*(\\d+)\\s+.*");
	public final static Pattern L12_PATTERN     = Pattern.compile("\\s*(\\d+\\.\\d+)\\s+.*");
	public final static Pattern L123_PATTERN    = Pattern.compile("\\s*(\\d+\\.\\d+\\.\\d+)\\s+.*");
	public final static Pattern L1234_PATTERN   = Pattern.compile("\\s*(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s+.*");
	public final static Pattern Ri_PATTERN      = Pattern.compile(".*\\s+([ivxlc]+)\\s*");
	public final static Pattern R1_PATTERN      = Pattern.compile(".*\\s+(\\d+)\\s*");
	public final static Pattern R12_PATTERN     = Pattern.compile(".*\\s+(\\d+\\.\\d+)\\s*");
	public final static Pattern R123_PATTERN    = Pattern.compile(".*\\s+(\\d+\\.\\d+\\.\\d+)\\s*");
	public final static Pattern R1234_PATTERN   = Pattern.compile(".*\\s+(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s*");
	public final static Pattern CHAPTER_PATTERN = Pattern.compile("\\s*[Cc][Hh][Aa][Pp][Tt][Ee][Rr]\\s+(\\d+)\\s*");

	public static final String R1234_KEY        = "R1234";
	public static final String R123_KEY         = "R123";
	public static final String R12_KEY          = "R12";
	public static final String R1_KEY           = "R1";
	public static final String RI_KEY           = "Ri";
	public static final String L1234_KEY        = "L1234";
	public static final String L123_KEY         = "L123";
	public static final String L12_KEY          = "L12";
	public static final String L1_KEY           = "L1";
	public static final String LI_KEY           = "Li";
	public static final String CHAPTER_KEY      = "CHAPTER";

	public final static List<String> PATTERN_NAMES;
	static {
		PATTERN_MAP = new HashMap<String, Pattern>();
		PATTERN_MAP.put(LI_KEY,Li_PATTERN);
		PATTERN_MAP.put(L1_KEY,L1_PATTERN);
		PATTERN_MAP.put(L12_KEY,L12_PATTERN);
		PATTERN_MAP.put(L123_KEY,L123_PATTERN);
		PATTERN_MAP.put(L1234_KEY,L1234_PATTERN);
		PATTERN_MAP.put(RI_KEY,Ri_PATTERN);
		PATTERN_MAP.put(R1_KEY,R1_PATTERN);
		PATTERN_MAP.put(R12_KEY,R12_PATTERN);
		PATTERN_MAP.put(R123_KEY,R123_PATTERN);
		PATTERN_MAP.put(R1234_KEY,R1234_PATTERN);
		PATTERN_MAP.put(CHAPTER_KEY,CHAPTER_PATTERN);
		
		PATTERN_NAMES = new ArrayList<String>();
		PATTERN_NAMES.add(LI_KEY);
		PATTERN_NAMES.add(L1_KEY);
		PATTERN_NAMES.add(L12_KEY);
		PATTERN_NAMES.add(L123_KEY);
		PATTERN_NAMES.add(L1234_KEY);
		PATTERN_NAMES.add(RI_KEY);
		PATTERN_NAMES.add(R1_KEY);
		PATTERN_NAMES.add(R12_KEY);
		PATTERN_NAMES.add(R123_KEY);
		PATTERN_NAMES.add(R1234_KEY);
		PATTERN_NAMES.add(CHAPTER_KEY);
	}

	private Map<String, String> matchByType = new HashMap<String, String>();
	private String line;
	private int lineNumber;
	
	public AnnotatedLine() {
		
	}

	public AnnotatedLine(int lineNumber, String line) {
		this.line = line;
		this.lineNumber = lineNumber;
		createMatches(line);
	}

	private void createMatches(String line) {
		matchByType = new HashMap<String, String>();
		for (String name : PATTERN_NAMES) {
			Pattern pattern = PATTERN_MAP.get(name);
			Matcher matcher = pattern.matcher(line);
			if (matcher.matches()) {
				matchByType.put(name, matcher.group(1));
			}
		}
	}

	public int size() {
		return matchByType.size();
	}

	public String toString() {
		return lineNumber+": "+matchByType.toString()+"; "+line;
	}

	public boolean containsKey(String key) {
		return matchByType.containsKey(key);
	}

	public Integer getChapterNumber() {
		String value = matchByType.get(CHAPTER_KEY);
		return value == null ? null : new Integer(value);
	}

	public IntArray getLeftSection() {
		IntArray array = null;
		String value = null;
		for (String key : matchByType.keySet()) {
			if (
//					key.equals(L1_KEY) ||
					key.equals(L12_KEY) ||
					key.equals(L123_KEY) ||
					key.equals(L1234_KEY)) {
				// ambiguous
				if (value != null) {
					array = null;
					break;
				}
				value = matchByType.get(key);
				LOG.trace("> "+key+": "+value);
				String[] values = value.split("\\.");
				array = new IntArray(values);
			}
		}
		return array;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	boolean containsLeftDecimalKeys() {
		return containsKey(AnnotatedLine.L1_KEY) ||
				containsKey(AnnotatedLine.L12_KEY) ||
				containsKey(AnnotatedLine.L123_KEY) ||
				containsKey(AnnotatedLine.L1234_KEY);
	}
	
	static boolean checkArrayIncrement(int lineNumber, IntArray lastSection, IntArray section) {
		if (lastSection == null) {
			return true;
		}
		LOG.trace(">>"+lastSection+"; "+section);
		int lastSize = lastSection == null ? 0 : lastSection.size();
		int thisSize = section.size();
		if (lastSize == thisSize) {
			if (!checkEquals(lastSection, section, lastSize - 1)) {
				return badIncrement(lineNumber, lastSection, section);
			}
			if (lastSection.elementAt(lastSize - 1) != section.elementAt(thisSize - 1) - 1) {
				return badIncrement(lineNumber, lastSection, section);
			}
		} else if (lastSize >= thisSize + 1) {
			// 1.2.3 => 1.3
			if (!checkEquals(lastSection, section, thisSize - 1)) {
				return badIncrement(lineNumber, lastSection, section);
			}
			if (lastSection.elementAt(thisSize - 1) != section.elementAt(thisSize - 1) - 1) {
				LOG.debug("xxx "+lastSize+"; "+thisSize);
				return badIncrement(lineNumber, lastSection, section);
			}
		} else if (lastSize == thisSize - 1) {
			// 1.2.3 => 1.2.3.1
			if (!checkEquals(lastSection, section, lastSize)) {
				return badIncrement(lineNumber, lastSection, section);
			}
			if (section.elementAt(thisSize - 1) != 1) {
				return badIncrement(lineNumber, lastSection, section);
			}
		} else if (lastSize >= thisSize - 1) {
			return badIncrement(lineNumber, lastSection, section);
		} else {
			return badIncrement(lineNumber, lastSection, section);
		}
		return true;
	}

	static boolean badIncrement(int lineNumber, IntArray lastSection,
			IntArray section) {
		LOG.warn("bad section increment ["+lineNumber+"] "+lastSection+" -> "+section);
		return false;
	}

	static boolean checkEquals(IntArray lastSection, IntArray section, int toCheck) {
		for (int i = 0; i < toCheck; i++) {
			if (lastSection.elementAt(i) != section.elementAt(i)) {
				LOG.debug("bad section increment "+lastSection+" -> "+section);
				return false;
			}
		}
		return true;
	}

}
