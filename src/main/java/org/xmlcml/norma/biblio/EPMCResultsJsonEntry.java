package org.xmlcml.norma.biblio;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.xmlcml.euclid.JodaDate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class EPMCResultsJsonEntry {
	
	private static final String YYYY_MM_DD = "yyyy-mm-dd";
	private static final String FULL_TEXT_URL_LIST = "fullTextUrlList";
	private static final String AFFILIATION    = "affiliation";
	private static final String PUB_MODEL      = "pubModel";
	private static final String LANGUAGE       = "language";
	private static final String PAGE_INFO      = "pageInfo";
	private static final String HAS_LABS_LINKS = "hasLabsLinks";
	private static final String DATE_OF_REVISION            = "dateOfRevision";
	private static final String ELECTRONIC_PUBLICATION_DATE = "electronicPublicationDate";
	private static final String HAS_DATE_OF_CREATION        = "hasDateOfCreation";
	private static final String HAS_TM_ACCESSION_NUMBERS    = "hasTMAccessionNumbers";
	private static final String HAS_DB_CROSS_REFERENCES     = "hasDbCrossReferences";
	private static final String HAS_TEXT_MINED_TERMS        = "hasTextMinedTerms";
	private static final String FIRST_PUBLICATION_DATE      = "firstPublicationDate";
	private static final String LUCENE_SCORE   = "luceneScore";
	private static final String HAS_REFERENCES = "hasReferences";
	private static final String CITED_BY_COUNT = "citedByCount";
	private static final String IN_PMC         = "inPMC";
	private static final String IN_EPMC        = "inEPMC";
	private static final String HAS_BOOK       = "hasBook";
	private static final String IS_OPEN_ACCESS = "isOpenAccess";
	private static final String SOURCE         = "source";
	private static final String Y              = "Y";
	private static final String AUTHOR_STRING  = "authorString";
	private static final String ABSTRACT_TEXT  = "abstractText";
	private static final String DOI            = "DOI";
	private static final String ID             = "id";
	private static final String PMID           = "pmid";
	private static final String PMCID          = "pmcid";
	private static final String TITLE          = "title";
	
	private JsonElement jsonEntry;
	private String abstractText;
	private String authorString;
	private String doi;
	private String id;
	private String pmid;
	private String pmcid;
	private String title;
	private Boolean hasBook;
	private boolean isOpenAccess;
	private boolean inEPMC;
	private boolean inPMC;
	private boolean hasReferences;
	private boolean hasTextMinedTerms;
	private boolean hasDbCrossReferences;
	private boolean hasLabsLinks;
	private boolean hasTMAccessionNumbers;
	private Integer citedByCount;
	private DateTime dateOfCreation;
	private DateTime dateOfRevision;
	private DateTime electronicPublicationDate;
	private DateTime firstPublicationDate;
	private Double luceneScore;
	private List<FullTextURL> fullTextList;
	private JournalInfo journalInfo;
	private List<EPMCAuthor> authorList;

	public EPMCResultsJsonEntry() {
	}

	public EPMCResultsJsonEntry(JsonElement entry) {
		this.jsonEntry = entry;
		createEntry();
	}

	public JsonElement getJsonEntry() {
		return jsonEntry;
	}

	public String getAbstractText() {
		return getText(ABSTRACT_TEXT);
	}

	public String getAuthorStringText() {
		return getText(AUTHOR_STRING);
	}

	public List<EPMCAuthor> getAuthorList() {
		List<EPMCAuthor> authorList = EPMCAuthor.createAuthorList(this);
		return authorList;
	}

	public String getDoiText() {
		return getText(DOI);
	}
	public String getIdText() {
		return getText(ID);
	}
	public String getPmidText() {
		return getText(PMID);
	}
	public String getPmcidText() {
		return getText(PMCID);
	}
 	public String getSource() {
 		return getText(SOURCE);
 	}
	public String getTitleText() {
		return getText(TITLE);
	}
    public boolean isOpenAccess() {
        return getBoolean(IS_OPEN_ACCESS);
    }
    public boolean inEPMC() {
        return getBoolean(IN_EPMC);
    }
    public boolean inPMC() {
        return getBoolean(IN_PMC);
    }
    public int citedByCount() {
        return getInteger(CITED_BY_COUNT);
    }
    public boolean hasReferences() {
        return getBoolean(HAS_REFERENCES);
    }
    public boolean hasTextMinedTerms() {
        return getBoolean(HAS_TEXT_MINED_TERMS);
    }
    public boolean hasDbCrossReferences() {
        return getBoolean(HAS_DB_CROSS_REFERENCES);
    }
    public boolean hasLabsLinks() {
        return getBoolean(HAS_LABS_LINKS);
    }
    public boolean hasTMAccessionNumbers() {
        return getBoolean(HAS_TM_ACCESSION_NUMBERS);
    }
    public DateTime getDateOfCreation() {
        return getDate(HAS_DATE_OF_CREATION);
    }
    public DateTime getDateOfRevision() {
        return getDate(DATE_OF_REVISION);
    }
    public DateTime getElectronicPublicationDate() {
        return getDate(ELECTRONIC_PUBLICATION_DATE);
    }
    public DateTime getFirstPublicationDate() {
        return getDate(FIRST_PUBLICATION_DATE);
    }
    public double getLuceneScore() {
    	return getDouble(LUCENE_SCORE);
    }
    public Boolean hasBook() {
    	return getBoolean(HAS_BOOK);
    }
    
 	public Integer getPageInfo() {
 		return getInteger(PAGE_INFO);
 	}

  	public String getAffiliation() {
  		return getText(AFFILIATION);
  	}

    public String getPubModel() {
   		return getText(PUB_MODEL);
   	}

   	public String getLanguage() {
   		return getText(LANGUAGE);
   	}
   	
 	public List<FullTextURL> getFullTextURLArray() {
   		List<FullTextURL> fullTextURLList = new ArrayList<FullTextURL>();
   		JsonArray urlArray =  getArray(FULL_TEXT_URL_LIST);
   		if (urlArray != null) {
	   		for (int i = 0; i < urlArray.size(); i++) {
	   			FullTextURL fullTextURL = new FullTextURL(urlArray.get(i));
	   			fullTextURLList.add(fullTextURL);
	   		}
   		}
   		return fullTextURLList;
   	}
 	
 	
 	public JournalInfo getJournalInfo() {
   		JsonArray journalInfo =  getArray("journalInfo");
   		return journalInfo == null ? null : new JournalInfo(journalInfo);
 	}

	private String getText(String field) {
		JsonElement entry = getJsonEntry();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonElement jsonElement = jsonObject.get(field);
		String text = jsonElement == null ? null : jsonElement.getAsString();
		return text;
	}
	
	JsonArray getArray(String field) {
		JsonElement entry = getJsonEntry();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonElement jsonElement = jsonObject.get(field);
		JsonArray array = jsonElement == null ? null : jsonElement.getAsJsonArray();
		return array;
	}
	
	private Boolean getBoolean(String field) {
		JsonElement entry = getJsonEntry();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonElement jsonElement = jsonObject.get(field);
		Boolean b = false;
		if (jsonElement != null && jsonElement.isJsonArray()) {
			jsonElement = ((JsonArray) jsonElement).get(0);
			b = Y.equals(jsonElement.getAsString());
		}
		return b;
	}
	
	private Integer getInteger(String field) {
		JsonElement entry = getJsonEntry();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonElement jsonElement = jsonObject.get(field);
		Integer value = jsonElement.getAsInt();
		return value;
	}
	
	private DateTime getDate(String field) {
		String text = getText(field);
		DateTime date = null;
		if (text != null && text.trim().length() != 0) {
			try {
				date = JodaDate.parseDate(text, YYYY_MM_DD);
			} catch (Exception e) {
				System.err.println("bad date: "+text);
			}
		}
		return date;
	}
	
	private Double getDouble(String field) {
		JsonElement entry = getJsonEntry();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonElement jsonElement = jsonObject.get(field);
		Double value = jsonElement == null ? null : jsonElement.getAsDouble();
		return value;
	}
	
	private void createEntry() {
		abstractText = getAbstractText();
		authorString = getAuthorStringText();
		doi = getDoiText();
		id = getIdText();
		pmid = getPmidText();
		pmcid = getPmcidText();
		title = getTitleText();
		authorList = getAuthorList();
		hasBook = hasBook();
		isOpenAccess = isOpenAccess();
		inEPMC = inEPMC();
		inPMC = inPMC();
		hasReferences = hasReferences();
		hasTextMinedTerms = hasTextMinedTerms();
		hasDbCrossReferences = hasDbCrossReferences();
		hasLabsLinks = hasLabsLinks();
		hasTMAccessionNumbers = hasTMAccessionNumbers();
		citedByCount = citedByCount();
		dateOfCreation = getDateOfCreation();
		dateOfRevision = getDateOfRevision();
		electronicPublicationDate = getElectronicPublicationDate();
		firstPublicationDate = getFirstPublicationDate();
		luceneScore = getLuceneScore();
		fullTextList = getFullTextURLArray();
		journalInfo = getJournalInfo();
	}

	static String getField(String fieldS, JsonArray jsonArray0) {
		String result;
		JsonElement jsonElement = (jsonArray0 == null) ? null : jsonArray0.get(0);
		result = getField(fieldS, jsonElement);
		return result;
	}

	static String getField(String fieldS, JsonElement jsonElement) {
		String result;
		JsonElement field = jsonElement.getAsJsonObject().get(fieldS);
		result = (field == null) ? null : field.getAsJsonArray().get(0).toString();
		return result;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
			sb.append(authorString+"\n"
			+doi+" | "
			+id+" | "
			+pmid+" | "
			+pmcid+"\n"
			+title+"\n");
		for (EPMCAuthor author : authorList) {
			sb.append(author+"\n");
		}
		sb.append(
		    (abstractText == null) ? "" : abstractText.substring(0,  Math.min(abstractText.length(), 200))+"... \n"
			+hasBook + " |  "
		    +isOpenAccess + " |  "
		    +inEPMC + " |  "
		    +inPMC + " |  "
		    +hasReferences + " |  "
		    +hasTextMinedTerms + " |  "
		    +hasDbCrossReferences + " |  "
		    +hasLabsLinks + " |  "
		    +hasTMAccessionNumbers + " |  "
		    +citedByCount + " |  "
		    +dateOfCreation + " |  "
		    +dateOfRevision + " |  "
		    +electronicPublicationDate + " |  "
		    +firstPublicationDate + " |  "
		    +luceneScore+"\n"
		    +fullTextList+"\n"
		    +journalInfo)
			;
		return sb.toString();
	}
	

}
