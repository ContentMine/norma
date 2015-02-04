package org.xmlcml.norma;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.pubstyle.DefaultPubstyleReader;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.norma.pubstyle.bmc.BmcReader;
import org.xmlcml.norma.pubstyle.hindawi.HindawiReader;
import org.xmlcml.norma.pubstyle.plosone.PlosoneReader;
import org.xmlcml.norma.tagger.PubstyleTagger;

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
	private PubstyleReader pubstyleReader;
	
	public final static List<Pubstyle> PUBSTYLES;
	static {
		PUBSTYLES = new ArrayList<Pubstyle>();
		
		PUBSTYLES.add(Pubstyle.BMC);
		PUBSTYLES.add(Pubstyle.HINDAWI);
		PUBSTYLES.add(Pubstyle.PLOSONE);
	};
	
	protected Pubstyle() {
	}
	
	public Pubstyle(String name, String taggerLocation, PubstyleReader reader) {
		this();
		setName(name);
		setTaggerLocation(taggerLocation);
		setPubstyleReader(reader);
	}
	
	public static Pubstyle deducePubstyle(HtmlElement element) {
		LOG.error("deducePubstyle from input document NYI");
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

	public PubstyleReader getPubstyleReaderOrCreateDefault() {
		if (pubstyleReader == null) {
			pubstyleReader = new DefaultPubstyleReader();
		}
		return pubstyleReader;
	}

	public PubstyleReader getPubstyleReaderOrCreateDefault(HtmlElement htmlElement) {
		getPubstyleReaderOrCreateDefault();
		pubstyleReader.setHtmlElement(htmlElement);
		return pubstyleReader;
	}

	public void setPubstyleReader(PubstyleReader documentReader) {
		this.pubstyleReader = documentReader;
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

	public void applyTagger(InputFormat inputFormat, HtmlElement htmlElement) {
		if (pubstyleReader == null) {
			LOG.error("cannot find a pubstyleReader");
		} else {
			PubstyleTagger tagger = pubstyleReader.getTagger(inputFormat);
			if (tagger == null) {
				LOG.error(name+" pubstyle: cannot find tagger for: "+inputFormat);
			} else {
				tagger.addTagsToSections(htmlElement);
			}
		}
	}

	public static List<Pubstyle> getPubstyles() {
		return PUBSTYLES;
	};
	
	public String toString() {
		return name;
	}

	public HtmlElement readRawHtmlAndCreateWellFormed(InputFormat inputFormat, String inputName) throws Exception {
		LOG.trace("using HTML");
		readInput(inputFormat, inputName);
		
		// may need to go to this at some stage
	//		HtmlUnitWrapper htmlUnitWrapper = new HtmlUnitWrapper();
	//		HtmlElement htmlElement = htmlUnitWrapper.readAndCreateElement(url);
	
		pubstyleReader.getOrCreateXHtmlFromRawHtml();
		HtmlElement htmlElement = pubstyleReader.getHtmlElement();
		return htmlElement;
	}

	private void readInput(InputFormat inputFormat, String inputName) throws Exception {
		pubstyleReader = getPubstyleReaderOrCreateDefault();
		pubstyleReader.setFormat(inputFormat);
		pubstyleReader.readFile(new File(inputName));
	}

	public static void help() {
		LOG.error("Normally give at least one pubstyle (currently only one). Current options are:");
		for (Pubstyle pubstyle : Pubstyle.getPubstyles()) {
			System.err.println("> "+pubstyle.toString());
		}
	}

}
