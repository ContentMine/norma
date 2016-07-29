package org.xmlcml.norma.sections;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlDiv;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class JATSDivFactory {

	private static final Logger LOG = Logger.getLogger(JATSDivFactory.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}
	public final static String ABBREV = "abbrev";
	public final static String ABSTRACT = "abstract";
	public final static String ACK = "ack";
	public final static String ADDRESS = "address";
	public final static String ADDR_LINE = "addr-line";
	public final static String AFF = "aff";
	public final static String AFF_ALTERNATIVES = "aff-alternatives";
	public final static String ALTERNATIVES = "alternatives";
	public final static String ALT_TITLE = "alt-title";
	public final static String APP = "app";
	public final static String APP_GROUP = "app-group";
	public final static String ARTICLE = "article";
	public final static String ARTICLE_CATEGORIES = "article-categories";
	public final static String ARTICLE_META = "article-meta";
	public final static String ARTICLE_TITLE = "article-title";
	public final static String AUTHOR_NOTES = "author-notes";
	public final static String AWARD_GROUP = "award-group";
	public final static String BACK = "back";
	public final static String BIO = "bio";
	public final static String BODY = "body";
	public final static String BOXED_TEXT = "boxed-text";
	public final static String CAPTION = "caption";
	public final static String CHAPTER_TITLE = "chapter-title";
	public final static String CITATION = "citation";
	public final static String COMMENT = "comment";
	public final static String CONFERENCE = "conference";
	public final static String CONTRIB = "contrib";
	public final static String CONTRIB_GROUP = "contrib-group";
	public final static String CORRESP = "corresp";
	public final static String COUNTS = "counts";
	public final static String CUSTOM_META = "custom-meta";
	public final static String CUSTOM_META_GROUP = "custom-meta-group";
	public final static String CUSTOM_META_WRAP = "custom-meta-wrap";
	public final static String DATE = "date";
	public final static String DATE_IN_CITATION ="date-in-citation";
	public final static String DEF = "def";
	public final static String DEF_ITEM = "def-item";
	public final static String DEF_LIST = "def-list";
	public final static String DISP_FORMULA ="disp-formula";
	public final static String DISP_QUOTE = "disp-quote";
	public final static String ELEMENT_CITATION = "element-citation";
	public final static String FIG = "fig";
	public final static String FLOATS_GROUP ="floats-group";
	public final static String FLOATS_WRAP = "floats-wrap";
	public final static String FN = "fn";
	public final static String FN_GROUP = "fn-group";
	public final static String FRONT = "front";
	public final static String FRONT_STUB = "front-stub";
	public final static String FUNDING_GROUP= "funding-group";
	public final static String FUNDING_STATEMENT = "funding-statement";
	public final static String GLOSSARY = "glossary";
	public final static String GRAPHIC = "graphic";
	public final static String HISTORY = "history";
	public final static String INLINE_FORMULA ="inline-formula";
	public final static String INSTITUTION_WRAP = "institution-wrap";
	public final static String JOURNAL_META = "journal-meta";
	public final static String JOURNAL_TITLE_GROUP = "journal-title-group";
	public final static String KWD_GROUP = "kwd-group";
	public final static String LICENSE = "license";
	public final static String LICENSE_P = "license-p";
	public final static String LIST = "list";
	public final static String LIST_ITEM = "list-item";
	public final static String MATH ="math";
	public final static String MEDIA = "media";
	public final static String MENCLOSE = "menclose";
	public final static String MFENCED ="mfenced";
	public final static String MFRAC ="mfrac";
	public final static String MIXED_CITATION = "mixed-citation";
	public final static String MOVER ="mover";
	public final static String MROW="mrow";
	public final static String MSQRT ="msqrt";
	public final static String MSTYLES = "mstyle";
	public final static String MSUB ="msub";
	public final static String MSUBSUP = "msubsup";
	public final static String MSUP ="msup";
	public final static String MTABLE ="mtable";
	public final static String MTD ="mtd";
	public final static String MTR ="mtr";
	public final static String MUNDER = "munder";
	public final static String MUNDEROVER ="munderover";
	public final static String NAME = "name";
	public final static String NAMED_CONTENT = "named-content";
	public final static String NAME_ALTERNATIVES = "name-alternatives";
	public final static String NOTES ="notes";
	public final static String ON_BEHALF_OF = "on-behalf-of";
	public final static String PERMISSIONS = "permissions";
	public final static String PERSON_GROUP = "person-group";
	public final static String PRINCIPAL_AWARD_RECIPIENT = "principal-award-recipient";
	public final static String PUBLISHER = "publisher";
	public final static String PUB_DATE = "pub-date";
	public final static String REF = "ref";
	public final static String REF_LIST = "ref-list";
	public final static String RELATED_ARTICLE = "related-article";
	public final static String RELATED_OBJECT = "related-object";
	public final static String SEC = "sec";
	public final static String SEC_META = "sec-meta";
	public final static String SEMANTICS = "semantics";
	public final static String SOURCE = "source";
	public final static String STATEMENT = "statement";
	public final static String STRING_NAME = "string-name";
	public final static String SUBJ_GROUP = "subj-group";
	public final static String SUBTITLE = "subtitle";
	public final static String SUB_ARTICLE = "sub-article";
	public final static String SUPPLEMENT = "supplement";
	public final static String SUPPLEMENTARY_MATERIAL = "supplementary-material";
	public final static String TABLE_WRAP = "table-wrap";
	public final static String TABLE_WRAP_FOOT = "table-wrap-foot";
	public final static String TAG = "div";
	public final static String TFOOT = "tfoot";
	public final static String TITLE = "title";
	public final static String TITLE_GROUP = "title-group";
	public final static String TRANS_ABSTRACT ="trans-abstract";
	public final static String TRANS_TITLE_GROUP ="trans-title-group";
	public final static String XREF = "xref";


	public final static List<String> DIV_LIST = Arrays.asList(
			new String[] {
					ABBREV,
					ABSTRACT,
					ACK,
					ADDRESS,
					ADDR_LINE,
//					AFF,
					AFF_ALTERNATIVES,
					ALTERNATIVES,
					ALT_TITLE,
					APP,
					APP_GROUP,
					ARTICLE,
					ARTICLE_CATEGORIES,
					ARTICLE_META,
					ARTICLE_TITLE,
					AUTHOR_NOTES,
					AWARD_GROUP,
					BACK,
					BIO,
					BODY,
					BOXED_TEXT,
					CAPTION,
					CHAPTER_TITLE,
					CITATION,
					COMMENT,
					CONFERENCE,
					CONTRIB,
					CONTRIB_GROUP,
					CORRESP,
					COUNTS,
					CUSTOM_META,
					CUSTOM_META_GROUP,
					CUSTOM_META_WRAP,
					DATE,
					DATE_IN_CITATION,
					DEF,
					DEF_ITEM,
					DEF_LIST,
					DISP_FORMULA,
					DISP_QUOTE,
					ELEMENT_CITATION,
					FIG,
					FLOATS_GROUP,
					FLOATS_WRAP,
					FN,
					FN_GROUP,
					FRONT,
					FRONT_STUB,
					FUNDING_GROUP,
					FUNDING_STATEMENT,
					GLOSSARY,
					GRAPHIC,
					HISTORY,
					INLINE_FORMULA,
					INSTITUTION_WRAP,
					JOURNAL_META,
					JOURNAL_TITLE_GROUP,
					KWD_GROUP,
					LICENSE,
					LICENSE_P,
					LIST,
					LIST_ITEM,
					MATH,
					MEDIA,
					MENCLOSE,
					MFENCED,
					MFRAC,
					MIXED_CITATION,
					MOVER,
					MROW,
					MSQRT,
					MSTYLES,
					MSUB,
					MSUBSUP,
					MSUP,
					MTABLE,
					MTD,
					MTR,
					MUNDER,
					MUNDEROVER,
					NAME,
					NAMED_CONTENT,
					NAME_ALTERNATIVES,
					NOTES,
					ON_BEHALF_OF,
					PERMISSIONS,
					PERSON_GROUP,
					PRINCIPAL_AWARD_RECIPIENT,
					PUBLISHER,
					PUB_DATE,
					REF,
					REF_LIST,
					RELATED_ARTICLE,
					RELATED_OBJECT,
					SEC,
					SEC_META,
					SEMANTICS,
					SOURCE,
					STATEMENT,
					STRING_NAME,
					SUBJ_GROUP,
					SUBTITLE,
					SUB_ARTICLE,
					SUPPLEMENT,
					SUPPLEMENTARY_MATERIAL,
					TABLE_WRAP,
					TABLE_WRAP_FOOT,
					TFOOT,
					TITLE,
					TITLE_GROUP,
					TRANS_ABSTRACT,
					TRANS_TITLE_GROUP,
//					XREF,

			}
		);

	public static boolean isDiv(String tag) {
		return DIV_LIST.contains(tag);
	}

	private Element jatsElement;
	private JATSFactory mainFactory;

	public JATSDivFactory(JATSFactory mainFactory) {
		this.mainFactory = mainFactory;
	}
	
	JATSDivFactory setElement(Element element) {
		if (element == null) {
			LOG.warn("NULL ELEMENT");
		}
		this.jatsElement = element;
		return this;
	}

	public boolean matches(String tag) {
		return getTag().equals(tag);
	}

	public String getTag() {
		return TAG;
	}


	public HtmlDiv createAndRecurse() {
		HtmlDiv divElement = new HtmlDiv();
		XMLUtil.copyAttributes(jatsElement, divElement);
		divElement.setClassAttribute(jatsElement.getLocalName());
		mainFactory.processChildren(jatsElement, divElement);
		return divElement;
	}

	
}
