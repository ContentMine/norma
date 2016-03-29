package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import nu.xom.Element;

public class JATSContribElement extends JATSElement {

	private static final String ROLE = "role";
	private static final String CONTRIB_TYPE = "contrib-type";

	/**
	<contrib contrib-type="author">
		<name>
			<surname>Huy</surname>
			<given-names>Rekol</given-names>
		</name>
		<xref ref-type="aff" rid="aff3">
			<sup>3</sup>
		</xref>
		<role>Editor</role>
	</contrib>
			
	 */
	final static String TAG = "contrib";

	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.NAME,
			JATSDivFactory.XREF,
			JATSSpanFactory.ROLE,
			JATSSpanFactory.EMAIL,
			JATSSpanFactory.URI,
			JATSSpanFactory.CONTRIB_ID,
			JATSSpanFactory.DEGREES,
			JATSSpanFactory.CONF_DATE,
			JATSDivFactory.AFF,
			JATSDivFactory.BIO,
			JATSSpanFactory.COLLAB,
			JATSSpanFactory.EDITION,
			JATSDivFactory.ADDRESS,
	});

	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	
	private JATSNameElement name;
	private JATSXrefElement xref;
	private String contribType;
	private String role;
	

	public JATSContribElement(Element element) {
		super(element);
		this.contribType = element.getAttributeValue(CONTRIB_TYPE);
		this.role = element.getAttributeValue(ROLE);
	}

	public String getContribType() {
		return contribType;
	}
}
