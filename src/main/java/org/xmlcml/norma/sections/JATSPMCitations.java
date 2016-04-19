package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

public class JATSPMCitations {

	private static final Logger LOG = Logger.getLogger(JATSPMCitations.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private Multiset<String> citedSet;
	ListMultimap<String, String> pmcidListByPmid;
	private Double nodesep = 0.4;
	private Double ranksep = 3.7;
	private int minCitationCount;
	
	public JATSPMCitations() {
		createMultimapsAndSets();
	}
	
	private void createMultimapsAndSets() {
		citedSet = HashMultiset.create();
		pmcidListByPmid = ArrayListMultimap.create();
	}

	public Multiset<String> getCitedSet() {
		return citedSet;
	}
	
	public void extractCitations(JATSArticleElement jatsArticleElement) {
		String pmcid = jatsArticleElement.getPMCID();
		LOG.trace(pmcid);
		JATSReflistElement reflist = jatsArticleElement.getReflistElement();
		if (reflist != null) {
			List<String> pmidList = reflist.getNonNullPMIDList();
			if (pmidList.size() > 0) {
				LOG.trace(">>>"+pmidList);
				citedSet.addAll(pmidList);
				for (String pmid : pmidList) {
					pmcidListByPmid.put(pmid, pmcid);
				}
			}
		}
	}

	/** in form PMCID, PMID
	 * 
	 * @return
	 */
	public List<JATSPMCitation> getCitations(int minCitationCount) {
		List<JATSPMCitation> citationList = new ArrayList<JATSPMCitation>();
		for (String pmid : pmcidListByPmid.keySet()) {
			List<String> pmcidList = pmcidListByPmid.get(pmid);
			if (pmcidList.size() >= minCitationCount) {
				for (String pmcid  : pmcidList) {
					JATSPMCitation citation = new JATSPMCitation(pmcid, pmid);
					citationList.add(citation);
				}
			}
		}
		return citationList;
	}


	public List<Multiset.Entry<String>> listCitationEntries(int minCount) {
//		List<Multiset.Entry<String>> entryList = Lists.newArrayList(entries);
		Iterable<Multiset.Entry<String>> entries = Multisets.copyHighestCountFirst(citedSet).entrySet();
		List<Multiset.Entry<String>> entryList = new ArrayList<Multiset.Entry<String>>();
		Iterator<Multiset.Entry<String>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Multiset.Entry<String> entry = iterator.next();
			if (entry.getCount() >= minCount) {
				entryList.add(entry);
			}
		}
		return entryList;
	}
	
}
