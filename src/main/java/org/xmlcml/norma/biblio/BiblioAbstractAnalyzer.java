package org.xmlcml.norma.biblio;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlcml.graphics.html.HtmlB;
import org.xmlcml.graphics.html.HtmlDiv;
import org.xmlcml.graphics.html.HtmlP;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/** analyzes semi-structured abstracts
 * 
 * @author pm286
 *
 */
public class BiblioAbstractAnalyzer {

	public Set<String> sectionSet = new HashSet<String>();
	private Multiset<String> sectionMultiset;
	public final static Pattern ABSTRACT_SECT_PATTERN = Pattern.compile("([A-Z]{3,}):");
	private static final String _ANONYMOUS = "_ANONYMOUS";

	private Set<String> createSectionSet() {
	    sectionSet.add("ABSI");
	    sectionSet.add("ABSTRACT");
	    sectionSet.add("ACQUISITION");
	    sectionSet.add("ACR");
	    sectionSet.add("ACTIVITY");
	    sectionSet.add("AHI");
	    sectionSet.add("AHR");
	    sectionSet.add("AIM");
	    sectionSet.add("AIMS");
	    sectionSet.add("ALREADY");
	    sectionSet.add("ANALYSES");
	    sectionSet.add("ANALYSIS");
	    sectionSet.add("ANP");
	    sectionSet.add("ANSWER");
	    sectionSet.add("AOR");
	    sectionSet.add("ARFS");
	    sectionSet.add("ART");
	    sectionSet.add("ASPECTS");
	    sectionSet.add("ASSESSMENT");
	    sectionSet.add("AUC");
	    sectionSet.add("BACKGROUND");
	    sectionSet.add("BACKGROUNDS");
	    sectionSet.add("BDES");
	    sectionSet.add("BMES");
	    sectionSet.add("BMI");
	    sectionSet.add("BNP");
	    sectionSet.add("BPH");
	    sectionSet.add("BSA");
	    sectionSet.add("BSI");
	    sectionSet.add("BTT");
	    sectionSet.add("CABG");
	    sectionSet.add("CAD");
	    sectionSet.add("CALCULATOR");
	    sectionSet.add("CAUTION");
	    sectionSet.add("CHANCE");
	    sectionSet.add("CHARM");
	    sectionSet.add("CHD");
	    sectionSet.add("CMDS");
	    sectionSet.add("COMMENTS");
	    sectionSet.add("COMPLICATIONS");
	    sectionSet.add("CONCERN");
	    sectionSet.add("CONCLUSION");
	    sectionSet.add("CONCLUSIONS");
	    sectionSet.add("CONCLUSIONSX");
	    sectionSet.add("CONSUMPTION");
	    sectionSet.add("CONTENT");
	    sectionSet.add("CONTEXT");
	    sectionSet.add("COPD");
	    sectionSet.add("COSTS");
	    sectionSet.add("COUCLUSION");
	    sectionSet.add("CPT");
	    sectionSet.add("CRC");
	    sectionSet.add("CRF");
	    sectionSet.add("CRITERIA");
	    sectionSet.add("CRP");
	    sectionSet.add("CSS");
	    sectionSet.add("CVD");
	    sectionSet.add("CWP");
	    sectionSet.add("DATA");
	    sectionSet.add("DBP");
	    sectionSet.add("DCL");
	    sectionSet.add("DEFINITION");
	    sectionSet.add("DESIGN");
	    sectionSet.add("DFS");
	    sectionSet.add("DISCUSSION");
	    sectionSet.add("DISEASE");
	    sectionSet.add("DISSEMINATION");
	    sectionSet.add("DSS");
	    sectionSet.add("DURATION");
	    sectionSet.add("ECW");
	    sectionSet.add("EGAT");
	    sectionSet.add("ENDPOINT");
	    sectionSet.add("ERI");
	    sectionSet.add("EVIDENCE");
	    sectionSet.add("EXPOSURE");
	    sectionSet.add("EXPOSURES");
	    sectionSet.add("EXTRACTION");
	    sectionSet.add("FACTORS");
	    sectionSet.add("FCSRT");
	    sectionSet.add("FFM");
	    sectionSet.add("FINDINGS");
	    sectionSet.add("FMH");
	    sectionSet.add("FRACTURE");
	    sectionSet.add("FUNDING");
	    sectionSet.add("GFR");
	    sectionSet.add("GNRI");
	    sectionSet.add("GOALS");
	    sectionSet.add("GROUP");
	    sectionSet.add("HCG");
	    sectionSet.add("HDL");
	    sectionSet.add("HMG");
	    sectionSet.add("HMSO");
	    sectionSet.add("HPW");
	    sectionSet.add("HRQOL");
	    sectionSet.add("HRR");
	    sectionSet.add("HUC");
	    sectionSet.add("HYPOTHESIS");
	    sectionSet.add("IBW");
	    sectionSet.add("IHD");
	    sectionSet.add("III");
	    sectionSet.add("IMPACT");
	    sectionSet.add("IMPLICATIONS");
	    sectionSet.add("IMPORTANCE");
	    sectionSet.add("INDICATIONS");
	    sectionSet.add("INTERPRETATION");
	    sectionSet.add("INTERVENTION");
	    sectionSet.add("INTERVENTIONS");
	    sectionSet.add("INTRODUCTION");
	    sectionSet.add("IQR");
	    sectionSet.add("IRR");
	    sectionSet.add("ISSUE");
	    sectionSet.add("JIB");
	    sectionSet.add("LAP");
	    sectionSet.add("LGA");
	    sectionSet.add("LIMITATION");
	    sectionSet.add("LIMITATIONS");
	    sectionSet.add("LNY");
	    sectionSet.add("LOCAL");
	    sectionSet.add("LPJ");
	    sectionSet.add("LRF");
	    sectionSet.add("LRYGBP");
	    sectionSet.add("LVC");
	    sectionSet.add("LVH");
	    sectionSet.add("LVM");
	    sectionSet.add("LVRS");
	    sectionSet.add("MACCE");
	    sectionSet.add("MACE");
	    sectionSet.add("MAP");
	    sectionSet.add("MATERIALS");
	    sectionSet.add("MEASURE");
	    sectionSet.add("MEASUREMENT");
	    sectionSet.add("MEASUREMENTS");
	    sectionSet.add("MEASURES");
	    sectionSet.add("MEDIAN");
	    sectionSet.add("METHOD");
	    sectionSet.add("METHODOLOGY");
	    sectionSet.add("METHODS");
	    sectionSet.add("MMF");
	    sectionSet.add("MMSE");
	    sectionSet.add("MPI");
	    sectionSet.add("MREC");
	    sectionSet.add("MRNYGBP");
	    sectionSet.add("MTGC");
	    sectionSet.add("NEED");
	    sectionSet.add("NOD");
	    sectionSet.add("NODAT");
	    sectionSet.add("NPW");
	    sectionSet.add("NUMBER");
	    sectionSet.add("NURSING");
	    sectionSet.add("NUTRITION");
	    sectionSet.add("OBJECT");
	    sectionSet.add("OBJECTIVE");
	    sectionSet.add("OBJECTIVES");
	    sectionSet.add("OBJECTS");
	    sectionSet.add("OBJETIVE");
	    sectionSet.add("OPTIONS");
	    sectionSet.add("OUTCOME");
	    sectionSet.add("OUTCOMES");
	    sectionSet.add("PAD");
	    sectionSet.add("PARP");
	    sectionSet.add("PARTICIPANTS");
	    sectionSet.add("PATIENT");
	    sectionSet.add("PATIENTS");
	    sectionSet.add("PBMC");
	    sectionSet.add("PCI");
	    sectionSet.add("PFS");
	    sectionSet.add("POINT");
	    sectionSet.add("POPF");
	    sectionSet.add("POPULATION");
	    sectionSet.add("PRACTICE");
	    sectionSet.add("PREDICTOR");
	    sectionSet.add("PREDICTORS");
	    sectionSet.add("PREVENTION");
	    sectionSet.add("PROCEDURE");
	    sectionSet.add("PROCEDURES");
	    sectionSet.add("PTDM");
	    sectionSet.add("PTM");
	    sectionSet.add("PURPOSE");
	    sectionSet.add("PURPOSES");
	    sectionSet.add("QUESTION");
	    sectionSet.add("RATIONALE");
	    sectionSet.add("RBCT");
	    sectionSet.add("RECOMMENDATIONS");
	    sectionSet.add("REGISTRATION");
	    sectionSet.add("REGISTRY");
	    sectionSet.add("REHABILITATION");
	    sectionSet.add("RELEVANCE");
	    sectionSet.add("REPORT");
	    sectionSet.add("RESEARCH");
	    sectionSet.add("RESULT");
	    sectionSet.add("RESULTS");
	    sectionSet.add("REVIEW");
	    sectionSet.add("RFO");
	    sectionSet.add("RFS");
	    sectionSet.add("RHR");
	    sectionSet.add("RII");
	    sectionSet.add("RISKS");
	    sectionSet.add("RYGB");
	    sectionSet.add("SABR");
	    sectionSet.add("SAMPLE");
	    sectionSet.add("SBP");
	    sectionSet.add("SELECTION");
	    sectionSet.add("SEM");
	    sectionSet.add("SETTING");
	    sectionSet.add("SETTINGS");
	    sectionSet.add("SHR");
	    sectionSet.add("SIDS");
	    sectionSet.add("SIGNIFICANCE");
	    sectionSet.add("SIHC");
	    sectionSet.add("SLR");
	    sectionSet.add("SMOKING");
	    sectionSet.add("SMR");
	    sectionSet.add("SOURCE");
	    sectionSet.add("SOURCES");
	    sectionSet.add("SPLS");
	    sectionSet.add("SPONSORS");
	    sectionSet.add("SPONSORSHIP");
	    sectionSet.add("SRH");
	    sectionSet.add("SSI");
	    sectionSet.add("STATUS");
	    sectionSet.add("STRATEGY");
	    sectionSet.add("STUDIES");
	    sectionSet.add("STUDY");
	    sectionSet.add("SUBJECTS");
	    sectionSet.add("SUMMARY");
	    sectionSet.add("SURVIVORS");
	    sectionSet.add("SYNTHESIS");
	    sectionSet.add("TBW");
	    sectionSet.add("TECHNIQUES");
	    sectionSet.add("TECHNOLOGY");
	    sectionSet.add("TESTING");
	    sectionSet.add("TFC");
	    sectionSet.add("THERAPY");
	    sectionSet.add("TREATMENT");
	    sectionSet.add("TRIAL");
	    sectionSet.add("TYPE");
	    sectionSet.add("UPE");
	    sectionSet.add("URL");
	    sectionSet.add("UTN");
	    sectionSet.add("VALIDATION");
	    sectionSet.add("VALUES");
	    sectionSet.add("VARIABLE");
	    sectionSet.add("VARIABLES");
	    sectionSet.add("WEIGHT");
	    sectionSet.add("XRT");
	    return sectionSet;
	}

