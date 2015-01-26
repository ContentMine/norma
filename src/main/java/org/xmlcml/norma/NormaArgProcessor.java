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
	
//	public final static ArgumentOption CHAR_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-c",
//			"--chars",
//			"[pairs of characters, as unicode points; i.e. char00, char01, char10, char11, char20, char21",
//			"\n"
//			+ "CHARS:\n"
//			+ "Replaces one character by another. There are many cases where original characters are unsuitable \n"
//			+ "for science and can be replaced (often by low codepoints).\n"
//			+ "Smart (balanced) quotes can usually be replaced by \" or '\n"
//			+ "mdash is often used where minus is better\n"
//			+ "Format, strings of form u1234 \n"
//			+ "",
//			org.xmlcml.args.StringPair.class,
//			"",
//			1, Integer.MAX_VALUE,
//			"processChars"
//			);
//		
//	public final static ArgumentOption DIV_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-d",
//			"--divs",
//			"expression [expression] ...",
//			"\n"
//			+ "DIVS:\n"
//			+ "List of expressions identifying XML element to wrap as divs/sections\n"
//			+ "Examples might be h1, h2, h3, or numbers sections\n"
//			+ "Still unxder developement \n",
//			java.lang.String.class,
//			"",
//			0, Integer.MAX_VALUE,
//			"processDivs"
//			);
//		
//	public final static ArgumentOption NAME_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-n",
//			"--name",
//			" tag1,tag2 [tag1,tag2 ....]",
//			"\n"
//			+ "NAME:\n"
//			+ "List of comma-separated pairs of tags; the first is replaced by the second. Example might be:\n"
//			+ "  em,i strong,b\n"
//			+ "i.e. replace all <em>...</em> by <i>...</i>"
//			+ "",
//			StringPair.class,
//			"",
//			1, Integer.MAX_VALUE,
//			"processNames"
//			);
//		
//	public final static ArgumentOption PUBSTYLE_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-p",
//			"--pubstyle",
//			"pub_code",
//			"\n"
//			+ "PUBSTYLE:\nCode or mnemomic to identifier the publisher or journal style. \n"
//			+ "this is a list of journal/publisher styles so Norma knows how to interpret the input. At present only one argument \n"
//			+ "is allowed. The pubstyle determines the format of the XML or HTML, the metadata, and\n"
//			+ "soon how to parse the PDF. At present we'll use mnemonics such as 'bmc' or 'biomedcentral.com' or 'cellpress'.\n"
//			+ "To get a list of these use "+"--pubstyle"+" without arguments. Note: under early development and note also that \n"
//			+ "publisher styles change and can be transferred between publishers and journals",
//			String.class,
//			Pubstyle.PLOSONE.toString(),
//			1, 1,
//			"processPubstyle"
//			);
//		
//	public final static ArgumentOption STRIP_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-s",
//			"--strip",
//			"[options to strip]",
//			"\n"
//			+ "STRIP:\n"
//			+ "List of XML components to strip from the raw well-formed HTML;\n"
//			+ "if a list is given, then use that; if this argument is missing (or the single\n"
//			+ "string '#pubstyle' then the Pubstyle deualts are used. If there are no arguments\n"
//			+ "then no stripping is done. a single '?' will list the Pubstyle defaults\n"
//			+ "The following are allowed:\n"
//			+ "  an element local name (e.g. input)\n"
//			+ "  an XPath expression (e.g. //*[@class='foobar'])\n"
//			+ "  !DOCTYPE (strips <!DOCTYPE ...> which speeds up reading)\n"
//			+ "  an attribute (e.g. @class) (NotYetImplemented)\n"
//			+ "\n"
//			+ "Note that tokens are whitespace-separated (sorry if this interacts with XPath)\n"
//			+ "\n"
//			+ "Examples: \n"
//			+ "  input script object (removes these three element\n"
//			+ "  //*[contains(@class,'sidebar')]  (removes <div class='sidebar'> ... </div>\n"
//			+ "  !DOCTYPE (removes <!DOCTYPE ...> before parsing))\n"
//			+ "  !DOCTYPE input script object //*[contains(@class,'sidebar')] (removes all the above)\n"
//			+ "",
//			String.class,
//			Pubstyle.PLOSONE.toString(),
//			0, Integer.MAX_VALUE,
//			"processStrip"
//			);
//	
//	public final static ArgumentOption TIDY_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-t",
//			"--tidy",
//			"[HTML tidy option]",
//			"\n"
//			+ "TIDY:\n"
//			+ "Choose an HTML tidy tool. At present we have:"
//			+ "  JTidy JSoup and HtmlUnit \n"
//			+ "(NYI) This is very experimental at present"
//			+ "",
//			String.class,
//			"JSoup",
//			1, 1,
//			"processTidy"
//			);
//	
//	public final static ArgumentOption XSL_OPTION = new ArgumentOption(
//			org.xmlcml.norma.NormaArgProcessor.class,
//			"-x",
//			"--xsl",
//			"stylesheet",
//			"\n"
//			+ "XSL:\n"
//			+ "Transform XML input with stylesheet. Argument may be a file/URL reference to a stylesheet,\n"
//			+ "or a code from one of {nlm, jats ...}\n"
//			+ "the codes are checked first and then the document reference\n"
//			+ "",
//			String.class,
//			"nlm",
//			1, 1,
//			"processXsl"
//			);
//	
//	// FIXME we will read this in from files
//	public final static List<ArgumentOption> NORMA_OPTION_LIST = Arrays.asList(new ArgumentOption[] {
//			CHAR_OPTION, 
//			DIV_OPTION, 
//			NAME_OPTION, 
//			PUBSTYLE_OPTION, 
//			STRIP_OPTION, 
//			TIDY_OPTION, 
//			XSL_OPTION
//	});
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
        for (ArgumentOption argumentOption : argumentOptionList) {
			LOG.debug(argumentOption.getHelp());
		}
	}

	public NormaArgProcessor(String[] args) {
		this();
		parseArgs(args);
	}

//	protected List<ArgumentOption> getOptionList() {
//		List<ArgumentOption> optionList = new ArrayList<ArgumentOption>(NORMA_OPTION_LIST);
//		optionList.addAll(super.argumentOptionList);
//		return optionList;
//	}

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
	
}
