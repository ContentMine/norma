package org.xmlcml.norma.txt;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class SentenceSplitter {

	;
	private static final Logger LOG = Logger.getLogger(SentenceSplitter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static Pattern SPLIT = Pattern.compile("[\\.\\?\\!][\\s+|\\n|\\r][A-Z]", Pattern.MULTILINE);

	private String rawText;
	private List<String> sentenceList;
	
	public SentenceSplitter() {
	}

	public void read(String rawText) {
		this.rawText = rawText;
	}

	public void split() {
		if (rawText != null) {
			
			ensureSentenceList();
			StringBuilder sb = new StringBuilder(rawText);
			LOG.trace(sb.toString());
			Matcher matcher = SPLIT.matcher(rawText);
			int start = 0;
			List<Integer> splitPositions = new ArrayList<Integer>();
			while (true) {
				if (!matcher.find(start)) {
					break;
				};
				int split = matcher.start() + 1;
				start = split;
				splitPositions.add((Integer)split);
			}
		
			int last = 0;
			for (Integer ii : splitPositions) {
				String s = sb.substring(last, ii);
				sentenceList.add(s);
				last = ii;
			}
			String s = sb.substring(last);
			sentenceList.add(s);
			splitPeriodEndingLine();
		}
	}

	private void splitPeriodEndingLine() {
//		List<String> newSentenceList = new ArrayList<String>();
//		for (String s : sentenceList) {
//			if (s.endsWith("\\.")) {
//				
//			}
//		}
	}

	public List<String> getSentenceList() {
		ensureSentenceList();
		return sentenceList;
	}

	private void ensureSentenceList() {
		if (sentenceList == null) {
			sentenceList = new ArrayList<String>();
		}
	}
}
