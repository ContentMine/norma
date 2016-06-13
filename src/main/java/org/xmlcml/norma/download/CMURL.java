package org.xmlcml.norma.download;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CProject;

public class CMURL extends AbstractCM {

	private static final Logger LOG = Logger.getLogger(CMURL.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	/**
	 * 
http://cancerandmetabolism.biomedcentral.com/articles/10.1186/s40170-016-0148-6
http://www.ans.org/pubs/journals/fst/a_38355
http://jpn.ca/vol41-issue3/41-3-192/
http://ieeexplore.ieee.org/lpdocs/epic03/wrapper.htm?arnumber=7374712
http://ieeexplore.ieee.org/lpdocs/epic03/wrapper.htm?arnumber=7399676
http://www.journalsleep.org/ViewAbstract.aspx?pid=30605	 */

	static Pattern PATTERN = Pattern.compile("https?://([^/]*)/(.*)");
	public static CMURL DUMMY = new CMURL(null, null);
	
	public CMURL(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	public CMURL() {
		
	}
	
	public static List<CMURL> readUrls(List<String> urlNames) {
		List<CMURL> urlList = new ArrayList<CMURL>();
		for (String urlName : urlNames) {
			CMURL url = CMURL.createDOI(urlName);
			if (url != null) {
				urlList.add(url);
			}
		}
		return urlList;
	}

	private static CMURL createDOI(String urlName) {
		CMURL url = null;
		if (urlName != null) {
			Matcher matcher = PATTERN.matcher(urlName);
			if (matcher.matches()) {
				url = new CMURL(matcher.group(1), matcher.group(2));
			} else {
				LOG.debug("bad: "+urlName);
				url = DUMMY;
			}
		}
		return url;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
