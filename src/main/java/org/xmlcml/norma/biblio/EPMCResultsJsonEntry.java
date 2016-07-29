package org.xmlcml.norma.biblio;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.xmlcml.cmine.util.CellRenderer;
import org.xmlcml.cmine.util.DataTablesTool;
import org.xmlcml.euclid.JodaDate;
import org.xmlcml.html.HtmlElement;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class EPMCResultsJsonEntry {
	
	private static final Logger LOG = Logger.getLogger(EPMCResultsJsonEntry.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String FULL_TEXT_URL_LIST = "fullTextUrlList";
	public static final String AFFILIATION    = "affiliation";
	public static final String PUB_MODEL      = "pubModel";
	public static final String LANGUAGE       = "language";
	public static final String PAGE_INFO      = "pageInfo";
	public static final String HAS_LABS_LINKS = "hasLabsLinks";
	public static final String DATE_OF_REVISION            = "dateOfRevision";
	public static final String ELECTRONIC_PUBLICATION_DATE = "electronicPublicationDate";
	public static final String HAS_DATE_OF_CREATION        = "hasDateOfCreation";
	public static final String HAS_TM_ACCESSION_NUMBERS    = "hasTMAccessionNumbers";
	public static final String HAS_DB_CROSS_REFERENCES     = "hasDbCrossReferences";
	public static final String HAS_TEXT_MINED_TERMS        = "hasTextMinedTerms";
	public static final String FIRST_PUBLICATION_DATE      = "firstPublicationDate";
	public static final String LUCENE_SCORE   = "luceneScore";
	public static final String HAS_REFERENCES = "hasReferences";
	public static final String CITED_BY_COUNT = "citedByCount";
	public static final String IN_PMC         = "inPMC";
	public static final String IN_EPMC        = "inEPMC";
	public static final String HAS_BOOK       = "hasBook";
	public static final String IS_OPEN_ACCESS = "isOpenAccess";
	public static final String SOURCE         = "source";
	public static final String AUTHOR_STRING  = "authorString";
	public static final String ABSTRACT_TEXT  = "abstractText";
	public static final String DOI            = "DOI";
	public static final String ID             = "id";
	public static final String PMID           = "pmid";
	public static final String PMCID          = "pmcid";
	public static final String TITLE          = "title";
	public static final CellRenderer[] FLAGS = {
		new CellRenderer(TITLE).setBrief(50),
		new CellRenderer(FULL_TEXT_URL_LIST).setWordCount(true).setVisible(false),
		new CellRenderer(AFFILIATION).setBrief(20),
		new CellRenderer(PUB_MODEL).setVisible(false),
		new CellRenderer(LANGUAGE).setVisible(false),
		new CellRenderer(PAGE_INFO).setVisible(false),
		new CellRenderer(HAS_LABS_LINKS).setVisible(false),
		new CellRenderer(DATE_OF_REVISION).setVisible(false),
		new CellRenderer(ELECTRONIC_PUBLICATION_DATE).setVisible(false),
		new CellRenderer(HAS_DATE_OF_CREATION).setVisible(false),
		new CellRenderer(HAS_TM_ACCESSION_NUMBERS).setVisible(false),
		new CellRenderer(HAS_DB_CROSS_REFERENCES).setVisible(false),
		new CellRenderer(HAS_TEXT_MINED_TERMS).setVisible(false),
		new CellRenderer(FIRST_PUBLICATION_DATE),
		new CellRenderer(LUCENE_SCORE).setVisible(false),
		new CellRenderer(HAS_REFERENCES).setVisible(false),
		new CellRenderer(CITED_BY_COUNT).setVisible(false),
		new CellRenderer(IN_PMC).setVisible(false),
		new CellRenderer(IN_EPMC).setVisible(false),
		new CellRenderer(HAS_BOOK).setVisible(false),
		new CellRenderer(IS_OPEN_ACCESS).setVisible(false),
		new CellRenderer(SOURCE).setVisible(false),
		new CellRenderer(AUTHOR_STRING).setBrief(20),
		new CellRenderer(ABSTRACT_TEXT).setBrief(20),
		new CellRenderer(DOI).setHref0("http://doi.org/"),
		new CellRenderer(ID).setVisible(false),
		new CellRenderer(PMID).setVisible(false),
		new CellRenderer(PMCID).setHref0("foo/").setHref1("/bar").setVisible(false),
	};	
	
	private static final String Y              = "Y";
	private static final String YYYY_MM_DD = "yyyy-mm-dd";
	
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
	private DataTablesTool dataTablesTool;

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
		JsonElement jsonElement = getField(field);
		String text = jsonElement == null ? null : jsonElement.getAsString();
		return text;
	}
	
	JsonArray getArray(String field) {
		JsonElement jsonElement = getField(field);
		JsonArray jsonArray = null;
		if (jsonElement != null) {
			if (jsonElement instanceof JsonArray) {
				jsonArray = jsonElement.getAsJsonArray();
			} else {
				jsonArray = new JsonArray();
				jsonArray.add(jsonElement);
			}
		}
		return jsonArray;
	}
	
	private Boolean getBoolean(String field) {
		JsonElement jsonElement = getField(field);
		Boolean b = false;
		if (jsonElement != null) {
			if (jsonElement.isJsonArray()) {
				jsonElement = ((JsonArray) jsonElement).get(0);
			}
			b = Y.equals(jsonElement.getAsString());
		}
		return b;
	}
	
	private Integer getInteger(String field) {
		JsonElement jsonElement = getField(field);
		Integer value = jsonElement.getAsInt();
		return value;
	}

	private JsonElement getField(String field) {
		JsonElement entry = getJsonEntry();
		JsonObject jsonObject = entry.getAsJsonObject();
		JsonElement jsonElement = jsonObject.get(field);
		jsonElement = strip1ElementArray(jsonElement);
		return jsonElement;
	}

	private JsonElement strip1ElementArray(JsonElement jsonElement) {
		if (jsonElement instanceof JsonArray) {
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			if (jsonArray.size() == 1) {
				jsonElement = jsonArray.get(0);
				if (jsonElement instanceof JsonPrimitive) {
					JsonPrimitive primitive = (JsonPrimitive) jsonElement;
				}
			}
		}
		return jsonElement;
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
		JsonElement jsonElement = getField(field);
		Double value = jsonElement == null ? null : jsonElement.getAsDouble();
		return value;
	}
	
	private Object getObject(String field) {
		JsonElement jsonElement = this.getField(field);
		return jsonElement.getAsString();
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

	public void setDataTablesTool(DataTablesTool dataTablesTool) {
		this.dataTablesTool = dataTablesTool;
	}

	public List<HtmlElement> createHtmlElements(List<CellRenderer> fieldList) {
		List<HtmlElement> htmlElements = new ArrayList<HtmlElement>();
		for (CellRenderer renderer : fieldList) {
			if (renderer.isVisible()) {
				String value = "nullx";
				Object object = getField(renderer.getFlag());
				if (object instanceof JsonArray) {
					value = String.valueOf(((JsonArray)object).get(0));
				} else if (object instanceof JsonPrimitive){
					value = ((JsonPrimitive)object).getAsString();
				} else if (object == null) {
					value = "?";
				} else {
					value = "?"+String.valueOf(object)+"/"+object.getClass();
				}
				renderer.setValue(value);
				htmlElements.add(renderer.getHtmlElement());
			}
		}
		return htmlElements;
	}
	

}
