package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSElementCitationElement extends JATSElement {

	/**
	<element-citation publication-type="journal">
		<person-group person-group-type="author">
			<name>
				<surname>Hammon</surname>
				<given-names>WM</given-names>
			</name>
			<name>
				<surname>Schrack</surname>
				<given-names>WD</given-names>
				<suffix>Jr</suffix>
			</name>
			<name>
				<surname>Sather</surname>
				<given-names>GE</given-names>
			</name>
		</person-group>
		<year>1958</year>
		<article-title>Serological survey for a arthropod-borne virus
			infections in the Philippines.</article-title>
		<source>Am J Trop Med Hyg</source>
		<volume>7</volume>
		<fpage>323</fpage>
		<lpage>328</lpage>
		<pub-id pub-id-type="pmid">13533740</pub-id>
	 */
	
	
	private static final Logger LOG = Logger.getLogger(JATSElementCitationElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.PERSON_GROUP,
			JATSDivFactory.ARTICLE_TITLE,
			JATSDivFactory.SOURCE,
			JATSDivFactory.DATE_IN_CITATION,
			JATSSpanFactory.CHAPTER_TITLE,
			JATSSpanFactory.EDITION,
			JATSSpanFactory.CONF_LOC,
			JATSSpanFactory.CONF_DATE,
			JATSSpanFactory.CONF_NAME,
			JATSSpanFactory.EMAIL,
			JATSSpanFactory.ISSUE_ID,
			JATSSpanFactory.ELOCATION_ID,
			JATSSpanFactory.MONTH,
			JATSSpanFactory.DAY,
			JATSSpanFactory.YEAR,
			JATSSpanFactory.VOLUME,
			JATSSpanFactory.FPAGE,
			JATSSpanFactory.LPAGE,
			JATSSpanFactory.PUB_ID,
			JATSSpanFactory.PUBLISHER_LOC,
			JATSSpanFactory.PUBLISHER_NAME,
			JATSSpanFactory.EXT_LINK,
			JATSSpanFactory.PAGE_RANGE,
			JATSSpanFactory.SERIES,
			JATSSpanFactory.SEASON,
			JATSDivFactory.COMMENT,
			JATSDivFactory.NAME,
			JATSSpanFactory.OBJECT_ID,
			JATSSpanFactory.ISSUE,
			JATSSpanFactory.ETAL,
			JATSSpanFactory.COLLAB,
			JATSSpanFactory.SIZE,
			JATSSpanFactory.TRANS_SOURCE,
	});
	
	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	
	static final String TAG = "element-citation";

	JATSElement personGroup;
	private String articleTitle;
	private String source;
	private String year;
	private String volume;
	private String fpage;
	private String lpage;
	private String pmid;
	private String pmcid;
	private String publisherLoc;
	private String publisherName;
	private String issue;
	private String collab;
	private String size;

	// <pub-id pub-id-type="pmid">13533740</pub-id>
	String getPMCID() {
		return pmcid;
	}
	public JATSElement getPersonGroup() {
		return personGroup;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public String getYear() {
		return year;
	}
	public String getVolume() {
		return volume;
	}
	public String getFpage() {
		return fpage;
	}
	public String getLpage() {
		return lpage;
	}
	public String getPmid() {
		return pmid;
	}
	public String getPmcid() {
		return pmcid;
	}
	public String getPublisherLoc() {
		return publisherLoc;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public String getIssue() {
		return issue;
	}
	public String getCollab() {
		return collab;
	}
	public String getSize() {
		return size;
	}
	public String getSource() {
		return source;
	}
	String getPMID() {
		return pmid;
	}
	public JATSElementCitationElement(Element element) {
		super(element);
	}
	
	protected void applyNonXMLSemantics() {
//		LOG.debug(this.toXML());
		this.personGroup = this.getSingleChild(JATSDivFactory.PERSON_GROUP);
		this.articleTitle =  this.getSingleValueByClassAttributeValue(JATSDivFactory.ARTICLE_TITLE);
		this.source =  this.getSingleValueByClassAttributeValue(JATSDivFactory.SOURCE);
		this.year = this.getSingleValueByClassAttributeValue(JATSSpanFactory.YEAR);
		this.volume = this.getSingleValueByClassAttributeValue(JATSSpanFactory.VOLUME);
		this.fpage = this.getSingleValueByClassAttributeValue(JATSSpanFactory.FPAGE);
		this.lpage = this.getSingleValueByClassAttributeValue(JATSSpanFactory.LPAGE);
		this.pmid = XMLUtil.getSingleValue(this, "*[@class='"+JATSSpanFactory.PUB_ID+"' and @"+JATSArticleIdElement.PUB_ID_TYPE+"='"+JATSArticleIdElement.PMID+"']");
		this.pmcid = XMLUtil.getSingleValue(this, "*[@class='"+JATSSpanFactory.PUB_ID+"' and @"+JATSArticleIdElement.PUB_ID_TYPE+"='"+JATSArticleIdElement.PMCID+"']");
		this.publisherLoc = this.getSingleValueByClassAttributeValue(JATSSpanFactory.PUBLISHER_LOC);
		this.publisherName = this.getSingleValueByClassAttributeValue(JATSSpanFactory.PUBLISHER_NAME);
		this.issue = this.getSingleValueByClassAttributeValue(JATSSpanFactory.ISSUE);
		this.collab = this.getSingleValueByClassAttributeValue(JATSSpanFactory.COLLAB);
		this.size = this.getSingleValueByClassAttributeValue(JATSSpanFactory.SIZE);
	}


	

}
