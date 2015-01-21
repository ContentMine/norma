package org.xmlcml.norma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.args.ArgumentOption;
import org.xmlcml.args.DefaultArgProcessor;
import org.xmlcml.args.StringPair;

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
	
	public final static ArgumentOption CHAR_OPTION = new ArgumentOption(
			"-c",
			"--chars",
			"[pairs of characters, as unicode points; i.e. char00, char01, char10, char11, char20, char21",
			"\n"
			+ "CHARS:\n"
			+ "Replaces one character by another. There are many cases where original characters are unsuitable \n"
			+ "for science and can be replaced (often by low codepoints).\n"
			+ "Smart (balanced) quotes can usually be replaced by \" or '\n"
			+ "mdash is often used where minus is better\n"
			+ "Format, strings of form u1234 \n"
			+ "",
			StringPair.class,
			"",
			1, Integer.MAX_VALUE
			);
		
	public final static ArgumentOption DIV_OPTION = new ArgumentOption(
			"-d",
			"--divs",
			"expression [expression] ...",
			"\n"
			+ "DIVS:\n"
			+ "List of expressions identifying XML element to wrap as divs/sections\n"
			+ "Examples might be h1, h2, h3, or numbers sections\n"
			+ "Still unxder developement \n",
			String.class,
			"",
			0, Integer.MAX_VALUE
			);
		
	public final static ArgumentOption NAME_OPTION = new ArgumentOption(
			"-n",
			"--name",
			" tag1,tag2 [tag1,tag2 ....]",
			"\n"
			+ "NAME:\n"
			+ "List of comma-separated pairs of tags; the first is replaced by the second. Example might be:\n"
			+ "  em,i strong,b\n"
			+ "i.e. replace all <em>...</em> by <i>...</i>"
			+ "",
			StringPair.class,
			"",
			1, Integer.MAX_VALUE
			);
		
	public final static ArgumentOption PUBSTYLE_OPTION = new ArgumentOption(
			"-p",
			"--pubstyle",
			"pub_code",
			"\n"
			+ "PUBSTYLE:\nCode or mnemomic to identifier the publisher or journal style. \n"
			+ "this is a list of journal/publisher styles so Norma knows how to interpret the input. At present only one argument \n"
			+ "is allowed. The pubstyle determines the format of the XML or HTML, the metadata, and\n"
			+ "soon how to parse the PDF. At present we'll use mnemonics such as 'bmc' or 'biomedcentral.com' or 'cellpress'.\n"
			+ "To get a list of these use "+"--pubstyle"+" without arguments. Note: under early development and note also that \n"
			+ "publisher styles change and can be transferred between publishers and journals",
			String.class,
			Pubstyle.PLOSONE.toString(),
			1, 1
			);
		
	public final static ArgumentOption STRIP_OPTION = new ArgumentOption(
			"-s",
			"--strip",
			"[options to strip]",
			"\n"
			+ "STRIP:\n"
			+ "List of XML components to strip from the raw well-formed HTML;\n"
			+ "if a list is given, then use that; if this argument is missing (or the single\n"
			+ "string '#pubstyle' then the Pubstyle deualts are used. If there are no arguments\n"
			+ "then no stripping is done. a single '?' will list the Pubstyle defaults\n"
			+ "The following are allowed:\n"
			+ "  an element local name (e.g. input)\n"
			+ "  an XPath expression (e.g. //*[@class='foobar'])\n"
			+ "  !DOCTYPE (strips <!DOCTYPE ...> which speeds up reading)\n"
			+ "  an attribute (e.g. @class) (NotYetImplemented)\n"
			+ "\n"
			+ "Note that tokens are whitespace-separated (sorry if this interacts with XPath)\n"
			+ "\n"
			+ "Examples: \n"
			+ "  input script object (removes these three element\n"
			+ "  //*[contains(@class,'sidebar')]  (removes <div class='sidebar'> ... </div>\n"
			+ "  !DOCTYPE (removes <!DOCTYPE ...> before parsing))\n"
			+ "  !DOCTYPE input script object //*[contains(@class,'sidebar')] (removes all the above)\n"
			+ "",
			String.class,
			Pubstyle.PLOSONE.toString(),
			0, Integer.MAX_VALUE
			);
	
	public final static ArgumentOption TIDY_OPTION = new ArgumentOption(
			"-t",
			"--tidy",
			"[HTML tidy option]",
			"\n"
			+ "TIDY:\n"
			+ "Choose an HTML tidy tool. At present we have:"
			+ "  JTidy JSoup and HtmlUnit \n"
			+ "(NYI) This is very experimental at present"
			+ "",
			String.class,
			"JSoup",
			1, 1
			);
	
	public final static ArgumentOption XSL_OPTION = new ArgumentOption(
			"-x",
			"--xsl",
			"stylesheet",
			"\n"
			+ "XSL:\n"
			+ "Transform XML input with stylesheet. Argument may be a file/URL reference to a stylesheet,\n"
			+ "or a code from one of {nlm, jats ...}\n"
			+ "the codes are checked first and then the document reference\n"
			+ "",
			String.class,
			"nlm",
			1, 1
			);
	
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
			LOG.trace("norma:"+arg);
			if (!arg.startsWith(MINUS)) {
				LOG.error("Parsing failed at: ("+arg+"), expected \"-\" trying to recover");
			} else if (CHAR_OPTION.matches(arg)) {
				processChars(CHAR_OPTION, listIterator); 
			} else if (DIV_OPTION.matches(arg)) {
				processDivs(DIV_OPTION, listIterator); 
			} else if (NAME_OPTION.matches(arg)) {
				processNames(NAME_OPTION, listIterator); 
			} else if (PUBSTYLE_OPTION.matches(arg)) {
				processPubstyle(PUBSTYLE_OPTION, listIterator); 
			} else if (STRIP_OPTION.matches(arg)) {
				processStrip(STRIP_OPTION, listIterator); 
			} else if (TIDY_OPTION.matches(arg)) {
				processTidy(TIDY_OPTION, listIterator); 
			} else if (XSL_OPTION.matches(arg)) {
				processXsl(XSL_OPTION, listIterator); 
			} else {
				processed = false;
				LOG.error("Unknown arg: ("+arg+"), trying to recover");
			}
		}
		return processed;
	}

	private void processChars(ArgumentOption charOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		charPairList = charOption.processArgs(inputs).getStringPairValues();
	}

	private void processDivs(ArgumentOption divOption, ListIterator<String> listIterator) {
		divList = createTokenListUpToNextMinus(listIterator);
	}

	private void processNames(ArgumentOption nameOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		namePairList = nameOption.processArgs(inputs).getStringPairValues();
	}

	private void processPubstyle(ArgumentOption pubstyleOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		if (inputs.size() == 0) {
			stripList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = pubstyleOption.processArgs(inputs).getStringValue();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	private void processStrip(ArgumentOption stripOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		if (inputs.size() == 0) {
			stripList = new ArrayList<String>();
		} else {
			stripList = inputs;
		}
	}

	private void processTidy(ArgumentOption tidyOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		tidyName = tidyOption.processArgs(inputs).getStringValue();
	}

	private void processXsl(ArgumentOption xslOption, ListIterator<String> listIterator) {
		List<String> inputs = createTokenListUpToNextMinus(listIterator);
		xslStylesheet = xslOption.processArgs(inputs).getStringValue();
	}

	protected void processHelp() {
		System.out.println(
				"\n"
				+ "====NORMA====\n"
				+ "Norma converters raw files into scholarlyHTML, and adds tags to sections.\n"
				+ "Some of the conversion is dependent on publication type (--pubstyle) while some\n"
				+ "is constant for all documents. Where possible Norma guesses the input type, but can\n"
				+ "also be guided with the --extensions flag where the file/URL has no extension. "
				+ ""
				);
		System.out.println(PUBSTYLE_OPTION.getHelp());
		super.processHelp();
	}

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
	
}
