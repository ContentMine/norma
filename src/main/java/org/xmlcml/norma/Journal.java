package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** format and metadata of scholarly Journal.
 * 
 * messy by definition.
 * 
 * may include publishers if all journals have a standard format (e.g. BMC)
 * 
 * @author pm286
 *
 */
public class Journal {

	private static final Logger LOG = Logger.getLogger(Journal.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public final static Journal BMC = new Journal("bmc");
	public final static Journal HINDAWI = new Journal("hindawi");
	public final static Journal PLOSONE = new Journal("plosone");
	
	private String name;
	
	public final static List<Journal> JOURNALS;
	static {
		JOURNALS = new ArrayList<Journal>();
		
		JOURNALS.add(Journal.BMC);
		JOURNALS.add(Journal.HINDAWI);
		JOURNALS.add(Journal.PLOSONE);
	};
	
	private Journal() {
	}
	
	public Journal(String name) {
		this.name = name;
	}
	
	public static Journal deduceJournal(String content) {
		throw new RuntimeException("NYI");
	}
	
	public static Journal getJournal(String name) {
		for (Journal j : JOURNALS) {
			if (j.getName().equals(name)) {
				return j;
			}
 		}
		return null;
	}

	private String getName() {
		return name;
	}

	public void read() {
		throw new RuntimeException("NYI");
	}

	public static List<Journal> getJournals() {
		return JOURNALS;
	};
	
	public String toString() {
		return name;
	}
	
}
