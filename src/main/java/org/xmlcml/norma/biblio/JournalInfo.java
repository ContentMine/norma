package org.xmlcml.norma.biblio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JournalInfo {

 	private static final String PRINT_PUBLICATION_DATE = "printPublicationDate";
	private static final String YEAR_OF_PUBLICATION = "yearOfPublication";
	private static final String MONTH_OF_PUBLICATION = "monthOfPublication";
	private static final String DATE_OF_PUBLICATION = "dateOfPublication";
	private static final String JOURNAL_ISSUE_ID = "journalIssueId";
	private static final String VOLUME = "volume";
	private static final String ISSUE = "issue";
	
	private String issue;
	private String volume;
	private String journalIssueId;
	private String dateOfPublication;
	public String getIssue() {
		return issue;
	}

	public String getVolume() {
		return volume;
	}

	public String getJournalIssueId() {
		return journalIssueId;
	}

	public String getDateOfPublication() {
		return dateOfPublication;
	}

	public String getMonthOfPublication() {
		return monthOfPublication;
	}

	public String getYearOfPublication() {
		return yearOfPublication;
	}

	public String getPrintPublicationDate() {
		return printPublicationDate;
	}

	private String monthOfPublication;
	private String yearOfPublication;
	private String printPublicationDate;
	private Journal journal;

	/**
    "journalInfo": [
        {
          "issue": [
            "2"
          ],
          "volume": [
            "10"
          ],
          "journalIssueId": [
            "2248873"
          ],
          "dateOfPublication": [
            "2015"
          ],
          "monthOfPublication": [
            "0"
          ],
          "yearOfPublication": [
            "2015"
          ],
          "printPublicationDate": [
            "2015-01-01"
          ],
          "journal": [
            {
              "title": [
                "PloS one"
              ],
              "ISOAbbreviation": [
                "PLoS ONE"
              ],
*/

	public JournalInfo(JsonArray journalInfo) {
		JsonElement element = journalInfo.get(0);
		if (element != null) {
			issue = EPMCResultsJsonEntry.getField(ISSUE, element);
			volume = EPMCResultsJsonEntry.getField(VOLUME, element);
			journalIssueId = EPMCResultsJsonEntry.getField(JOURNAL_ISSUE_ID, element);
			dateOfPublication = EPMCResultsJsonEntry.getField(DATE_OF_PUBLICATION, element);
			monthOfPublication = EPMCResultsJsonEntry.getField(MONTH_OF_PUBLICATION, element);
			yearOfPublication = EPMCResultsJsonEntry.getField(YEAR_OF_PUBLICATION, element);
			printPublicationDate = EPMCResultsJsonEntry.getField(PRINT_PUBLICATION_DATE, element);
			journal = Journal.getJournal(element);
		}
	}
	
	public String toString() {
		String s = ""
			+issue+" | "
			+volume+" | "
			+journalIssueId+" | "
			+dateOfPublication+" | "
			+monthOfPublication+" | "
			+yearOfPublication+" | "
			+printPublicationDate+"\n"
			+journal;
		return s;
	}

}
