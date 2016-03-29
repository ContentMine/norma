package org.xmlcml.norma.sections;

import nu.xom.Element;

public class JATSArticleIdElement extends JATSElement {

	public final static String TAG = "article-id";

	public static final String PMID = "pmid";
	public static final String PMCID = "pmcid";
	public static final String PUB_ID_TYPE = "pub-id-type";

	public JATSArticleIdElement(Element element) {
		super(element);
	}


}
