package org.xmlcml.norma.download;

import org.xmlcml.norma.download.CrossRefDownloader.Type;

public class DownloadFilter {

	private String issn;
	private String fromPubDate;
	private String untilPubDate;
	private Type type;

	public String getISSN() {
		return issn;
	}

	public String getFromPubDate() {
		return fromPubDate;
	}

	public void setFromPubDate(String fromPubDate) {
		this.fromPubDate = fromPubDate;
	}

	public String getUntilPubDate() {
		return untilPubDate;
	}

	public void setUntilPubDate(String toPubDate) {
		this.untilPubDate = toPubDate;
	}

	public void setISSN(String issn) {
		this.issn = issn;
	}
	
	public void setType(Type type) {
		this.type = type;
	}


	public String getFilterString() {
		String s = "";
		if (issn != null) {
			s += "issn:"+issn+",";
		}
		if (fromPubDate != null) {
			s += "from-pub-date:"+fromPubDate+",";
		}
		if (untilPubDate != null) {
			s += "until-pub-date:"+untilPubDate+",";
		}
		if (type != null) {
			s += "type:"+type.getName()+",";
		}
		return s.length() > 0 ? "filter=" + s.substring(0, s.length() - 1) : "";
	}

}
