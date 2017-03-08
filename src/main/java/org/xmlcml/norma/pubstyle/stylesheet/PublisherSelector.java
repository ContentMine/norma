package org.xmlcml.norma.pubstyle.stylesheet;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;
import nu.xom.Node;

/** selects a publisher ffrom a stylesheet
 * 
 * typical stylesheet contents
    <xsl:variable name="publisher">The Royal Society</xsl:variable>
    <xsl:variable name="prefix">10.1098</xsl:variable>
	<xsl:variable name="publisherSelector">
	    meta[
	      (@name='DC.Publisher' and .='The Royal Society') or 
	      (@name='DC.Identifier' and contains(.,'10.1098/'))
	    ]</xsl:variable>


 * @author pm286
 *
 */
public class PublisherSelector {

	private static final Logger LOG = Logger.getLogger(PublisherSelector.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String GET_PUBLISHER = "/*/*[local-name()='variable' and @name='publisher']/text()";
	public static final String GET_PREFIX = "/*/*[local-name()='variable' and @name='prefix']/text()";
	public static final String GET_XPATH = "/*/*[local-name()='variable' and @name='publisherSelector']/text()";

	private Element xslElement;
	private String publisher;
	private String prefix;
	private String xslPath;

	public PublisherSelector(Element xslElement) {
		this.xslElement = xslElement;
		getOrCreatePrefix();
		getOrCreatePublisher();
		getOrCreateXslPath();
	}
	
	public String getOrCreatePublisher() {
		if (publisher == null) {
			publisher = XMLUtil.getSingleValue(xslElement, PublisherSelector.GET_PUBLISHER);
		}
		return publisher;
	}

	public String getOrCreatePrefix() {
		if (prefix == null) {
			prefix = XMLUtil.getSingleValue(xslElement, PublisherSelector.GET_PREFIX);
		}
		return prefix;
	}
	
	public String getOrCreateXslPath() {
		if (xslPath == null) {
			xslPath = XMLUtil.getSingleValue(xslElement, PublisherSelector.GET_XPATH);
			LOG.trace(xslElement.toXML()+" ********** xsl:path: "+xslPath);
		}
		return xslPath;
	}

	public boolean matches(Element fulltextXml) {
		getOrCreateXslPath();
		List<Node> nodes = XMLUtil.getQueryNodes(fulltextXml, this.xslPath);
		return nodes.size() > 0;
	}
	
	public String toString() {
		return prefix+": "+publisher;
	}

}
