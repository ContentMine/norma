package org.xmlcml.norma.applic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** bibliography from ESSoilDB.
 * 
#1997#VOL. 14, 366-368 (Sep/Oct 2002)#Volatile Constituents of the Essential Oil of Acorus calamus L. Growth in Konya Province (Turkey).#M. Ozcan, A. Akgul, J.C Chalchat.#Journal of Essential Oil Research#Acoraceae#Plant
#1998#VOL. 15, 313-318 (Sep/Oct 2003)#Composition of Essential Oil of Sweet Flag(Acorus calamus L.) Leaves at Different Growing Phases#Petras R. Venskutonis,Audrone Dagilyte#Journal of Essential Oil Research#Acoraceae#Plant
 * 
 * @author pm286
 *
 */
public class BibliographicRecord {

	private static final Logger LOG = Logger.getLogger(BibliographicRecord.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	/**
VOL. 14, 366-368 (Sep/Oct 2002)
VOL. 15, 313-318 (Sep/Oct 2003)
VOL. 18, 18-20 (2003)
	 */
	private static final String NA = "NA";
	// too messy, give up
	private static final Pattern BIB = Pattern.compile("VOL\\.\\s*(\\d+)\\,\\s*(\\d+)\\s*[\\-\\–]\\s*(\\d+)\\s*\\(\\s*([A-Z][a-z]+(/[A-Z][a-z]+)?)?\\s*(\\d+)\\s*\\)?\\s*");
	
	private String year;
	private String bib;
	private String title;
	private List<String> authors;
	private String journalTitle;

	public BibliographicRecord() {
		
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((bib == null) ? 0 : bib.hashCode());
		result = prime * result + ((journalTitle == null) ? 0 : journalTitle.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BibliographicRecord other = (BibliographicRecord) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (bib == null) {
			if (other.bib != null)
				return false;
		} else if (!bib.equals(other.bib))
			return false;
		if (journalTitle == null) {
			if (other.journalTitle != null)
				return false;
		} else if (!journalTitle.equals(other.journalTitle))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}



	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		year = NA.equals(year) ? null : year;
		this.year = year;
	}

	public String getBib() {
		return bib;
	}

	public void setBib(String bib) {
		bib = bib.trim();
		Matcher matcher = BIB.matcher(bib);
		if (matcher.matches()) {
			String vol = matcher.group(1);
			String firstPage = matcher.group(2);
			String lastPage = matcher.group(3);
			String dates = matcher.group(4);
		} else {
			LOG.trace("No bib match: "+bib);
		}
		this.bib = bib;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(String authorString) {
		this.authors = Arrays.asList(authorString.split("\\s*,\\s*"));
	}

	public String getJournalTitle() {
		return journalTitle;
	}

	public void setJournalTitle(String jtitle) {
		this.journalTitle = jtitle;
	}

	/**
	private String year;
	private String bib;
	private String title;
	private String authors;
	private String journalTitle;
	 * @return
	 */
	public List<String> getValues() {
		List<String> sList = new ArrayList<String>();
		sList.add(String.valueOf(hashCode()));
		sList.add(year);
		sList.add("\""+bib+"\"");
		sList.add("\""+title+"\"");
		sList.add("\""+authors+"\"");
		sList.add("\""+journalTitle+"\"");
		
		return sList;
	}

	public String toString() {
		String s = getValues().toString();
		return s.substring(1, s.length() - 1);
	}
}
