package org.xmlcml.norma.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** a record of edits carried out.
 * 
 * @author pm286
 *
 */
public class EditList implements Iterable<String> {

	public static final Logger LOG = Logger.getLogger(EditList.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private List<String> edits;
	
	public EditList() {
		
	}
	
	public void add(EditList editList) {
		if (editList != null) {
			ensureEdits();
			this.edits.addAll(editList.getEdits());
		}
	}

	public Iterator<String> iterator() {
		ensureEdits();
		return edits.iterator();
	}

	private void ensureEdits() {
		if (edits == null) {
			edits = new ArrayList<String>();
		}
	}

	/** list of edits.
	 * 
	 * @return
	 */
	public List<String> getEdits() {
		ensureEdits();
		return edits;
	}

	public void add(String string) {
		ensureEdits();
		edits.add(string);
	}

	public int size() {
		ensureEdits();
		return edits.size();
	}
	
	public String toString() {
		return getEdits().toString();
	}
	
	
}
