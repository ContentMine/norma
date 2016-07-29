package org.xmlcml.norma.biblio;

import com.google.gson.JsonElement;

public class Journal {

	private static final String ESSN = "ESSN";
	private static final String NL_MID = "NLMid";
	private static final String MEDLINE_ABBREVIATION = "medlineAbbreviation";
	private static final String ISO_ABBREVIATION = "ISOAbbreviation";
	private static final String TITLE = "title";
	private static final String JOURNAL = "journal";
	
	private String title;
	private String isoAbbreviation;
	private String medlineAbbreviation;
	private String nlmId;
	private String essn;

	/**
        "journal": [
          {
            "title": [
              "PloS one"
            ],
            "ISOAbbreviation": [
              "PLoS ONE"
            ],
            "medlineAbbreviation": [
              "PLoS One"
            ],
            "NLMid": [
              "101285081"
            ],
            "ESSN": [
              "1932-6203"
            ]
          }
        ]
      }
	 */
	public Journal() {
		
	}
	
	public Journal(JsonElement element) {
		title = EPMCResultsJsonEntry.getField(TITLE, element);
		isoAbbreviation = EPMCResultsJsonEntry.getField(ISO_ABBREVIATION, element);
		medlineAbbreviation = EPMCResultsJsonEntry.getField(MEDLINE_ABBREVIATION, element);
		nlmId = EPMCResultsJsonEntry.getField(NL_MID, element);
		essn = EPMCResultsJsonEntry.getField(ESSN, element);
	}

	public static Journal getJournal(JsonElement element) {
		Journal journal = null;
		JsonElement journalElement = element.getAsJsonObject().get(JOURNAL);
		if(journalElement != null) {
			journal = new Journal(journalElement.getAsJsonArray().get(0));
		}
		return journal;
	}

	public String getTitle() {
		return title;
	}

	public String getIsoAbbreviation() {
		return isoAbbreviation;
	}

	public String getMedlineAbbreviation() {
		return medlineAbbreviation;
	}

	public String getNlmId() {
		return nlmId;
	}

	public String getEssn() {
		return essn;
	}

	public String toString() {
		String s = ""
			+title+" | "
			+isoAbbreviation+" | "
			+medlineAbbreviation+" | "
			+nlmId+" | "
			+essn;
		return s;
	}

}
