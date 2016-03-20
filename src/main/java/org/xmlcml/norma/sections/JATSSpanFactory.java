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
	
	private static final String TAG = "span";

public final static String ATTRIB = "attrib";
public final static String CHAPTER_TITLE = "chapter-title";
public final static String CONF_DATE = "conf-date";
public final static String CONF_LOC = "conf-loc";
public final static String CONF_NAME = "conf-name";
public final static String CONTRIB_ID = "contrib-id";
public final static String COUNT = "count";
public final static String DEGREES = "degrees";
public final static String INSTITUTION_ID = "institution-id";
public final static String ISSUE_ID = "issue-id";
public final static String MONOSPACE = "monospace";
public final static String PHONE = "phone";
public final static String SEASON = "season";
public final static String SERIES_TITLE = "series-title";
public final static String STRING_DATA = "string-date";
public final static String TEX_MATH = "tex-math";
public final static String TRANS_SOURCE = "trans-source";
public final static String URI = "uri";
public final static String ISSN_L = "issn-l";
	
	public static final String ARTICLE_ID           = "article-id";
	public static final String COLLAB               = "collab";
	public static final String COPYRIGHT_YEAR       = "copyright-year";
	public static final String COPYRIGHT_STATEMENT  = "copyright-statement";
	public static final String ELOCATION_ID         = "elocation-id";
	public static final String EMAIL                = "email";
	public static final String ETAL                 = "etal";
	public static final String FPAGE                = "fpage";
	public static final String GIVEN_NAMES          = "given-names";
	public static final String GRAPHICS             = "graphics";
	public static final String ISSUE                = "issue";
	public static final String ISSN                 = "issn";
	public static final String JOURNAL_ID           = "journal-id";
	public static final String JOURNAL_TITLE        = "journal-title";
	public static final String LABEL                = "label";
	public static final String LPAGE                = "lpage";
	public static final String NAME                 = "name";
	public static final String OBJECT_ID            = "object-id";
	public static final String PAGE_COUNT           = "page-count";
	public static final String PUB_ID               = "pub-id";
	public static final String PUBLISHER_LOC        = "publisher-loc";
	public static final String PUBLISHER_NAME       = "publisher-name";
	public static final String ROLE                 = "role";
	public static final String SIZE                 = "size";
//	public static final String SOURCE               = "source";
	public static final String SUBJECT              = "subject";
	public static final String SUFFIX               = "suffix";
	public static final String SURNAME              = "surname";
	public static final String VOLUME               = "volume";
	public static final String XREF                 = "xref";
	
	public static final String DAY                  = "day";
	public static final String MONTH                = "month";
	public static final String YEAR                 = "year";

	public static final String EXT_LINK                 = "ext-link";
	public static final String ISSUE_TITLE                 = "issue-title";
	public static final String KWD                 = "kwd";

	public static final String COPYRIGHT_HOLDER                 = "copyright-holder";
	public static final String COUNTRY                 = "country";
	public static final String EDITION                 = "edition";
	public static final String EQUATION_COUNT                 = "equation-count";
	public static final String FIG_COUNT                 = "fig-count";
	public static final String INSTITUTION                 = "institution";
	public static final String REF_COUNT                 = "ref-count";
	public static final String TABLE_COUNT                 = "table-count";
	public static final String WORD_COUNT                 = "word-count";
	
	 public static final String HR = "hr";
	 public static final String MI = "mi";
	 public static final String MN = "mn";
	 public static final String MO = "mo";
	 public static final String MSPACE = "mspace";
	 public static final String MTEXT = "mtext";
	 public static final String SC = "sc";
	 public static final String SELF_URI = "self-uri";
	 public static final String SERIES = "series";
	 public static final String TRANS_TITLE = "trans-title";

public final static String AWARD_ID = "award-id";
public final static String BREAK = "break";
public final static String FUNDING_SOURCE = "funding-source";
public final static String INLINE_GRAPHIC = "inline-graphic";
public final static String META_NAME = "meta-name";
public final static String META_VALUE= "meta-value";
public final static String TERM = "term";
public final static String UNDERLINE = "underline";

public final static String FAX = "fax";
public final static String CONF_SPONSOR = "conf-sponsor";

public final static String ANNOTATION = "annotation";
public final static String MALIGNGROUP = "maligngroup";
public final static String MALIGNMARK = "malignmark";
public final static String ON_BEHALF_OF = "on-behalf-of";
public final static String ROMAN = "roman";
public final static String STYLED_CONTENT = "styled-content";

public final static String NOTE = "note";
public final static String DISP_FORMULA_GROUP = "disp-formula-group";
public final static String PAGE_RANGE = "page-range";
public final static String ALT_TEXT = "alt-text";

public final static String STRIKE = "strike";
public final static String ISBN = "isbn";
public final static String INLINE_SUPPLEMENTAL_MATERIAL = "inline-supplementary-material";
public final static String PATENT = "patent";
public final static String TARGET = "target";
public final static String MROOT = "mroot";
public final static String PREFIX = "prefix";
public final static String SANS_SERIF = "sans-serif";
public final static String ACCESS_DATE = "access-date";

	public final static List<String> SPAN_LIST = Arrays.asList(
			new String[] {
					
					ACCESS_DATE,
					ALT_TEXT,
					ANNOTATION,
					ARTICLE_ID,
					ATTRIB,
					AWARD_ID,
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
					STRING_DATA,
					STYLED_CONTENT,
					SUBJECT,
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
			LOG.warn("SPAN element "+element.getLocalName()+" has children "+element.toXML());
		}

		return this;
	}

	public static boolean isSpan(String tag) {
		return SPAN_LIST.contains(tag) || SPAN_SPAN_LIST.contains(tag);
	}

	public HtmlSpan createAndRecurse() {
		HtmlSpan spanElement = new HtmlSpan();
		XMLUtil.copyAttributes(jatsElement, spanElement);
		spanElement.setClassAttribute(jatsElement.getLocalName());
		mainFactory.processChildren(jatsElement, spanElement);
		return spanElement;
	}



}
