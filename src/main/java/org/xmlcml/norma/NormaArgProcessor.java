package org.xmlcml.norma;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.args.ArgIterator;
import org.xmlcml.cproject.args.ArgumentOption;
import org.xmlcml.cproject.args.DefaultArgProcessor;
import org.xmlcml.cproject.args.StringPair;
import org.xmlcml.cproject.args.ValueElement;
import org.xmlcml.cproject.args.VersionManager;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.image.ocr.NamedImage;
import org.xmlcml.norma.input.html.HtmlCleaner;
import org.xmlcml.norma.output.HtmlAggregate;
import org.xmlcml.norma.output.HtmlDisplay;
import org.xmlcml.norma.tagger.SectionTaggerX;

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

	public static String RESOURCE_NAME_TOP = "/org/xmlcml/norma";
	private static String ARGS_RESOURCE = RESOURCE_NAME_TOP+"/"+"args.xml";
	private static final VersionManager NORMA_VERSION_MANAGER = new VersionManager();
	

	public final static String DOCTYPE = "!DOCTYPE";
	// options
	private List<StringPair> charPairList;
	private List<String> divList;
	private List<StringPair> movePairList;
	private List<StringPair> renamePairList;
	private List<StringPair> namePairList;
	private Pubstyle pubstyle;
	private List<String> stripList;
	private String tidyName;
	private List<String> xslNameList;
	private boolean removeDTD;
	private NormaTransformer normaTransformer;
	private List<SectionTaggerX> sectionTaggerNameList;
	private HtmlElement cleanedHtmlElement;
	List<String> transformTokenList;
	private List<String> relabelStrings;
	private List<String> htmlDisplayFilters;
	private HtmlDisplay htmlDisplay;
	private List<String> htmlAggregatorFilters;
	private HtmlAggregate htmlAggregate;

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
		List<String> tokens = argIterator.getStrings(option);
		charPairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parseDivs(ArgumentOption option, ArgIterator argIterator) {
		divList = argIterator.getStrings(option);
	}

	public void parseHtml(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		tidyName = option.processArgs(tokens).getStringValue();
		LOG.trace("HTML: "+tidyName);
	}

	public void parseHtmlAggregate(ArgumentOption option, ArgIterator argIterator) {
		htmlAggregatorFilters = argIterator.getStrings(option);
		htmlAggregate = new HtmlAggregate(htmlAggregatorFilters);
		if (htmlAggregatorFilters.size() == 1) {
			fileFilterPattern = Pattern.compile(htmlAggregatorFilters.get(0));
			htmlAggregate.setFileFilterPattern(fileFilterPattern);
		}
	}

	public void parseHtmlDisplay(ArgumentOption option, ArgIterator argIterator) {
		htmlDisplay = new HtmlDisplay(argIterator.getStrings(option));
	}

	public void parseMove(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		movePairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parseNames(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		namePairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parsePubstyle(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		if (tokens.size() == 0) {
			stripList = new ArrayList<String>();
			Pubstyle.help();
		} else {
			String name = option.processArgs(tokens).getStringValue();
			pubstyle = Pubstyle.getPubstyle(name);
		}
	}

	public void parseRelabelContent(ArgumentOption option, ArgIterator argIterator) {
		relabelStrings = argIterator.getStrings(option);
	}

	public void parseRename(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		renamePairList = option.processArgs(tokens).getStringPairValues();
	}

	public void parseTag(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		sectionTaggerNameList = new ArrayList<SectionTaggerX>();
		if (tokens.size() == 0) {
			LOG.warn("parseTag requires list of taggers");
		} else {
			for (String token : tokens) {
				SectionTaggerX sectionTagger = new SectionTaggerX(token);
				sectionTaggerNameList.add(sectionTagger);
			}
		}
		
	}

	public void parseStandalone(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
		try {
			removeDTD = tokens.size() == 1 ? new Boolean(tokens.get(0)) : false;
		} catch (Exception e) {
			throw new RuntimeException("bad boolean: "+tokens.get(0));
		}
	}

	public void parseStrip(ArgumentOption option, ArgIterator argIterator) {
		List<String> tokens = argIterator.getStrings(option);
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
//		List<String> tokens = argIterator.createTokenListUpToNextNonDigitMinus(option);
		List<String> tokens = argIterator.getStrings(option);
		transformTokenList = option.processArgs(tokens).getStringValues();
		getOrCreateNormaTransformer();
		List<ValueElement> valueElements = option.getValueElements();
		for (ValueElement valueElement : valueElements) {
			LOG.trace("value "+valueElement.getName());
		}
//		normaTransformer.parseTransform(transformTokenList.get(0));
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
		boolean ok = false;
		if (currentCTree == null) {
			LOG.warn("No current CTree");
		} else {
			LOG.trace("***run transform on tree "+currentCTree);
			getOrCreateNormaTransformer();
			normaTransformer.setCurrentCTree(currentCTree);
			String transformTypeString = option.getStringValue();
			normaTransformer.clearVariables();
			normaTransformer.runTransform(transformTypeString);
		}
	}

	public void runHtml(ArgumentOption option) {
		String cleanerType = option.getStringValue();
		runHtmlCleaner(cleanerType);
	}

	public void runHtmlAggregate(ArgumentOption option) {
		LOG.debug("Aggregate----");
		if (htmlAggregate != null && currentCTree != null) {
			htmlAggregate.setCTree(currentCTree);
			htmlAggregate.setOutput(output);
			htmlAggregate.display();
		}
	}

	public void runHtmlDisplay(ArgumentOption option) {
		LOG.debug("Display----");
		if (htmlDisplay != null && currentCTree != null) {
			htmlDisplay.setCTree(currentCTree);
			htmlDisplay.setOutput(output);
			htmlDisplay.setFileFilterPattern(fileFilterPattern);
			htmlDisplay.display();
		}
	}

	void runHtmlCleaner(String cleanerType) {
		LOG.trace("***run html "+currentCTree);
		HtmlCleaner htmlCleaner = new HtmlCleaner(this);
		cleanedHtmlElement = htmlCleaner.cleanHTML2XHTML(cleanerType);
		if (cleanedHtmlElement == null) {
			LOG.error("Cannot parse HTML in: "+currentCTree.getDirectory().getAbsolutePath());
			return;
		}
		if (output != null) {
			currentCTree.writeFile(cleanedHtmlElement.toXML(), output);
		}
	}

	public void runCopy(ArgumentOption option) {
		LOG.trace("***run copy "+currentCTree);
		try {
			copyFile();
		} catch (IOException e) {
			throw new RuntimeException("cannot copy file", e);
		}
	}

	public void runMove(ArgumentOption option) {
		LOG.trace("***run move "+currentCTree.getDirectory());
		moveFiles();
	}

	public void runRelabelContent(ArgumentOption option) {
		if (currentCTree == null) {
			LOG.warn("No current CTree");
		}

		LOG.trace("***run relabel "+currentCTree);
		relabelContent();
	}

	public void runRename(ArgumentOption option) {
		LOG.trace("***run rename "+currentCTree);
		renameFiles();
	}

	private void moveFiles() {
		if (movePairList == null) {
			throw new RuntimeException("must give parseMove");
		}
		if (currentCTree == null) {
			throw new RuntimeException("no current CTree");
		}
		for (StringPair movePair : movePairList) {
			final String suffix = movePair.left;
			String toDir = movePair.right;
			File directory = currentCTree.getDirectory();
			File to = new File(directory, toDir);
			File[] files = directory.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return suffix.equals(FilenameUtils.getExtension(name));
				}
			});
			if (files != null) {
				for (File file : files) {
					try {
						FileUtils.moveFileToDirectory(file, to, true);
					} catch (IOException e) {
						LOG.warn("cannot move file: "+file+"; "+e);
					}
				}
			}
		}
	}

	private void relabelContent() {
		if (relabelStrings == null) {
			throw new RuntimeException("must give parseRelabel");
		}
		for (String relabelFilename : relabelStrings) {
			File relabelFile = new File(currentCTree.getDirectory(), relabelFilename);
			if (relabelFile.exists()) {
				String content = NormaUtil.getStringFromInputFile(relabelFile);
				if (content != null) {
					if (false) {
					} else if (relabelFilename.equals(CTree.FULLTEXT_PDF) && !NormaUtil.isPDFContent(content)) {
						if (NormaUtil.isHtmlContent(content)) {
							relabelContent(relabelFile, content, CTree.FULLTEXT_HTML);
						}
					} else if (relabelFilename.equals(CTree.FULLTEXT_HTML) && !NormaUtil.isHtmlContent(content)) {
						if (NormaUtil.isPDFContent(content)) {
							relabelContent(relabelFile, content, CTree.FULLTEXT_PDF);
						}
					}
				}
			}
		}
	}
	
	private boolean relabelContent(File oldFile, String content, String filenameType) {
		File newFile = new File(oldFile.getParentFile(), filenameType);
		try {
			FileUtils.copyFile(oldFile, newFile, true);
			FileUtils.forceDelete(oldFile);
		} catch (IOException e) {
			throw new RuntimeException("Cannot rename file: "+oldFile+" => "+newFile, e);
		}
		return false;
	}

	private void renameFiles() {
		if (renamePairList == null) {
			throw new RuntimeException("must give parseRename");
		}
		for (StringPair namePair : renamePairList) {
			File from = new File(currentCTree.getDirectory(), namePair.left);
			File to = new File(currentCTree.getDirectory(), namePair.right);
			try {
				FileUtils.copyFile(from, to, true);
				FileUtils.forceDelete(from);
			} catch (IOException e) {
				throw new RuntimeException("Cannot rename file: "+from+" => "+to, e);
			}
		}
	}

	private void copyFile() throws IOException {
		if (namePairList == null) {
			throw new RuntimeException("must give parseMove");
		}
		for (StringPair namePair : namePairList) {
			File from = new File(currentCTree.getDirectory(), namePair.left);
			File to = new File(currentCTree.getDirectory(), namePair.right);
			FileUtils.copyFile(from, to);
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
		File imageDir = currentCTree.getOrCreateExistingImageDir();
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


	public File checkAndGetInputFile(CTree cTree) {
		File inputFile = null;
		if (cTree == null) {
			throw new RuntimeException("null cTree");
		}
		String inputName = getSingleInputName();
		if (inputName == null) {
			throw new RuntimeException("Must have single input option");
		}
		if (inputName != null) {
			if (!CTree.isReservedFilename(inputName) && !CTree.hasReservedParentDirectory(inputName) ) {
				throw new RuntimeException("Input must be reserved file; found: "+inputName);
			}
			inputFile = cTree.getExistingReservedFile(inputName);
			if (inputFile == null) {
				inputFile = cTree.getExistingFileWithReservedParentDirectory(inputName);
			}
			if (inputFile == null) {
				String msg = "Could not find input file "+inputName+" in directory "+cTree.getDirectory();
				TREE_LOG().error(msg);
				System.err.print("!");
	//			throw new RuntimeException(msg);
			}
		}
		return inputFile;
	}

	private void createCTreeListFromInputList() {
		// proceed unless there is a single reserved file for input
		if (CTree.isNonEmptyNonReservedInputList(inputList)) {
			LOG.debug("CREATING CTree FROM INPUT:"+inputList+"; FIX THIS, BAD STRATEGY");
			// this actually creates directory
			getOrCreateOutputDirectory();
			ensureCTreeList();
			createNewCTreesAndCopyOriginalFilesAndAddToList();
		}
	}

	private void createNewCTreesAndCopyOriginalFilesAndAddToList() {
		ensureCTreeList();
		for (String filename : inputList) {
			try {
				CTree cTree = createCTreeAndCopyFileOrMakeSubDirectory(filename);
				if (cTree != null) {
					cTreeList.add(cTree);
				}
			} catch (IOException e) {
				LOG.error("Failed to create CTree: "+filename+"; "+e);
			}
		}
	}

	private CTree createCTreeAndCopyFileOrMakeSubDirectory(String filename) throws IOException {
		CTree cTree = null;
		File file = new File(filename);
		if (file.isDirectory()) {
			this.PROJECT_LOG().error("should not have any directories in inputList: "+file);
		} else {
			if (output != null) {
				String name = FilenameUtils.getName(filename);
				if (CTree.isReservedFilename(name)) {
					this.PROJECT_LOG().info(name+" is reserved for CTree: (check that inputs are not already in a CTree) "+file.getAbsolutePath());
				}
				String cmFilename = CTree.getCTreeReservedFilenameForExtension(name);
				if (cmFilename == null) {
					this.PROJECT_LOG().error("Cannot create CTree from this type of file: "+name);
					return null;
				}
				LOG.trace("Reserved filename: "+cmFilename);
				if (CTree.isReservedDirectory(cmFilename)) {
					cTree = makeCTree(name);
					ensureReservedDirectoryAndCopyFile(cTree, cmFilename, filename);
				} else {
					cTree = makeCTree(name);
					File destFile = cTree.getReservedFile(cmFilename);
					if (destFile != null) {
						FileUtils.copyFile(file, destFile);
					}
				}
			}
		}
		return cTree;
	}

	private CTree makeCTree(String name) {
		CTree cTree;
		String dirName = FilenameUtils.removeExtension(name);
		cTree = createCTree(dirName);
		return cTree;
	}

	private void ensureReservedDirectoryAndCopyFile(CTree cTree, String reservedFilename, String filename) {
		File reservedDir = new File(cTree.getDirectory(), reservedFilename);
		LOG.debug("Res "+reservedDir.getAbsolutePath());
		File orig = new File(filename);
		LOG.debug("Orig: "+orig.getAbsolutePath());
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

	private CTree createCTree(String dirName) {
		File cTreeFile = new File(output, dirName);
		CTree cTree = new CTree(cTreeFile);
		cTree.createDirectory(cTreeFile, false);
		return cTree;
	}

	private void getOrCreateOutputDirectory() {
		if (output != null) {
			File outputDir = new File(output);
			if (outputDir.exists()) {
				if (!outputDir.isDirectory()) {
					throw new RuntimeException("cTreeRoot "+outputDir+" must be a directory");
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

	public List<SectionTaggerX> getSectionTaggers() {
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
		createCTreeListFromInputList();
	}

	public CTree getCurrentCMTree() {
		return currentCTree;
	}

	/** created by Tidy.
	 * 
	 * @return
	 */
	public HtmlElement getCleanedHtmlElement() {
		return cleanedHtmlElement;
	}

}
