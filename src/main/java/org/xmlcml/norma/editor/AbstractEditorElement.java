package org.xmlcml.norma.editor;

import java.io.InputStream;

import nu.xom.Comment;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.ProcessingInstruction;
import nu.xom.Text;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.graphics.svg.SVGCircle;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.xml.XMLUtil;

/**
 * error detectors and corrector for potentially garbled strings
 * 
 * see https://github.com/ContentMine/phylotree/issues/20
 * 
 * simple pattern (detectOnly)
 * 
 * 
 * 
 *   <pattern level="0">
    <!--  this regex enforces the ID patterns strictly.
    It will only fail when charcters are garbled to the same type, e.g.
    M->N , i->l, 3->8
    These are undetectable at this stage
     -->
    <space count="*"/>
    <!--  genus started with an uppercase letter followed by either several lowercase letters
    or on/two lowercase letters followed by period (abbreviation) -->
    <field name="genus" pattern="(?:\u2018?)[A-Z](?:[a-z]{2,}|[a-z]?\.)">
    </field>
    <space/>
    <!-- species should be only 2 or more lowercase characters -->
    <field name="species" pattern="[a-z]{2,}(?:\u2019?)">
    </field>
    <space/>
    <field name="strain" pattern="[^\s\(]+">
    </field>
    <space/>
    <!-- ID has an alpha and numeric part EU840723 or AJ307974 or NC_002967 -->
    <!-- require but strip left bracket -->
    <field name="id0" pattern="(?:\()[A-Z]{1,2}|NC_">
    <!--  and right bracket -->
    <field name="id1" pattern="[0-9]{5,6}(?:\)">
    </field>
    <space count="*"/>
  </pattern>

and

<pattern level="1">
    <!-- this regex allows for common garbles (detected as an error in 0) 
    and error correction by "safe" correction. The correction will generate a conformant 
    filed, but it may not be "correct". Each substitution has an error and can be logged.  -->
    <space count="*"/>
    <field name="genus" pattern="(?:\u2018?)[A-Z](?:[a-z]{2,}|[a-z02S/]?\.)">
      <substitution name="zero2little_o_or_big_o" original="0" edited="[oO]"/>
      <substitution name="two2little_z_or_big_z" original="2" edited="[zZ]"/>
      <substitution name="big_s2little_s" original="S" edited="s"/>
      <substitution name="slash2little_l_or_big_i" original="/" edited="[lI]"/>
      <!-- edit more as we find them -->
    </field>
    <space/>
    <field name="species" pattern="[a-z/]+(?:\u2019?)">
      <substitution name="s_slash_c2lower_sic" original="s/c" edited="sic"/>
      <substitution name="c_slash_l2lower_cil" original="d/" edited="cil"/>
      <substitution name="k_slash_n2lower_kin" original="k/n" edited="kin"/>
      <substitution name="r_slash_o2lower_rio" original="r/o" edited="rio"/>
      <substitution name="zero2little_o" original="0" edited="o"/>
      <substitution name="big_s2little_s" original="S" edited="s"/>
      <substitution name="slash2lower_l" original="/" edited="l"/>
    </field>
    <space/>
    <field name="strain" pattern="[^\s\(]+">
    </field>
    <space/>
    <field name="id0" pattern="(?:\()[A-Z123580]{1,2}|NC_">
      <!-- big letters may be garbled to numbers -->
      <substitution name="zero2big_o" original="0" edited="O"/>
      <substitution name="one2big_i" original="1" edited="I"/>
      <substitution name="two2big_z" original="2" edited="Z"/>
      <substitution name="three2big_b" original="3" edited="B"/>
      <substitution name="five2big_s" original="5" edited="S"/>
      <substitution name="eight2big_b" original="8" edited="B"/>
    </field>
    <field name="id1" pattern="[0-9BIOSZ]{5,6}(?:\)">
      <!--  numbers may be garbled to big letters -->
      <substitution name="big_o2zero" original="O" edited="0"/>
      <substitution name="big_b2eight" original="B" edited="eight"/>
      <substitution name="big_i2one" original="I" edited="one"/>
      <substitution name="big_s2five" original="S" edited="5"/>
      <substitution name="big_z2two" original="Z" edited="2"/>
    </field>
    <space count="*"/>
  </pattern>
 * @author pm286
 *
 */

public abstract class AbstractEditorElement extends Element {

	public static final Logger LOG = Logger.getLogger(AbstractEditorElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	protected AbstractEditorElement(String tag) {
		super(tag);
	}
	
	public static AbstractEditorElement createEditorElement(InputStream is) {
		Element element = XMLUtil.parseQuietlyToDocument(is).getRootElement();
		AbstractEditorElement editorElement = AbstractEditorElement.createEditorElement(element);
		return editorElement;
	}
	
	protected SubstitutionEditor getSubstitutionEditor() {
		SubstitutionEditor editor = null;
		Node parent = this.getParent();
		if (parent instanceof AbstractEditorElement) {
			editor = ((AbstractEditorElement)parent).getSubstitutionEditor(); 
		}
		return editor;
	}

	/** 
	 * Copy constructor from non-subclassed elements
	 */
	public static AbstractEditorElement createEditorElement(Element element) {
		AbstractEditorElement newElement = null;
		String tag = element.getLocalName();
		if (tag == null || tag.equals("")) {
			throw new RuntimeException("no tag");
		} else if (tag.equals(CombineElement.TAG)) {
			newElement = new CombineElement();
		} else if (tag.equals(ConstantElement.TAG)) {
			newElement = new ConstantElement();
		} else if (tag.equals(EditorElement.TAG)) {
			newElement = new EditorElement();
		} else if (tag.equals(FieldElement.TAG)) {
			newElement = new FieldElement();
		} else if (tag.equals(PatternElement.TAG)) {
			newElement = new PatternElement();
		} else if (tag.equals(PatternListElement.TAG)) {
			newElement = new PatternListElement();
		} else if (tag.equals(SpaceElement.TAG)) {
			newElement = new SpaceElement();
		} else if (tag.equals(SubstitutionElement.TAG)) {
			newElement = new SubstitutionElement();
		} else {
			throw new RuntimeException("unsupported editor element: "+tag);
		}
		if (newElement != null) {
	        XMLUtil.copyAttributes(element, newElement);
	        createSubclassedChildren(element, newElement);
		}
        return newElement;

	}
	protected static void createSubclassedChildren(Element oldElement, AbstractEditorElement newElement) {
		if (oldElement != null) {
			for (int i = 0; i < oldElement.getChildCount(); i++) {
				Node node = oldElement.getChild(i);
				Node newNode = null;
				if (node instanceof Text) {
					String value = node.getValue();
					newNode = new Text(value);
				} else if (node instanceof Comment) {
					newNode = new Comment(node.getValue());
				} else if (node instanceof ProcessingInstruction) {
					newNode = new ProcessingInstruction((ProcessingInstruction) node);
				} else if (node instanceof Element) {
					newNode = createEditorElement((Element) node);
				} else {
					throw new RuntimeException("Cannot create new node: "+node.getClass());
				}
				newElement.appendChild(newNode);
			}
		}
	}
	

}
