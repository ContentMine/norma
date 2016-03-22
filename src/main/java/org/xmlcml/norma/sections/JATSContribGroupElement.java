package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSContribGroupElement extends JATSElement {

	private static final Logger LOG = Logger.getLogger(JATSContribGroupElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	/**
			<contrib-group>
				<contrib contrib-type="author">
					<name>
						<surname>Haddow</surname>
						<given-names>Andrew D.</given-names>
					</name>
					<xref ref-type="aff" rid="aff1">
						<sup>1</sup>
					</xref>
					<xref ref-type="corresp" rid="cor1">
						<sup>&#x0002a;</sup>
					</xref>
				</contrib>
				<contrib contrib-type="author">
				...
				</contrib>
			</contribGroup>
			
	 */
	final static String TAG = "contribGroup";

	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.CONTRIB,
	});

	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}
	
	private List<JATSContribElement> contribList;
	private String contribType;

	public JATSContribGroupElement(Element element) {
		super(element);
	}
	
	protected void applyNonXMLSemantics() {
		contribList = new ArrayList<JATSContribElement>();
		contribType = null;
		List<Element> contribElements = XMLUtil.getQueryElements(this, "*[local-name()='"+JATSContribElement.TAG+"']");
		boolean first = true;
		for (Element element : contribElements) {
			JATSContribElement contribElement = (JATSContribElement)element;
			String contribType1 = contribElement.getContribType();
			if (first) {
				if (contribType1 != null) {
					contribType = contribType1;
				}
				first = false;
			} else if (contribType == null || (contribType != contribType1)) {
				LOG.warn("inconsistent contribTypes " + contribType1 + " != " + contribType);
				contribType = null;
			}
			contribList.add((JATSContribElement)element);
		}
	}

	
}
