package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSHistoryElement extends JATSElement {

	/**
	<history>
		<date date-type="received">
			<day>15</day>
			<month>8</month>
			<year>2011</year>
		</date>
		<date date-type="accepted">
			<day>3</day>
			<month>12</month>
			<year>2011</year>
		</date>
	</history>
	 */
	private static final Logger LOG = Logger.getLogger(JATSHistoryElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	static final String TAG = "history";
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.DATE,
	});

	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	private List<JATSDateElement> dateList;

	public JATSHistoryElement(Element element) {
		super(element);
	}

	protected void applyNonXMLSemantics() {
		dateList = new ArrayList<JATSDateElement>();
		List<Element> dateElements = XMLUtil.getQueryElements(this, "*[local-name()='"+JATSDateElement.TAG+"']");
		for (Element element : dateElements) {
			dateList.add((JATSDateElement)element);
		}
	}


}
