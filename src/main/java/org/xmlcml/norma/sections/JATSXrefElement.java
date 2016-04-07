package org.xmlcml.norma.sections;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import nu.xom.Element;

public class JATSXrefElement extends JATSElement {

	private static final String REF_TYPE = "ref-type";
	private static final String RID = "rid";

	/**
	<xref ref-type="aff" rid="aff1">
		<sup>1</sup>
	</xref>
	 */
	private static final Logger LOG = Logger.getLogger(JATSXrefElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String TAG = "xref";
	
	private String refType; 
	private String rid; 

	public JATSXrefElement(Element element) {
		super(element);
		this.refType = element.getAttributeValue(REF_TYPE);
		this.rid     = element.getAttributeValue(RID); 
	}
	
	

}
