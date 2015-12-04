package org.xmlcml.norma.biblio;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlDiv;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class RISEntry {

	public static final Logger LOG = Logger.getLogger(RISEntry.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String AB = "AB";
	private static final String ER = "ER";
	public static final String TY = "TY";
	
	public final static String START_DASH_SPACE = "^[A-Z][A-Z][A-Z ][A-Z ]\\- .*"; // PMID breaks the rules, this covers it
	public final static String DASH_SPACE = "- "; // PMID breaks the rules, this covers it
	public static final String PMID = "PMID";
	private String type;
	private StringBuilder currentValue;
	private Multimap<String, StringBuilder> valuesByField = ArrayListMultimap.create();
	private boolean canAdd;
	private List<String> fieldList;
	String abstractx;
	
	public RISEntry() {
		canAdd = true;
		fieldList = new ArrayList<String>();
	}
	
	public String getType() {
		return type;
	}

	public String addLine(String line) {
		if (!canAdd) {
			System.err.println("Cannot add line: "+line);
		}
		String field = null;
		if (line.matches(START_DASH_SPACE)) {
			String[] ss = line.split(DASH_SPACE);
			field = ss[0].trim();
			recordUnknownFields(field);
			if (!fieldList.contains(field)) {
				fieldList.add(field);
			}
			if (ss.length == 1) {
				currentValue = null;
				if (field.equals(ER)) {
					canAdd = false;
				}
			} else {
				currentValue = new StringBuilder(ss[1].trim());
				valuesByField.put(field, currentValue);
			}
		} else {
			String v = line.trim();
			if (canAdd) {
				if (currentValue != null) {
					currentValue.append(" "+v);
				} else {
					System.err.println("Cannot add "+line);
				}
			} else {
				System.out.println("Cannot add: "+line);
			}
		}
		return field;
	}

	private void recordUnknownFields(String field) {
		if (!RISParser.FIELD_MAP.containsKey(field)) {
			if (!RISParser.UNKNOWN_KEYS.contains(field)) {
				RISParser.addUnknownKey(field);
				LOG.trace("Unknown Key: "+field);
			}
		}
	}
	
	public HtmlDiv createAbstractHtml() {
		List<StringBuilder> abstracts = new ArrayList<StringBuilder>(valuesByField.get(AB));
		HtmlDiv abstractDiv = null;
		if (abstracts.size() == 1) {
			abstractx = abstracts.get(0).toString();
			BiblioAbstractAnalyzer abstractAnalyzer = new BiblioAbstractAnalyzer();
			abstractDiv = abstractAnalyzer.createAndAnalyzeSections(abstractx);
		}
		
		return abstractDiv;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String key : fieldList) {
			sb.append(key+": ");
			List<StringBuilder> values = new ArrayList<StringBuilder>(valuesByField.get(key));
			if (values.size() == 1) {
				sb.append(values.get(0).toString()+"\n");
			} else {
				sb.append("\n");
				for (StringBuilder sb0 : values) {
					sb.append("    "+sb0.toString()+"\n");
				}
			}
		}
		return sb.toString();
	}

	public String getAbstractString() {
		if (abstractx == null) {
			createAbstractHtml();
		}
		return abstractx;
	}

}
