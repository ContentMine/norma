package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.JodaDate;

import nu.xom.Element;

public class JATSDateElement extends JATSElement {

	/**
	<date date-type="received">
		<day>15</day>
		<month>8</month>
		<year>2011</year>
	</date>
	 */
	private static final Logger LOG = Logger.getLogger(JATSDateElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	static final String TAG = "date";
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.DAY,
			JATSSpanFactory.MONTH,
			JATSSpanFactory.YEAR,
	});
	
	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	private String day;
	private String month;
	private String year;
	private JodaDate date; // created from day, month, year
	protected String pubType;

	public JATSDateElement(Element element) {
		super(element);
	}

	protected void applyNonXMLSemantics() {
		day = this.getSingleChildValue(JATSSpanFactory.DAY);
		month = this.getSingleChildValue(JATSSpanFactory.MONTH);
		year = this.getSingleChildValue(JATSSpanFactory.YEAR);
//		JodaDate.parseDate(date, format);
		
	}


	

}
