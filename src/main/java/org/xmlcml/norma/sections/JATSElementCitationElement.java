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
	
	private static final String PMID = "pmid";
	private static final String PMCID = "pmcid";
	private static final String PUB_ID_TYPE = "pub-id-type";
	
	private static final Logger LOG = Logger.getLogger(JATSElementCitationElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
			JATSDivFactory.PERSON_GROUP,
			JATSDivFactory.ARTICLE_TITLE,
			JATSDivFactory.SOURCE,
			JATSSpanFactory.YEAR,
			JATSSpanFactory.VOLUME,
			JATSSpanFactory.FPAGE,
			JATSSpanFactory.LPAGE,
			JATSSpanFactory.PUB_ID,
			JATSSpanFactory.PUBLISHER_LOC,
			JATSSpanFactory.PUBLISHER_NAME,
			JATSDivFactory.COMMENT,
			JATSSpanFactory.ISSUE,
			JATSSpanFactory.COLLAB,
			JATSSpanFactory.SIZE,
	});
	
	@Override
	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}

	
	static final String TAG = "element-citation";

	JATSElement personGroup;
	JATSElement articleTitle;
	JATSElement source;
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
	public JATSElement getArticleTitle() {
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
	public void setSource(JATSElement source) {
		this.source = source;
	}
	String getPMID() {
		return pmid;
	}
	public JATSElementCitationElement(Element element) {
		super(element);
	}
	
	protected void applyNonXMLSemantics() {
		this.personGroup = this.getSingleChild(JATSDivFactory.PERSON_GROUP);
		this.articleTitle =  this.getSingleChild(JATSDivFactory.ARTICLE_TITLE);
		this.source =  this.getSingleChild(JATSDivFactory.SOURCE);
		this.year = this.getSingleChildValue(JATSSpanFactory.YEAR);
		this.volume = this.getSingleChildValue(JATSSpanFactory.VOLUME);
		this.fpage = this.getSingleChildValue(JATSSpanFactory.FPAGE);
		this.lpage = this.getSingleChildValue(JATSSpanFactory.LPAGE);
		this.pmid = XMLUtil.getSingleValue(this, "*[local-name()='"+JATSSpanFactory.PUB_ID+"' and @"+PUB_ID_TYPE+"='"+PMCID+"']");
		this.pmcid = XMLUtil.getSingleValue(this, "*[local-name()='"+JATSSpanFactory.PUB_ID+"' and @"+PUB_ID_TYPE+"='"+PMCID+"']");
		this.publisherLoc = this.getSingleChildValue(JATSSpanFactory.PUBLISHER_LOC);
		this.publisherName = this.getSingleChildValue(JATSSpanFactory.PUBLISHER_NAME);
		this.issue = this.getSingleChildValue(JATSSpanFactory.ISSUE);
		this.collab = this.getSingleChildValue(JATSSpanFactory.COLLAB);
		this.size = this.getSingleChildValue(JATSSpanFactory.SIZE);
	}


	

}
