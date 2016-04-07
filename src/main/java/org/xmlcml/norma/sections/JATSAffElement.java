package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSAffElement extends JATSElement {

	/**
	<aff id="aff1">
		<label>1</label>
		<addr-line>Institute for Human Infections and Immunity, Center for
			Tropical Diseases, Department of Pathology, University of Texas
			Medical Branch, Galveston, Texas, United States of America</addr-line>
	</aff>
	 */
	private static final Logger LOG = Logger.getLogger(JATSAffElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static String TAG = "aff";
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSSpanFactory.LABEL,
			JATSSpanFactory.SUP,
			JATSDivFactory.ADDR_LINE,
			JATSDivFactory.NAMED_CONTENT,
			JATSSpanFactory.EXT_LINK,
			JATSSpanFactory.INSTITUTION,
			JATSSpanFactory.COUNTRY,
			JATSSpanFactory.EMAIL,
			JATSSpanFactory.ITALIC,
			JATSSpanFactory.BOLD,
	});

	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	private String label;
	private String addrLine;
	
	public JATSAffElement(Element element) {
		super(element);
	}
	
	protected void applyNonXMLSemantics() {
		label = this.getSingleChildValue(JATSSpanFactory.LABEL);
		addrLine = this.getSingleChildValue(JATSDivFactory.ADDR_LINE);
	}

}
