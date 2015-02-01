package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.args.ArgIterator;
import org.xmlcml.args.ArgumentOption;
import org.xmlcml.args.DefaultArgProcessor;
import org.xmlcml.args.StringPair;
import org.xmlcml.files.FileContainer;

/** 
 * Processes commandline arguments.
 * for Norma
 * 
 * @author pm286
 */
public class NormaArgProcessor extends DefaultArgProcessor{
	
	public static final Logger LOG = Logger.getLogger(NormaArgProcessor.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static String HELP_NORMA = "Norma help";
	
	private static String RESOURCE_NAME_TOP = "/org/xmlcml/norma";
	private static String ARGS_RESOURCE = RESOURCE_NAME_TOP+"/"+"args.xml";
	
	public final static String DOCTYPE = "!DOCTYPE";
		
	// options
	private List<StringPair> charPairList;
	private List<String> divList;
	private List<StringPair> namePairList;
	private Pubstyle pubstyle;
	private List<String> stripList;
	private String tidyName;
	private String xslStylesheet;

	public NormaArgProcessor() {
		super();
		this.readArgumentOptions(ARGS_RESOURCE);
//        for (ArgumentOption argumentOption : argumentOptionList) {
//			LOG.debug(argumentOption.getHelp());
//		}
	}

	public NormaArgProcessor(String[] args) {
		this();
		parseArgs(args);
	}

	// ============= METHODS =============
 	public void processChars(ArgumentOption charOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus();
		charPairList = charOption.processArgs(inputs).getStringPairValues();
	}

	public void processDivs(ArgumentOption divOption, ArgIterator argIterator) {
		divList = argIterator.createTokenListUpToNextMinus();
	}

	public void processNames(ArgumentOption nameOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus();
		namePairList = nameOption.processArgs(inputs).getStringPairValues();
	}
	
	public void processPubstyle(ArgumentOption pubstyleOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus();
		if (inputs.size() == 0) {
			stripList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = pubstyleOption.processArgs(inputs).getStringValue();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	public void processStrip(ArgumentOption stripOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus();
		if (inputs.size() == 0) {
			stripList = new ArrayList<String>();
		} else {
			stripList = inputs;
		}
	}

	public void processTidy(ArgumentOption tidyOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus();
		tidyName = tidyOption.processArgs(inputs).getStringValue();
	}

	public void processXsl(ArgumentOption xslOption, ArgIterator argIterator) {
		List<String> inputs = argIterator.createTokenListUpToNextMinus();
		xslStylesheet = xslOption.processArgs(inputs).getStringValue();
	}

	public void processHelp() {
		System.err.println(
				"\n"
				+ "====NORMA====\n"
				+ "Norma converters raw files into scholarlyHTML, and adds tags to sections.\n"
				+ "Some of the conversion is dependent on publication type (--pubstyle) while some\n"
				+ "is constant for all documents. Where possible Norma guesses the input type, but can\n"
				+ "also be guided with the --extensions flag where the file/URL has no extension. "
				+ ""
				);
		super.processHelp();
	}
	
	// ==========================

	public Pubstyle getPubstyle() {
		return pubstyle;
	}

	public String getStylesheet() {
		return xslStylesheet;
	}

	public List<StringPair> getCharPairList() {
		return charPairList;
	}

	public List<String> getDivList() {
		return divList;
	}

	public List<StringPair> getNamePairList() {
		return namePairList;
	}

	public List<String> getStripList() {
		return stripList;
	}

	public String getTidyName() {
		return tidyName;
	}

//	public FileContainer getFileContainer() {
//		return fileC;
//	}

}
