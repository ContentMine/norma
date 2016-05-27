package org.xmlcml.norma.download;

public class DownloadFilter {

	private String issn;
	private String fromPubDate;

	public String getISSN() {
		return issn;
	}

	public String getFromPubDate() {
		return fromPubDate;
	}

	public void setFromPubDate(String fromPubDate) {
		this.fromPubDate = fromPubDate;
	}

	public void setISSN(String issn) {
		this.issn = issn;
	}

	public String getFilterString() {
		String s = "";
		if (issn != null) {
			s += "issn:"+issn+",";
		}
		if (fromPubDate != null) {
			s += "from-pub-date:"+fromPubDate+",";
		}
		return s.length() > 0 ? "filter=" + s.substring(0, s.length() - 1) : "";
	}
}
