package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.document.bmc.BmcReader;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.norma.pubstyle.hindawi.HindawiReader;
import org.xmlcml.norma.pubstyle.plosone.PlosoneReader;

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

	public final static Pubstyle BMC     = new Pubstyle(
			"bmc", 
			"src/main/resources/org/xmlcml/norma/document/bmc/",
			new BmcReader());
	public final static Pubstyle HINDAWI = new Pubstyle(
			"hindawi", 
			"src/main/resources/org/xmlcml/norma/document/hindawi/",
			new HindawiReader());
	public final static Pubstyle PLOSONE = new Pubstyle(
			"plosone",
			"src/main/resources/org/xmlcml/norma/document/plosone/",
			new PlosoneReader());
	
	private String name;
	private String taggerLocation;
	private PubstyleReader documentReader;
	
	public final static List<Pubstyle> PUBSTYLES;
	static {
		PUBSTYLES = new ArrayList<Pubstyle>();
		
		PUBSTYLES.add(Pubstyle.BMC);
		PUBSTYLES.add(Pubstyle.HINDAWI);
		PUBSTYLES.add(Pubstyle.PLOSONE);
	};
	
	private Pubstyle() {
	}
	
	public Pubstyle(String name, String taggerLocation, PubstyleReader documentReader) {
		this();
		setName(name);
		setTaggerLocation(taggerLocation);
		setDocumentReader(documentReader);
	}
	
	public static Pubstyle deducePubstyle(HtmlElement element) {
		
		LOG.error("deducePubstylel NYI");
		return null;
		
	}
	
	public static Pubstyle getPubstyle(String name) {
		for (Pubstyle j : PUBSTYLES) {
			if (j.getName().equals(name)) {
				return j;
			}
 		}
		return null;
	}

	public PubstyleReader getPubstyleReader() {
		return documentReader;
	}

	public void setDocumentReader(PubstyleReader documentReader) {
		this.documentReader = documentReader;
	}

	public String getTaggerLocation() {
		return taggerLocation;
	}

	public void setTaggerLocation(String taggerLocation) {
		this.taggerLocation = taggerLocation;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String getName() {
		return name;
	}

	public void applyTagger() {
		LOG.error("tagger NYI");
	}

	public static List<Pubstyle> getPubstyles() {
		return PUBSTYLES;
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
