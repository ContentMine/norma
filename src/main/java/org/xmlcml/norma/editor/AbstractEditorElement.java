package org.xmlcml.norma.editor;

import nu.xom.Comment;
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

public abstract class AbstractEditorElement extends Element {

	public static final Logger LOG = Logger.getLogger(AbstractEditorElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	protected AbstractEditorElement(String tag) {
		super(tag);
	}

	/** 
	 * Copy constructor from non-subclassed elements
	 */
	public static AbstractEditorElement createEditorElement(Element element) {
		AbstractEditorElement newElement = null;
		String tag = element.getLocalName();
		if (tag == null || tag.equals("")) {
			throw new RuntimeException("no tag");
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
