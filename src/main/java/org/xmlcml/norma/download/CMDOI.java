package org.xmlcml.norma.download;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.files.CProject;

public class CMDOI extends AbstractCM {

	private static final Logger LOG = Logger.getLogger(CMDOI.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	/**
	 * 
http://dx.doi.org/10.5539/cis.v9n2p126
http://dx.doi.org/10.1530/edm-16-0003
http://dx.doi.org/10.12968/bjsn.2016.11.4.166
http://dx.doi.org/10.14358/pers.82.5.320
http://dx.doi.org/10.3109/10715762.2016.1162299
http://dx.doi.org/10.1127/ejm/2015/0027-2502
http://dx.doi.org/10.7467/ksae.2016.24.3.285
http://dx.doi.org/10.20488/austd.22645
http://dx.doi.org/10.1177/2150135116645604
http://dx.doi.org/10.1525/irqr.2016.9.1.29
	 */

	static Pattern PATTERN = Pattern.compile("https?://dx\\.doi\\.org/(10\\.\\d{3,8})/(.*)");
	public static CMDOI DUMMY = new CMDOI(null, null);
	
	public CMDOI(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	public CMDOI() {
		
	}
	
	public static List<CMDOI> readDois(List<String> doiNames) {
		List<CMDOI> doiList = new ArrayList<CMDOI>();
		for (String doiName : doiNames) {
			CMDOI doi = CMDOI.createDOI(doiName);
			if (doi != null) {
				doiList.add(doi);
			}
		}
		return doiList;
	}

	private static CMDOI createDOI(String doiName) {
		CMDOI doi = null;
		if (doiName != null) {
			Matcher matcher = PATTERN.matcher(doiName);
			if (matcher.matches()) {
				doi = new CMDOI(matcher.group(1), matcher.group(2));
			} else {
				LOG.debug("bad: "+doiName);
				doi = DUMMY;
			}
		}
		return doi;
	}
}
