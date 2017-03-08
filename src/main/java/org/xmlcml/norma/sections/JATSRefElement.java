package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSRefElement extends JATSElement {

	static final Logger LOG = Logger.getLogger(JATSRefElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	static final String TAG = "ref";
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.LABEL,
			JATSDivFactory.ELEMENT_CITATION,
	});
	
	private JATSElementCitationElement elementCitation;
	private String label;

	public JATSRefElement(Element element) {
		super(element);
	}
	
	public String getPMID() {
		return elementCitation == null ? null : elementCitation.getPMID();
	}

	public String getPMCID() {
		return elementCitation == null ? null : elementCitation.getPMCID();
	}
	
	protected void applyNonXMLSemantics() {
		label = getSingleChildValue(JATSSpanFactory.LABEL);
		elementCitation = (JATSElementCitationElement) getSingleChild(JATSElementCitationElement.TAG);
	}


}
