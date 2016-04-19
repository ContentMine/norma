package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSPubDateElement extends JATSDateElement {

	private static final String PUB_TYPE = "pub-type";
	/**
	<pub-date pub-type="epub">
		<day>28</day>
		<month>2</month>
		<year>2012</year>
	</pub-date>
	 */
	private static final Logger LOG = Logger.getLogger(JATSPubDateElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	static final String TAG = "pub-date";
	
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.SEASON,
			JATSSpanFactory.YEAR,
			JATSSpanFactory.DAY,
			JATSSpanFactory.MONTH,
			JATSSpanFactory.STRING_DATE,
	});
	
	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}
	public JATSPubDateElement(Element element) {
		super(element);
		this.pubType = element.getAttributeValue(PUB_TYPE);
	}

}
