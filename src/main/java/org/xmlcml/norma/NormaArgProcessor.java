package org.xmlcml.norma;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.args.ArgIterator;
import org.xmlcml.cmine.args.ArgumentOption;
import org.xmlcml.cmine.args.DefaultArgProcessor;
import org.xmlcml.cmine.args.StringPair;
import org.xmlcml.cmine.args.ValueElement;
import org.xmlcml.cmine.args.VersionManager;
import org.xmlcml.cmine.files.CMDir;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.image.ocr.NamedImage;
import org.xmlcml.norma.input.html.HtmlCleaner;
import org.xmlcml.norma.tagger.SectionTagger;

/**
 * Processes commandline arguments.
 * for Norma
 *
 * @author pm286
 */
public class NormaArgProcessor extends DefaultArgProcessor {

	private static final String DOT_PNG = ".png";
	private static final String IMAGE = "image";
	private static final String PNG = "png";

	public static final Logger LOG = Logger.getLogger(NormaArgProcessor.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private static final String XML = "xml";

	public final static String HELP_NORMA = "Norma help";

	private static String RESOURCE_NAME_TOP = "/org/xmlcml/norma";
	private static String ARGS_RESOURCE = RESOURCE_NAME_TOP+"/"+"args.xml";
	private static final VersionManager NORMA_VERSION_MANAGER = new VersionManager();
	

	public final static String DOCTYPE = "!DOCTYPE";
	// options
	private List<StringPair> charPairList;
	private List<String> divList;
	private List<StringPair> namePairList;
	private Pubstyle pubstyle;
	private List<String> stripList;
	private String tidyName;
	private List<String> xslNameList;
	private boolean removeDTD;
	private NormaTransformer normaTransformer;
	private List<SectionTagger> sectionTaggerNameList;

	public NormaArgProcessor() {
		super();
		this.readArgumentOptions(this.getArgsResource());
	}
	
	public static VersionManager getVersionManager() {
//		LOG.debug("VM Norma "+NORMA_VERSION_MANAGER.hashCode()+" "+NORMA_VERSION_MANAGER.getName()+";"+NORMA_VERSION_MANAGER.getVersion());
		return NORMA_VERSION_MANAGER;
	}
	
	public NormaArgProcessor(String args) {
		this(args == null ? null : args.replaceAll("\\s+", " ").split(" "));
	}

	public NormaArgProcessor(String[] args) {
		this();
		setDefaults();
		parseArgs(args);
	}

	private void setDefaults() {
		this.removeDTD = true;
	}

	private String getArgsResource() {
		return ARGS_RESOURCE;
	}
	
	// ============= METHODS =============

 	public void parseChars(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		charPairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parseDivs(ArgumentOption option, ArgIterator argIterator) {
		divList = argIterator.createTokenListUpToNextNonDigitMinus(option);
	}

	public void parseHtml(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		tidyName = option.processArgs(tokens).getStringValue();
		LOG.trace("HTML: "+tidyName);
	}

	public void parseNames(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		namePairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parsePubstyle(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		if (tokens.size() == 0) {
			stripList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = option.processArgs(tokens).getStringValue();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	public void parseTag(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		sectionTaggerNameList = new ArrayList<SectionTagger>();
		if (tokens.size() == 0) {
			LOG.warn("parseTag requires list of taggers");
		} else {
			for (String token : tokens) {
				SectionTagger sectionTagger = new SectionTagger(token);
				sectionTaggerNameList.add(sectionTagger);
			}
		}
		
	}

	public void parseStandalone(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		try {
			removeDTD = tokens.size() == 1 ? new Boolean(tokens.get(0)) : false;
		} catch (Exception e) {
			throw new RuntimeException("bad boolean: "+tokens.get(0));
		}
	}

	public void parseStrip(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		if (tokens.size() == 0) {
			stripList = new ArrayList<String>();
		} else {
			stripList = tokens;
		}
	}

	public void parseTidy(ArgumentOption option, ArgIterator argIterator) {
		LOG.debug("parseTidy DEPRECATED; use parseHtml");
		parseHtml(option, argIterator);
	}

	public void parseTransform(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		List<String> tokenList = option.processArgs(tokens).getStringValues();
		getOrCreateNormaTransformer();
		List<ValueElement> valueElements = option.getValueElements();
		for (ValueElement valueElement : valueElements) {
			LOG.trace("value "+valueElement.getName());
		}
		normaTransformer.parseTransform(this, tokenList);
	}

	/** deprecated // use transform instead
	 *
	 * @param option
	 * @param argIterator
	 */
	public void parseXsl(ArgumentOption option, ArgIterator argIterator) {
		LOG.warn("option --xsl is deprecated); use --transform instead");
		parseTransform(option, argIterator);
	}
	
	

	// ===========run===============

	public void transform(ArgumentOption option) {
		// deprecated so
		runTransform(option);
	}

	public void runTransform(ArgumentOption option) {
		if (currentCMDir == null) {
			LOG.warn("No current CMDir");
		} else {
			LOG.trace("***run transform "+currentCMDir);
			getOrCreateNormaTransformer();
			normaTransformer.transform(option);
		}
	}

	public void runHtml(ArgumentOption option) {
		LOG.trace("***run html "+currentCMDir);
		HtmlCleaner htmlCleaner = new HtmlCleaner(this);
		HtmlElement htmlElement = htmlCleaner.cleanHTML2XHTML(option.getStringValue());
		if (htmlElement == null) {
			LOG.error("Cannot parse HTML");
			return;
		}
		if (output != null) {
			currentCMDir.writeFile(htmlElement.toXML(), output);
		}
	}

	private NormaTransformer getOrCreateNormaTransformer() {
		if (normaTransformer == null) {
			normaTransformer = new NormaTransformer(this);
		}
		return normaTransformer;
	}

	public void runTest(ArgumentOption option) {
		LOG.debug("RUN_TEST "+"is a dummy");
	}


	// =============output=============
	public void outputMethod(ArgumentOption option) {
		outputSpecifiedFormat();
	}

	protected void printVersion() {
		getVersionManager().printVersion();
		DefaultArgProcessor.getVersionManager().printVersion();
	}


	private void outputSpecifiedFormat() {
		getOrCreateNormaTransformer();
		normaTransformer.outputSpecifiedFormat();
	}

	void writeImages() {
		File imageDir = currentCMDir.getOrCreateExistingImageDir();
		Set<String> imageSerialSet = new HashSet<String>();
		StringBuilder sb = new StringBuilder();
		for (NamedImage serialImage : normaTransformer.serialImageList) {
			try {
				String serialString = serialImage.getKey();
				String imageSerial = serialString.split("\\.")[3];
				sb.append(serialString);
				if (!imageSerialSet.contains(imageSerial)) {
					File imageFile = new File(imageDir, serialString+DOT_PNG);
					ImageIO.write(serialImage.getImage(), PNG, imageFile);
					imageSerialSet.add(imageSerial);
				}
			} catch (IOException e) {
				throw new RuntimeException("Cannot write image ", e);
			}
		}
	}

	// ==========================


	public File checkAndGetInputFile(CMDir cmDir) {
		if (cmDir == null) {
			throw new RuntimeException("null cmDir");
		}
		String inputName = getString();
		if (inputName == null) {
			throw new RuntimeException("Must have single input option");
		}
		if (!CMDir.isReservedFilename(inputName) && !CMDir.hasReservedParentDirectory(inputName) ) {
			throw new RuntimeException("Input must be reserved file; found: "+inputName);
		}
		File inputFile = cmDir.getExistingReservedFile(inputName);
		if (inputFile == null) {
			inputFile = cmDir.getExistingFileWithReservedParentDirectory(inputName);
		}
		if (inputFile == null) {
			throw new RuntimeException("Could not find input file "+inputName+" in directory "+cmDir.getDirectory());
		}
		return inputFile;
	}

	private void createCMDirListFromInputList() {
		// proceed unless there is a single reserved file for input
		if (CMDir.isNonEmptyNonReservedInputList(inputList)) {
			LOG.trace("CREATING CMDir FROM INPUT:"+inputList);
			// this actually creates directory
			getOrCreateOutputDirectory();
			ensureCMDirList();
			createNewCMDirsAndCopyOriginalFilesAndAddToList();
		}
	}

	private void createNewCMDirsAndCopyOriginalFilesAndAddToList() {
		ensureCMDirList();
		for (String filename : inputList) {
			try {
				CMDir cmDir = createCMDirAndCopyFileOrMakeSubDirectory(filename);
				if (cmDir != null) {
					cmDirList.add(cmDir);
				}
			} catch (IOException e) {
				LOG.error("Failed to create CMDir: "+filename+"; "+e);
			}
		}
	}

	private CMDir createCMDirAndCopyFileOrMakeSubDirectory(String filename) throws IOException {
		CMDir cmDir = null;
		File file = new File(filename);
		if (file.isDirectory()) {
			LOG.error("should not have any directories in inputList: "+file);
		} else {
			if (output != null) {
				String name = FilenameUtils.getName(filename);
				if (CMDir.isReservedFilename(name)) {
					LOG.error(name+" is reserved for CMDir: (check that inputs are not already in a CMDir) "+file.getAbsolutePath());
				}
				String cmFilename = CMDir.getCMDirReservedFilenameForExtension(name);
				if (cmFilename == null) {
					LOG.error("Cannot create CMDir from this type of file: "+name);
					return null;
				}
				LOG.trace("Reserved filename: "+cmFilename);
				if (CMDir.isReservedDirectory(cmFilename)) {
					cmDir = makeCMDir(name);
					ensureReservedDirectoryAndCopyFile(cmDir, cmFilename, filename);
				} else {
					cmDir = makeCMDir(name);
					File destFile = cmDir.getReservedFile(cmFilename);
					if (destFile != null) {
						FileUtils.copyFile(file, destFile);
					}
				}
			}
		}
		return cmDir;
	}

	private CMDir makeCMDir(String name) {
		CMDir cmDir;
		String dirName = FilenameUtils.removeExtension(name);
		cmDir = createCMDir(dirName);
		return cmDir;
	}

	private void ensureReservedDirectoryAndCopyFile(CMDir cmDir, String reservedFilename, String filename) {
		File reservedDir = new File(cmDir.getDirectory(), reservedFilename);
		LOG.trace("Res "+reservedDir.getAbsolutePath());
		File orig = new File(filename);
		LOG.trace("Orig: "+orig.getAbsolutePath());
		try {
			FileUtils.forceMkdir(reservedDir);
		} catch (IOException e) {
			throw new RuntimeException("Cannot make directory: "+reservedFilename+" "+e);
		}
		String name = FilenameUtils.getName(filename);
		try {
			File outFile = new File(reservedDir, name);
			FileUtils.copyFile(new File(filename), outFile);
		} catch (IOException e) {
			throw new RuntimeException("Cannot copy file: "+filename+" to "+reservedDir, e);
		}
		LOG.trace("created "+name+" in "+reservedDir);

	}

	private CMDir createCMDir(String dirName) {
		File cmDirFile = new File(output, dirName);
		CMDir cmDir = new CMDir(cmDirFile);
		cmDir.createDirectory(cmDirFile, false);
		return cmDir;
	}

	private void getOrCreateOutputDirectory() {
		if (output != null) {
			File outputDir = new File(output);
			if (outputDir.exists()) {
				if (!outputDir.isDirectory()) {
					throw new RuntimeException("cmDirRoot "+outputDir+" must be a directory");
				}
			} else {
				outputDir.mkdirs();
			}
		}
	}


	// ==========================

	public Pubstyle getPubstyle() {
		return pubstyle;
	}

	public List<String> getStylesheetNameList() {
		return xslNameList;
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

	public boolean isStandalone() {
		return removeDTD;
	}

	public List<SectionTagger> getSectionTaggers() {
		return sectionTaggerNameList;
	}

	@Override
	/** parse args and resolve their dependencies.
	 *
	 * (don't run any argument actions)
	 *
	 */
	public void parseArgs(String[] args) {
		super.parseArgs(args);
		createCMDirListFromInputList();
	}

	public CMDir getCurrentCMDir() {
		return currentCMDir;
	}

}
