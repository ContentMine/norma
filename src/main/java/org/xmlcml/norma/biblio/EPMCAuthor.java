package org.xmlcml.norma.biblio;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class EPMCAuthor {

	private static final String AUTHOR = "author";
	/**
    "authorList": [
      {
        "author": [
          {
            "fullName": [
              "Li Y"
            ],
            "firstName": [
              "Yuanyuan"
            ],
            "lastName": [
              "Li"
            ],
            "initials": [
              "Y"
            ],
            "affiliation": [
              "Key Laboratory of Systems Biology, Shandong Normal University, Jinan, Shandong, China; Key Laboratory of Computational Biology, CAS-MPG Partner Institute for Computational Biology, Shanghai Institutes for Biological Sciences, Chinese Academy of Sciences, Shanghai, China."
            ]
          },
          {
            "fullName": [
              "Ma X"
            ],
	 */
	private static final String AUTHOR_LIST = "authorList";
	private String fullName;
	private String firstName;
	private String lastName;
	private String initials;
	private String affiliation;

	public static List<EPMCAuthor> createAuthorList(EPMCResultsJsonEntry entry) {
		JsonArray array = entry.getArray(AUTHOR_LIST);
		List<EPMCAuthor> authorList = null;
		if (array != null) {
			JsonElement authorElement = array.get(0);
			JsonObject jsonObject = authorElement.getAsJsonObject();
			if (jsonObject != null) {
				// the true list is under "author"
				JsonElement element = jsonObject.get(AUTHOR);
				if (element != null) {
					JsonArray authorArray = element.getAsJsonArray();
					int size = authorArray.size();
					authorList = new ArrayList<EPMCAuthor>();
					for (int i = 0; i < size; i++) {
						authorList.add(new EPMCAuthor(authorArray.get(i)));
					}
				}
			}
		}
		return authorList;
	}

	public EPMCAuthor(JsonElement jsonElement) {
		if (jsonElement != null) {
			fullName = EPMCResultsJsonEntry.getField("fullName", jsonElement);
			firstName = EPMCResultsJsonEntry.getField("firstName", jsonElement);
			lastName = EPMCResultsJsonEntry.getField("lastName", jsonElement);
			initials = EPMCResultsJsonEntry.getField("initials", jsonElement);
			affiliation = EPMCResultsJsonEntry.getField("affiliation", jsonElement);
		}
	}

	public String getFullName() {
		return fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getInitials() {
		return initials;
	}

	public String getAffiliation() {
		return affiliation;
	}
	
	public String toString() {
		String s = ""
			+fullName+" | "
			+firstName+" | "
			+initials+" | "
			+lastName+" | "
			+affiliation
			;
		return s;
	}

}
