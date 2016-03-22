package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSPersonGroupElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSPersonGroupElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	final static String TAG = "person-group";

	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.NAME,
			JATSSpanFactory.ETAL,
	});

	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	private List<JATSPersonGroupElement> personGroupList;
	private String etal;
	
	public JATSPersonGroupElement(Element element) {
		super(element);
	}
	
	public List<JATSPersonGroupElement> getPersonGroupList() {
		return personGroupList;
	}

	protected void applyNonXMLSemantics() {
		personGroupList = new ArrayList<JATSPersonGroupElement>();
		List<Element> personGroupElements = XMLUtil.getQueryElements(this, "*[local-name()='"+JATSPersonGroupElement.TAG+"']");
		for (Element element : personGroupElements) {
			personGroupList.add((JATSPersonGroupElement)element);
		}
		etal = this.getSingleChildValue(JATSSpanFactory.ETAL);
	}


}
