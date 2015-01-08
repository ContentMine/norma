package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;

/** format and metadata of scholarly Journal.
 * 
 * messy by definition.
 * 
 * may include publishers if all journals have a standard format (e.g. BMC)
 * 
 * @author pm286
 *
 */
public class Pubstyle {

	private static final Logger LOG = Logger.getLogger(Pubstyle.class);
	
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public final static Pubstyle BMC = new Pubstyle("bmc");
	public final static Pubstyle HINDAWI = new Pubstyle("hindawi");
	public final static Pubstyle PLOSONE = new Pubstyle("plosone");
	
	private String name;
	
	public final static List<Pubstyle> JOURNALS;
	static {
		JOURNALS = new ArrayList<Pubstyle>();
		
		JOURNALS.add(Pubstyle.BMC);
		JOURNALS.add(Pubstyle.HINDAWI);
		JOURNALS.add(Pubstyle.PLOSONE);
	};
	
	private Pubstyle() {
	}
	
	public Pubstyle(String name) {
		this.name = name;
	}
	
	public static Pubstyle deducePubstyle(HtmlElement element) {
		
		LOG.error("deducePubstylel NYI");
		return null;
		
	}
	
	public static Pubstyle getPubstyle(String name) {
		for (Pubstyle j : JOURNALS) {
			if (j.getName().equals(name)) {
				return j;
			}
 		}
		return null;
	}

	private String getName() {
		return name;
	}

	public void applyTagger() {
		LOG.error("tagger NYI");
	}

	public static List<Pubstyle> getPubstyles() {
		return JOURNALS;
	};
	
	public String toString() {
		return name;
	}

	public static void help() {
		LOG.error("Normally give at least one pubstyle (currently only one). Current options are:");
		for (Pubstyle pubstyle : Pubstyle.getPubstyles()) {
			System.err.println("> "+pubstyle.toString());
		}
	}
	
}
