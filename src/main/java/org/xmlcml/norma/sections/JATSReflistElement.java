package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSReflistElement extends JATSElement {

	/**
		<ref-list>
			<title>References</title>
			<ref id="pntd.0001477-Thiel1">
		 */
	private static final Logger LOG = Logger.getLogger(JATSReflistElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static String TAG = "ref-list";
	
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
		JATSDivFactory.TITLE,
		JATSDivFactory.REF,
	});

	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}
	
	private String title;
	private List<JATSRefElement> refList;
	private List<String> pmidList;
	private List<String> pmcidList;
	
	public JATSReflistElement(Element element) {
		super(element);
	}

	protected void applyNonXMLSemantics() {
		title = getSingleChildValue(JATSTitleElement.TAG);
		refList = new ArrayList<JATSRefElement>();
		List<Element> refs = XMLUtil.getQueryElements(this, "*[local-name()='"+JATSRefElement.TAG+"']");
		for (Element ref : refs) {
			refList.add((JATSRefElement)ref);
		}
	}

	public String getTitle() {
		return title;
	}

	public List<JATSRefElement> getRefList() {
		return refList;
	}

	public List<String> getNonNullPMIDList() {
		pmidList = new ArrayList<String>();
		for (JATSRefElement ref : refList) {
			String pmid = ref.getPMID();
			if (pmid != null) {
				pmidList.add(pmid);
			}
		}
		return pmidList;
	}

	public List<String> getNonNullPMCIDList() {
		pmcidList = new ArrayList<String>();
		for (JATSRefElement ref : refList) {
			String pmcid = ref.getPMCID();
			if (pmcid != null) {
				pmcidList.add(pmcid);
			}
		}
		return pmcidList;
	}

}
