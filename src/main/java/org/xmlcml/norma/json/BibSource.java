package org.xmlcml.norma.json;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.norma.util.JsonUtil;

import com.google.gson.JsonObject;

/** bibliographic data within a catalog entry.
 * 
 * may require renaming later
 * 
 * @author pm286
 *
 */
public class BibSource {
		
		private static final Logger LOG = Logger.getLogger(BibSource.class);
		static {
			LOG.setLevel(Level.DEBUG);
		}
		
		private String doi;
		private String last_updated;
		private String description;
		private Collection<String> author;
		private String firstpage;
		private String id;
		private String volume;
		private String fulltext_xml;
		private String created_date; // might convert to Date later
		private String date; // might convert to Date later
		private String issue;
		private String fulltext_pdf;
		
		public BibSource() {
			
		}
		public BibSource(
				 String doi,
				 String last_updated,
				 String description,
				 Collection<String> author,
				 String firstpage,
				 String id,
				 String volume,
				 String fulltext_xml,
				 String created_date, // might convert to Date later
				 String date, // might convert to Date later
				 String issue,
				 String fulltext_pdf
				) {
			setDoi(doi);
			setLast_updated(last_updated);
			setDescription(description);
			setAuthor(author);
			setFirstpage(firstpage);
			setId(id);
			setVolume(volume);
			setFulltext_xml(fulltext_xml);
			setCreated_date(created_date);
			setDate(date);
			setIssue(issue);
			setFulltext_pdf(fulltext_pdf);
		}
		
		public static BibSource createBibSource(JsonObject jsonObject) {
			String doi                = jsonObject.get("doi").getAsString();
			String last_updated       = jsonObject.get("last_updated").getAsString();
			String description        = jsonObject.get("description").getAsString();
			Collection<String> author = JsonUtil.getAsStringArray(jsonObject, "author");
			String firstpage          = jsonObject.get("firstpage").getAsString();
			String id                 = jsonObject.get("id").getAsString();
			String volume             = jsonObject.get("volume").getAsString();
			String fulltext_xml       = jsonObject.get("fulltext_xml").getAsString();
			String created_date       = jsonObject.get("created_date").getAsString();
			String date               = jsonObject.get("date").getAsString();
			String issue              = jsonObject.get("issue").getAsString();
			String fulltext_pdf       = jsonObject.get("fulltext_pdf").getAsString();
			
			BibSource bibSource = new BibSource(
		        doi,
		        last_updated,
		        description,
				author,
		        firstpage,
		        id,
		        volume,
		        fulltext_xml,
		        created_date,
		        date,
		        issue,
		        fulltext_pdf
					);
			return bibSource;
		}
		
		public String getDoi() {
			return doi;
		}
		public void setDoi(String doi) {
			this.doi = doi;
		}
		public String getLast_updated() {
			return last_updated;
		}
		public void setLast_updated(String last_updated) {
			this.last_updated = last_updated;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Collection<String> getAuthor() {
			return author;
		}
		public void setAuthor(Collection<String> author) {
			this.author = author;
		}
		public String getFirstpage() {
			return firstpage;
		}
		public void setFirstpage(String firstpage) {
			this.firstpage = firstpage;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getVolume() {
			return volume;
		}
		public void setVolume(String volume) {
			this.volume = volume;
		}
		public String getFulltext_xml() {
			return fulltext_xml;
		}
		public void setFulltext_xml(String fulltext_xml) {
			this.fulltext_xml = fulltext_xml;
		}
		public String getCreated_date() {
			return created_date;
		}
		public void setCreated_date(String created_date) {
			this.created_date = created_date;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getIssue() {
			return issue;
		}
		public void setIssue(String issue) {
			this.issue = issue;
		}
		public String getFulltext_pdf() {
			return fulltext_pdf;
		}
		public void setFulltext_pdf(String fulltext_pdf) {
			this.fulltext_pdf = fulltext_pdf;
		}
		
		@Override 
		public String toString() {
			String s = "";
			s += " / "+doi;
			s += " / "+last_updated;
			s += " / "+description;
			s += " / "+author;
			s += " / "+firstpage;
			s += " / "+id;
			s += " / "+volume;
			s += " / "+fulltext_xml;
			s += " / "+created_date; 
			s += " / "+date; 
			s += " / "+issue;
			s += " / "+fulltext_pdf;
			return s;

		}
}
