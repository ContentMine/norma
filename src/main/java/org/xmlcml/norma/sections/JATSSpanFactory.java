package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.html.HtmlSpan;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSSpanFactory {

	private static final Logger LOG = Logger.getLogger(JATSSpanFactory.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}  
	
	public final static String ACCESS_DATE = "access-date";
	public final static String ALT_TEXT = "alt-text";
	public final static String ANNOTATION = "annotation";
	public final static String ARTICLE_ID = "article-id";
	public final static String ATTRIB = "attrib";
	public final static String AWARD_ID = "award-id";
	public final static String BOLD = "bold";
	public final static String BREAK = "break";
	public final static String CHAPTER_TITLE = "chapter-title";
	public final static String COLLAB = "collab";
	public final static String CONF_DATE = "conf-date";
	public final static String CONF_LOC = "conf-loc";
	public final static String CONF_NAME = "conf-name";
	public final static String CONF_SPONSOR = "conf-sponsor";
	public final static String CONTRIB_ID = "contrib-id";
	public final static String COPYRIGHT_HOLDER = "copyright-holder";
	public final static String COPYRIGHT_STATEMENT = "copyright-statement";
	public final static String COPYRIGHT_YEAR = "copyright-year";
	public final static String COUNT = "count";
	public final static String COUNTRY = "country";
	public final static String DAY = "day";
	public final static String DEGREES = "degrees";
	public final static String DISP_FORMULA_GROUP = "disp-formula-group";
	public final static String EDITION = "edition";
	public final static String ELOCATION_ID = "elocation-id";
	public final static String EMAIL = "email";
	public final static String EQUATION_COUNT = "equation-count";
	public final static String ETAL = "etal";
	public final static String EXT_LINK = "ext-link";
	public final static String FAX = "fax";
	public final static String FIG_COUNT = "fig-count";
	public final static String FPAGE = "fpage";
	public final static String FUNDING_SOURCE = "funding-source";
	public final static String GIVEN_NAMES = "given-names";
	public final static String GRAPHICS = "graphics";
	public final static String HR = "hr";
	public final static String INLINE_GRAPHIC = "inline-graphic";
	public final static String INLINE_SUPPLEMENTAL_MATERIAL = "inline-supplementary-material";
	public final static String INSTITUTION = "institution";
	public final static String INSTITUTION_ID = "institution-id";
	public final static String ISBN = "isbn";
	public final static String ISSN = "issn";
	public final static String ISSN_L = "issn-l";
	public final static String ISSUE = "issue";
	public final static String ISSUE_ID = "issue-id";
	public final static String ISSUE_TITLE = "issue-title";
	public final static String ITALIC = "italic";
	public final static String JOURNAL_ID = "journal-id";
	public final static String JOURNAL_TITLE = "journal-title";
	public final static String KWD = "kwd";
	public final static String LABEL = "label";
	public final static String LPAGE = "lpage";
	public final static String MALIGNGROUP = "maligngroup";
	public final static String MALIGNMARK = "malignmark";
	public final static String META_NAME = "meta-name";
	public final static String META_VALUE= "meta-value";
	public final static String MI = "mi";
	public final static String MN = "mn";
	public final static String MO = "mo";
	public final static String MONOSPACE = "monospace";
	public final static String MONTH = "month";
	public final static String MROOT = "mroot";
	public final static String MSPACE = "mspace";
	public final static String MTEXT = "mtext";
	public final static String NAME = "name";
	public final static String NOTE = "note";
	public final static String OBJECT_ID = "object-id";
	public final static String ON_BEHALF_OF = "on-behalf-of";
	public final static String PAGE_COUNT = "page-count";
	public final static String PAGE_RANGE = "page-range";
	public final static String P = "p";
	public final static String PATENT = "patent";
	public final static String PHONE = "phone";
	public final static String PREFIX = "prefix";
	public final static String PUBLISHER_LOC = "publisher-loc";
	public final static String PUBLISHER_NAME = "publisher-name";
	public final static String PUB_ID = "pub-id";
	public final static String REF_COUNT = "ref-count";
	public final static String ROLE = "role";
	public final static String ROMAN = "roman";
	public final static String SANS_SERIF = "sans-serif";
	public final static String SC = "sc";
	public final static String SEASON = "season";
	public final static String SELF_URI = "self-uri";
	public final static String SERIES = "series";
	public final static String SERIES_TITLE = "series-title";
	public final static String SIZE = "size";
	public final static String STRIKE = "strike";
	public final static String STRING_DATE = "string-date";
	public final static String STYLED_CONTENT = "styled-content";
	public final static String SUBJECT = "subject";
	public final static String SUP = "sup";
	public final static String SUFFIX = "suffix";
	public final static String SURNAME = "surname";
	public final static String TABLE_COUNT = "table-count";
	public final static String TARGET = "target";
	public final static String TERM = "term";
	public final static String TEX_MATH = "tex-math";
	public final static String TRANS_SOURCE = "trans-source";
	public final static String TRANS_TITLE = "trans-title";
	public final static String UNDERLINE = "underline";
	public final static String URI = "uri";
	public final static String VOLUME = "volume";
	public final static String WORD_COUNT = "word-count";
	public final static String XREF = "xref";
	public final static String YEAR = "year";

	public final static List<String> SPAN_LIST = Arrays.asList(
			new String[] {
					
					ACCESS_DATE,
					ALT_TEXT,
					ANNOTATION,
//					ARTICLE_ID,
					ATTRIB,
					AWARD_ID,
					BOLD,
					BREAK,
					CHAPTER_TITLE,
					COLLAB,
					CONF_DATE,
					CONF_LOC,
					CONF_NAME,
					CONF_SPONSOR,
					CONTRIB_ID,
					COPYRIGHT_HOLDER,
					COPYRIGHT_STATEMENT,
					COPYRIGHT_YEAR,
					COUNT,
					COUNTRY,
					DAY,
					DEGREES,
					DISP_FORMULA_GROUP,
					EDITION,
					ELOCATION_ID,
					EMAIL,
					EQUATION_COUNT,
					ETAL,
					EXT_LINK,
					FAX,
					FIG_COUNT,
					FPAGE,
					FUNDING_SOURCE,
					GIVEN_NAMES,
					GRAPHICS,
					HR,
					INLINE_GRAPHIC,
					INLINE_SUPPLEMENTAL_MATERIAL,
					INSTITUTION,
					INSTITUTION_ID,
					ISBN,
					ISSN,
					ISSN_L,
					ISSUE,
					ISSUE_ID,
					ISSUE_TITLE,
					ITALIC,
					JOURNAL_ID,
					JOURNAL_TITLE,
					KWD,
					LABEL,
					LPAGE,
					MALIGNGROUP,
					MALIGNMARK,
					META_NAME,
					META_VALUE,
					MI,
					MN,
					MO,
					MONOSPACE,
					MONTH,
					MROOT,
					MSPACE,
					MTEXT,
					NOTE,
					OBJECT_ID,
					ON_BEHALF_OF,
					PAGE_COUNT,
					PAGE_RANGE,
					PATENT,
					PHONE,
					PREFIX,
					PUBLISHER_LOC,
					PUBLISHER_NAME,
					PUB_ID,
					REF_COUNT,
					ROLE,
					ROMAN,
					SANS_SERIF,
					SC,
					SEASON,
					SELF_URI,
					SERIES,
					SERIES_TITLE,
					SIZE,
					STRIKE,
					STRING_DATE,
					STYLED_CONTENT,
					SUBJECT,
					SUP,
					SUFFIX,
					SURNAME,
					TABLE_COUNT,
					TARGET,
					TERM,
					TEX_MATH,
					TRANS_SOURCE,
					TRANS_TITLE,
					UNDERLINE,
					URI,
					VOLUME,
					WORD_COUNT,
					YEAR,
			}
		);


	/** these allow nested spans
	 * 
	 */
	public final static List<String> SPAN_SPAN_LIST = Arrays.asList(
			new String[] {
			}
		);


	private Element jatsElement;
	private JATSFactory mainFactory;

	public JATSSpanFactory(JATSFactory mainFactory) {
		this.mainFactory = mainFactory;
	}
	
	JATSSpanFactory setElement(Element element) {
		this.jatsElement = element;
		String tag = element.getLocalName();
		if (SPAN_SPAN_LIST.contains(tag)) {
			// OK
		} else if (element.getChildElements().size() > 0 ) {
			LOG.trace("SPAN element "+element.getLocalName()+" has children "+element.toXML());
		}

		return this;
	}

	public static boolean isSpan(String tag) {
		return SPAN_LIST.contains(tag) || SPAN_SPAN_LIST.contains(tag);
	}

	public HtmlSpan createAndRecurse() {
		HtmlSpan spanElement = new HtmlSpan();
		XMLUtil.copyAttributes(jatsElement, spanElement);
		String tag = jatsElement.getLocalName();
		spanElement.setClassAttribute(tag);
//		if (JATSSpanFactory.PUB_ID.equals(tag)) {
//			LOG.info("+++"+tag+" "+spanElement.toXML());
//		}
		mainFactory.processChildren(jatsElement, spanElement);
		return spanElement;
	}



}
