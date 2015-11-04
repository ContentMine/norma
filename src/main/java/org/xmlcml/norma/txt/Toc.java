package org.xmlcml.norma.txt;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.IntArray;

/** holds TableOfContents.
 * 
 * @author pm286
 *
 */
public class Toc extends AbstractSection {

	private static final Logger LOG = Logger.getLogger(Toc.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public Toc() {
		
	}
	
	boolean checkToc() {
		IntArray lastSection = null;
		if (parentLineContainer != null) {
			for (int i = 1; i < parentLineContainer.size(); i++) {
				AnnotatedLine annotatedLine = parentLineContainer.get(i);
				LOG.trace(annotatedLine);
				IntArray section = annotatedLine.getLeftSection();
				if (lastSection == null) {
					lastSection = section;
				} else {
					if (!AnnotatedLine.checkArrayIncrement(annotatedLine.getLineNumber(), lastSection, section)) {
	//					LOG.warn("possible bad increment "+lastSection+" => "+section);
					}
				}
				lastSection = section;
			}
		}
		return true;
	}
	
}
