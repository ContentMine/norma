package org.xmlcml.norma.sections;

import org.xmlcml.norma.util.DottyLink;

/** holds PMCID and PMID.
 * citing between all combinations
 * will evolve... as we analyze more documents
 * @author pm286
 *
 */
public class JATSPMCitation implements DottyLink {

	private String pmcid;
	private String pmid;

	public JATSPMCitation() {
		
	}
	
	public JATSPMCitation(String pmcid, String pmid) {
		setPMCID(pmcid);
		setPMID(pmid);
		
	}

	private void setPMID(String pmid) {
		this.pmid = pmid;
	}

	private void setPMCID(String pmcid) {
		this.pmcid = pmcid;
	}

	/** this logic may change as we explore documents
	 * 
	 */
	public String getHead() {
		return pmcid;
	}

	/** this logic may change as we explore documents
	 * 
	 */
	public String getTail() {
		return pmid;
	}
}
