package org.xmlcml.norma.nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opennlp.tools.util.Span;

public class NameFinderWrapper {

	public static final String DATE = "date";
	public static final String LOCATION = "location";
	public static final String PERSON = "person";
	public static final String RESOURCE_LOCATION = "/org/xmlcml/norma/util";
	
	public final static Map<String, String> resourceStringByType = new HashMap<String, String>();
	static {
		resourceStringByType.put(DATE, OpenNLPWrapper.EN_DATE_BIN);
		resourceStringByType.put(LOCATION, OpenNLPWrapper.EN_LOCATION_BIN);
		resourceStringByType.put(PERSON, OpenNLPWrapper.EN_PERSON_BIN);
	}

	private String type;
	String resourceString;
	List<Span> spans;
	private OpenNLPWrapper openNLPWrapper;

	public NameFinderWrapper() {
		
	}

	/**
	 * do we add more than one type?
	 * 
	 * @param type
	 */
	public void setType(String type) {
		if (type == null) {
			throw new RuntimeException("null type");
		}
		this.type = type;
		String resourceString0 = resourceStringByType.get(type);
		if (resourceString0 == null) {
			throw new RuntimeException("no resource for: "+type);
		}
		resourceString = RESOURCE_LOCATION+"/"+resourceString0;
		
	}
	
	public String getType() {
		return type;
	}

	public List<List<String>> searchText(String text) {
		ensureOpenNLPWrapper();
		openNLPWrapper.clearText();
		List<String> sentences = openNLPWrapper.getOrCreateSentences(text);
		List<List<String>> nameList = new ArrayList<List<String>>();
		for (int sentenceIndex = 0; sentenceIndex < sentences.size(); sentenceIndex++) {
			List<String> name = searchSentenceForNames(text, sentenceIndex);
			nameList.add(name);
		}
		return nameList;
	}
	
	public List<String> searchSentenceForNames(String text, int sentenceIndex) {
		findSpansInText(text, sentenceIndex);
		String[] ss = Span.spansToStrings(spans.toArray(new Span[0]), openNLPWrapper.getSentenceTokens().toArray(new String[0]));
		return Arrays.asList(ss);
	}

	public void findSpansInText(String text) {
	}
	
	public List<Span> findSpansInText(String text, int sentenceIndex) {
		ensureOpenNLPWrapper();
		openNLPWrapper.tokenizeSentenceInText(text, sentenceIndex);
		spans = openNLPWrapper.findSpansInSentence(this);
		return spans;
	}

	private void ensureOpenNLPWrapper() {
		if (openNLPWrapper == null) {
			openNLPWrapper = new OpenNLPWrapper();
		}
	}
	

	public List<Span> getSpans() {
		return spans;
	}

	public List<List<String>> getNames() {
		List<List<String>> compoundNameList = new ArrayList<List<String>>();
		for (Span span : spans) {
			List<String> compoundName = new ArrayList<String>();
			for (int i = span.getStart(); i < span.getEnd(); i++) {
				compoundName.add(openNLPWrapper.getSentenceTokens().get(i));
			}
			compoundNameList.add(compoundName);
		}
		return compoundNameList;
	}

	public List<List<String>> findCompoundNames() {
		List<Span> spanList = getSpans();
		List<List<String>> compoundNames = getNames();
		return compoundNames;
	}
	


}
