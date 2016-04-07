package org.xmlcml.norma.sections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

/**

 * 
 * @author pm286
 *
 */
public class JATSArticleElement extends JATSElement {
	/**
		<article>
		  <front>
		  </front>
		  <body>
		  </body>
		  <back>
		  </back>
		  </article>
	 */

	static final Logger LOG = Logger.getLogger(JATSArticleElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static final String TAG = "article";
	
	public final static List<String> ALLOWED_CHILD_NAMES = Arrays.asList(new String[] {
		JATSDivFactory.FRONT,
		JATSDivFactory.BODY,
		JATSDivFactory.BACK,
		JATSDivFactory.FLOATS_GROUP,
		JATSDivFactory.FLOATS_WRAP,
	});

	protected List<String> getAllowedChildNames() {
		return ALLOWED_CHILD_NAMES;
	}


	private JATSFrontElement front;
	private JATSBodyElement body;
	private JATSBackElement back;
	
	public JATSArticleElement(Element element) {
		super(element);
	}
	
	protected void applyNonXMLSemantics() {
		front = (JATSFrontElement) getSingleChild(JATSFrontElement.TAG);
		body = (JATSBodyElement) getSingleChild(JATSBodyElement.TAG);
		back = (JATSBackElement) getSingleChild(JATSBackElement.TAG);
	}

	public JATSReflistElement getReflistElement() {
		return back == null ? null : back.getReflist();
	}

	public JATSFrontElement getFront() {
		return front;
	}

	public JATSBodyElement getBody() {
		return body;
	}

	public JATSBackElement getBack() {
		return back;
	}

	public String getPMCID() {
		return front == null ? null : front.getPMCID();
	}

}
