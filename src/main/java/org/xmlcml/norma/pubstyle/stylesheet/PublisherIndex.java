package org.xmlcml.norma.pubstyle.stylesheet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

/** typical entry
 *     
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
public class PublisherIndex {

	private static final Logger LOG = Logger.getLogger(PublisherIndex.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private List<File> htmlXslFiles;
	private Map<String, PublisherSelector> selectorByPublisher;
	private Map<String, PublisherSelector> selectorByPrefix;
	
	public PublisherIndex() {
		
	}

	public PublisherIndex(File pubstyleDir) {
		this.readHtmlXslDirectory(pubstyleDir);
	}

	public List<File> readHtmlXslDirectory(File pubstyleDir) {
		if (pubstyleDir == null || !pubstyleDir.exists()) {
			throw new RuntimeException("Cannot find/read pubstyle Dir: "+pubstyleDir);
		}
		htmlXslFiles = new ArrayList<File>(FileUtils.listFiles(
				pubstyleDir, new NameFileFilter("toHtml.xsl") , new WildcardFileFilter("*")));
		createIndex();
		return htmlXslFiles;
	}
	
	private void createIndex() {
		ensureIndexes();
		for (File htmlXsl : htmlXslFiles) {
			Element xslElement = XMLUtil.parseQuietlyToDocument(htmlXsl).getRootElement();
			String publisher = XMLUtil.getSingleValue(xslElement, PublisherSelector.GET_PUBLISHER);
			if (publisher != null) {
				PublisherSelector publisherSelector = new PublisherSelector(xslElement);
				if (selectorByPublisher.containsKey(publisher)) {
					throw new RuntimeException("Duplicate publisher: "+publisher);
				}
				selectorByPublisher.put(publisher, publisherSelector);
				String prefix = publisherSelector.getOrCreatePrefix();
				if (prefix != null) {
					if (selectorByPrefix.containsKey(prefix)) {
						throw new RuntimeException("Duplicate prefix: "+prefix);
					}
					selectorByPrefix.put(prefix, publisherSelector);
				} else {
					LOG.warn("No prefix in XSL: "+publisherSelector);
				}
			} else {
				LOG.debug("no publisher in XSL: "+htmlXsl+"; please add");
			}
		}
	}

	public Map<String, PublisherSelector> getSelectorByPublisherMap() {
		ensureIndexes();
		return selectorByPublisher;
	}
	private void ensureIndexes() {
		if (selectorByPublisher ==  null) {
			selectorByPublisher = new HashMap<String, PublisherSelector>();
		}
		if (selectorByPrefix ==  null) {
			selectorByPrefix = new HashMap<String, PublisherSelector>();
		}
	}
	
	public PublisherSelector getPublisherSelectorByPublisher(String publisherName) {
		ensureIndexes();
		return selectorByPublisher.get(publisherName);
	}
	
	public PublisherSelector getPublisherSelectorByPrefix(String publisherName) {
		ensureIndexes();
		return selectorByPrefix.get(publisherName);
	}

	public PublisherSelector getPublisherSelectorForFile(File fulltextXhtmlFile) {
		Element fulltextElement = XMLUtil.parseQuietlyToDocument(fulltextXhtmlFile).getRootElement();
		return getPublisherSelectorForElement(fulltextElement);
	}

	private PublisherSelector getPublisherSelectorForElement(Element fulltextElement) {
		ensureIndexes();
		Collection<PublisherSelector> publisherSelectors = selectorByPrefix.values();
		for (PublisherSelector publisherSelector : publisherSelectors) {
			if (publisherSelector.matches(fulltextElement)) {
				return publisherSelector;
			}
		}
		return null;
	}

	public List<PublisherSelector> getPublisherSelectors() {
		ensureIndexes();
		return new ArrayList<PublisherSelector>(selectorByPublisher.values());
		
		
	}
	
	
	
}