	public BiblioAbstractAnalyzer() {
		createSectionSet();
		sectionMultiset = HashMultiset.create();
	}
	
	public void analyze(RISEntry chunk) {
		String abstractx = chunk.getAbstractString();
		createAndAnalyzeSections(abstractx);
	}

	public void addToMultiset(String section) {
		sectionMultiset.add(section);
	}
	
	public Multiset<String> getSectionMultiset() {
		return sectionMultiset;
	}

	public Set<String> getSectionSet() {
		return sectionSet;
	}

	/** splits abstract at keywords.
	 * 
	 * @param risChunk
	 * @return
	 */
	HtmlDiv createAndAnalyzeSections(String abstractx) {
		if (abstractx == null) return null;
		HtmlDiv sectionList = new HtmlDiv();
		HtmlB b = new HtmlB();
		b.appendChild("ENTRY");
		sectionList.appendChild(b);
		Matcher matcher = ABSTRACT_SECT_PATTERN.matcher(abstractx);
		int start = 0;
		int endKeyword = 0;
		String sectionName = "";
		String sectionBody = "";
		HtmlP section = new HtmlP();
		sectionList.appendChild(section);
		while (matcher.find(start)) {
			endKeyword = matcher.end();
			int startKeyword = matcher.start();
			sectionBody = abstractx.substring(start, startKeyword);
			section.setValue(sectionName+": "+sectionBody);
			// first section might be anonymous
			if (start == 0 && startKeyword != 0) {
				sectionName = _ANONYMOUS;
			} else {
				sectionName = matcher.group(1);
				if (!getSectionSet().contains(sectionName)) {
					System.err.println(">> unknown section "+sectionName);
					getSectionSet().add(sectionName);
				}
				addToMultiset(sectionName);
			}
			
			section = new HtmlP();
			sectionList.appendChild(section);
			section.setClassAttribute(sectionName);
			start = endKeyword;
		}
		sectionBody = abstractx.substring(endKeyword);
		section.setValue(sectionName+": "+sectionBody);
		return sectionList;
	}

	HtmlDiv createAbstractList(RISParser risParser) {
		HtmlDiv abstractList = new HtmlDiv();
		abstractList.setClassAttribute(RISParser.ABSTRACT_LIST);
		risParser.getEntries();
		for (RISEntry chunk : risParser.entryList) {
			String abstractx = chunk.getAbstractString();
			HtmlDiv abstractDiv = createAndAnalyzeSections(abstractx);
			if (abstractx != null) {
				abstractList.appendChild(abstractDiv);
			}
		}
		return abstractList;
	}

}
