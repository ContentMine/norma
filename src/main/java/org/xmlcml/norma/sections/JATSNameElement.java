package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSNameElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSNameElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	static final String TAG = "name";

	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.SURNAME,
			JATSSpanFactory.GIVEN_NAMES,
			JATSSpanFactory.PREFIX,
			JATSSpanFactory.SUFFIX,
	});

	private String surname;
	private String givenNames;
	private String suffix;
	
	public JATSNameElement(Element element) {
		super(element);
	}
	
	public String getSurname() {
		return surname;
	}
	
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	public String getGivenNames() {
		return givenNames;
	}

	public String getSuffix() {
		return suffix;
	}

	protected void applyNonXMLSemantics() {
		this.surname = this.getSingleChildValue(JATSSurnameElement.TAG);
		this.givenNames = this.getSingleChildValue(JATSGivenNamesElement.TAG);
		this.suffix = this.getSingleChildValue(JATSSpanFactory.LABEL);
	}


}
