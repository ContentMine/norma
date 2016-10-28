package org.xmlcml.norma.txt;

import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public abstract class AbstractSection implements Iterable<AnnotatedLine> {

	private static final Logger LOG = Logger.getLogger(AbstractSection.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public AnnotatedLineContainer localLineContainer;
	protected AnnotatedLineContainer parentLineContainer;

	public Iterator<AnnotatedLine> iterator() {
		return localLineContainer.iterator();
	}

	public void add(AnnotatedLine section) {
		ensureAnnotatedLineContainer();
		localLineContainer.add(section);
	}

	private void ensureAnnotatedLineContainer() {
		if (localLineContainer == null) {
			localLineContainer = new AnnotatedLineContainer();
		}
	}

	public void addLines(int start, int end) {
		for (int i = start; i < end; i++) {
			this.add(parentLineContainer.get(i));
		}
	}

	protected int size() {
		ensureAnnotatedLineContainer();
		return localLineContainer.size(); 
	}
	
	public void makeSection(AbstractSection section, int startNumber, int endNumber) {
		for (int i = startNumber; i < endNumber; i++) {
			section.add(parentLineContainer.get(i));
		}
	}
}
