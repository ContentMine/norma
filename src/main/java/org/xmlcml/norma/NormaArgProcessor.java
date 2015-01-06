package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** 
 * Processes commandline arguments.
 * 
 * @author pm286
 */
public class NormaArgProcessor extends DefaultArgProcessor{
	
	private static final Logger LOG = Logger.getLogger(NormaArgProcessor.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public final static String HELP_NORMA = "Norma help";

//	public static final String P = "-p";
//	public static final String PUBSTYLE = "--pubstyle";
//	public static final String PUBSTYLE_HELP = "\n"
//			+ "PUBTYPE:\nCode or mnemomic to identifier the publisher or journal style. \n"
//			+ "this is a list of journal/publisher styles so Norma knows how to interpret the input. At present only one argument \n"
//			+ "is allowed. The pubstyle determines the format of the XML or HTML, the metadata, and\n"
//			+ "soon how to parse the PDF. At present we'll use mnemonics such as 'bmc' or 'biomedcentral.com' or 'cellpress'.\n"
//			+ "To get a list of these use "+PUBSTYLE+" without arguments. Note: under early devlopment and also that \n"
//			+ "publisher styles change and can be transferred between publishers and journals";

	
	public final static ArgumentOption PUBSTYLE_OPTION = new ArgumentOption(
		"-p",
		"--pubstyle",
		"\n"
		+ "PUBTYPE:\nCode or mnemomic to identifier the publisher or journal style. \n"
		+ "this is a list of journal/publisher styles so Norma knows how to interpret the input. At present only one argument \n"
		+ "is allowed. The pubstyle determines the format of the XML or HTML, the metadata, and\n"
		+ "soon how to parse the PDF. At present we'll use mnemonics such as 'bmc' or 'biomedcentral.com' or 'cellpress'.\n"
		+ "To get a list of these use "+"-p"+" without arguments. Note: under early devlopment and note also that \n"
		+ "publisher styles change and can be transferred between publishers and journals",
		Pubstyle.class,
		Pubstyle.PLOSONE,
		1, 1
		);
	
	private List<String> pubstyleList;

	private Pubstyle pubstyle;

	public NormaArgProcessor() {
		super();
	}

	public NormaArgProcessor(String[] args) {
		this();
		parseArgs(args);
	}

	public boolean parseArgs(String[] commandLineArgs) {
		List<String> args = Arrays.asList(commandLineArgs);
		ListIterator<String> listIterator = args.listIterator();
		return parseArgs(listIterator);
	}

	protected boolean parseArgs(ListIterator<String> listIterator) {
		boolean parsed = true;
//		this.applyDefaults();
		while(listIterator.hasNext()) {
			if (!super.parseArgs(listIterator)) {
				parsed &= parseArgs1(listIterator);
			}
		}
		return parsed;
	}
	
//	protected void applyDefaults() {
//		super.applyDefaults();
//		pubstyleList = PUBSTYLE_OPTION.getDefaults().getDefaultStrings();
//	}

	protected boolean parseArgs1(ListIterator<String> listIterator) {
		boolean processed = false;
		if (listIterator.hasNext()) {
			processed = true;
			String arg = listIterator.next();
			if (!arg.startsWith(MINUS)) {
				LOG.error("Parsing failed at: ("+arg+"), expected \"-\" trying to recover");
			} else if (PUBSTYLE_OPTION.matches(arg)) {
				processPubstyle(PUBSTYLE_OPTION, listIterator); 
			} else {
				processed = false;
				LOG.error("Unknown arg: ("+arg+"), trying to recover");
			}
		}
		return processed;
	}

	private void processPubstyle(ArgumentOption argOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		if (inputs.size() == 0) {
			pubstyleList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = argOption.processArgs(inputs).getString();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	protected void processHelp() {
		super.processHelp();
		LOG.info(HELP_NORMA);
	}


}
