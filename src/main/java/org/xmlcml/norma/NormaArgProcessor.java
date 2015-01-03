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

	public static final String J = "-j";
	public static final String JOURNAL = "--journal";
	public static final String JOURNAL_HELP = "\n"
			+ "JOURNAL:\nName or code of journal or publisher. \n"
			+ "this is a list of journals so Norma knows how to interpret the input. At present only \n"
			+ "one is allowed. The journal/s determine the format of the XML or HTML, the metadata, and\n"
			+ "soon how to parse the PDF. At present we'll use mnemonics such as 'bmc' or 'biomedcentral.com'\n"
			+ "to get a list of these use "+JOURNAL+" without arguments";

	private List<String> journalList;

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
		while(listIterator.hasNext()) {
			if (!super.parseArgs(listIterator)) {
				parsed &= parseArgs1(listIterator);
			}
		}
		return parsed;
	}
	
	protected boolean parseArgs1(ListIterator<String> listIterator) {
		boolean processed = false;
		if (listIterator.hasNext()) {
			processed = true;
			String arg = listIterator.next();
			if (!arg.startsWith(MINUS)) {
				LOG.error("Parsing failed at: ("+arg+"), expected \"-\" trying to recover");
			} else if (J.equals(arg) || JOURNAL.equals(arg)) {
				processJournal(listIterator); 
			} else {
				processed = false;
				LOG.error("Unknown arg: ("+arg+"), trying to recover");
			}
		}
		return processed;
	}

	private void processJournal(ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		if (inputs.size() == 0) {
			journalList = new ArrayList<String>();
			LOG.error("Must give at least one journal (currently only one). Current options are:");
			for (Journal journal : Journal.getJournals()) {
				System.err.println("> "+journal.toString());
			}
			
		} else {
			journalList = inputs;
		}
	}

	protected void processHelp() {
		super.processHelp();
		LOG.info(HELP_NORMA);
	}


}
