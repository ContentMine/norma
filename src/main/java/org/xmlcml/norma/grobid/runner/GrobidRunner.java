package org.xmlcml.norma.grobid.runner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.grobid.core.main.batch.GrobidMain;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.CTreeList;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.norma.grobid.vec.GrobidVecElement;
import org.xmlcml.xml.XMLUtil;

public class GrobidRunner {
	private static final Logger LOG = Logger.getLogger(GrobidRunner.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String EXE = " -exe";
	private static final Object GH = " -gH";
	private static final Object DIN = " -dIn";
	private static final Object DOUT = " -dOut";

	public static final String IMAGE_VEC_REGEX = ".*/image\\-\\d+\\.vec";
	public static final String IMAGE_PNG_REGEX = ".*/image\\-\\d+\\.png";
	public static final String FULLTEXT_TEI_XML = "fulltext.tei.xml";
	public static final String TEI_XML = ".tei.xml";
	public static final String DOT_XML = CTree.DOT + CTree.XML;
	public static final String DOT_SVG = CTree.DOT + CTree.SVG;
	
	public static File GROBID_HOME = null;
	static {
		try {
			GROBID_HOME = new File(new File("."), "../grobid-grobid-parent-0.4.1/grobid-home").getCanonicalFile();
		} catch (IOException e) {
			LOG.error("Cannot create GROBID_HOME");
			e.printStackTrace();
		}
	}

	private File grobidHome;
	private File inputDir;
	private File outputDir;
	private List<GrobidOption> grobidOptions;
	private String cmd;
	private CProject cProject;

	public GrobidRunner() {
		this(GROBID_HOME);
	}
	
	public GrobidRunner(File grobidHome) {
		this.grobidHome = grobidHome;
	}

	public void setInputDirectory(File inputDir) {
		this.inputDir = inputDir;
	}

	public void setOutputDirectory(File outputDir) {
		this.outputDir = outputDir;
	}

	public void setOptions(List<GrobidOption> grobidOptions) {
		this.grobidOptions = new ArrayList<GrobidOption>(grobidOptions);
	}

	public void run() throws Exception {
		StringBuilder sb = new StringBuilder();
		if (grobidOptions == null || grobidOptions.size() == 0) {
			throw new RuntimeException("No grobid commands given");
		}
		if (grobidHome == null) {
			throw new RuntimeException("No grobid.home directory given");
		}
		if (inputDir == null) {
			throw new RuntimeException("No input directory given");
		}
		if (outputDir == null) {
			outputDir = inputDir;
			LOG.info("No output directory given; using inputDir: "+outputDir);
		}
		cmd = makeCommandLine(sb);
		String[] args = cmd.split("\\s+");
		GrobidMain.main(args);
	}

	private String makeCommandLine(StringBuilder sb) {
		sb.append(EXE);
		for (GrobidOption grobidOption : grobidOptions) {
			sb.append(" "+grobidOption.getOption());
		}
		sb.append(GH);
		sb.append(" "+grobidHome);
		sb.append(DIN);
		sb.append(" "+inputDir);
		sb.append(DOUT);
		sb.append(" "+outputDir);
		cmd = sb.toString();
		return cmd;
	}

	public CProject createProject(File cProjectDir) throws IOException {
		List<File> pdfFiles = new RegexPathFilter(".*\\.pdf").listNonDirectoriesRecursively(cProjectDir);
		cProject = new CProject(cProjectDir);
		for (File pdfFile : pdfFiles) {
			CTree currentTree = createCTreeFromPDFFile(pdfFile);
			if (currentTree != null) {
				cProject.add(currentTree);
			}
		}
		return cProject;
	}

	public CTree createCTreeFromPDFFile(File pdfFile) throws IOException {
		String cTreeName = FilenameUtils.getBaseName(pdfFile.getName());
		CTree cTree = null;
		File cProjectDir = pdfFile.getParentFile();
		File protoCTreeFile = getDirWithXmlFile(cProjectDir, cTreeName);
		if (protoCTreeFile != null) {
			File cTreeFile = new File(cProjectDir, cTreeName);
			cTree = new CTree(cTreeFile);
			moveOrDeleteGrobidFiles(pdfFile, cTreeName, cProjectDir, protoCTreeFile, cTreeFile);
			convertVecToSVG();
		}
		return cTree;
	}

	private void convertVecToSVG() {
		CTreeList cTreeList = cProject.getOrCreateCTreeList();
		for (CTree cTree : cTreeList) {
			convertVecToSVG(cTree);
		}
	}

	private void convertVecToSVG(CTree cTree) {
		List<File> vecFiles = new RegexPathFilter(".*/svg/image\\-\\d+\\.vec").listNonDirectoriesRecursively(cTree.getDirectory());
		for (File vecFile : vecFiles) {
			if (FileUtils.sizeOf(vecFile) > 1000000) {
				System.out.println("Large file: "+vecFile);
				continue;
			}
			File svgFile = null;
			try {
				svgFile = convertVecToSVGAndWrite(vecFile, cTree.getDirectory());
			} catch (IOException e) {
				LOG.error("Could not write file: "+svgFile);
				continue;
			}
		}
	}

	private File convertVecToSVGAndWrite(File vecFile, File directory) throws IOException {
		String baseName = FilenameUtils.getBaseName(vecFile.getName());
		File svgFile = new File(directory, baseName + DOT_SVG);
		System.out.println("PMR reading "+vecFile.getAbsolutePath());
		GrobidVecElement grobidVec = GrobidVecElement.readFile(vecFile);
		System.out.println("PMR converting "+grobidVec);
		SVGElement svgElement = grobidVec.createSVG();
		System.out.println("PMR writing "+svgElement);
		XMLUtil.debug(svgElement, svgFile, 1);
		return svgFile;
	}

	private void moveOrDeleteGrobidFiles(File pdfFile, String cTreeName, File cProjectDir, File protoCTreeFile, File cTreeFile)
			throws IOException {
		FileUtils.moveDirectory(protoCTreeFile, cTreeFile);
		FileUtils.moveFile(pdfFile, new File(cTreeFile, CTree.FULLTEXT_PDF));
		FileUtils.moveFile(new File(cTreeFile, cTreeName + GrobidRunner.DOT_XML), new File(cTreeFile, GrobidRunner.FULLTEXT_TEI_XML));
		FileUtils.deleteQuietly(new File(cProjectDir, cTreeName + GrobidRunner.TEI_XML));
		moveFiles(cTreeFile, GrobidRunner.IMAGE_PNG_REGEX, CTree.IMAGES);
		moveFiles(cTreeFile, GrobidRunner.IMAGE_VEC_REGEX, CTree.SVG);
	}

	public File getDirWithXmlFile(File cProjectFile, String pdfRoot) {
		File[] dirs = cProjectFile.listFiles();
		File dir0 = null;
		for (File dir : dirs) {
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (File file : files) {
					if (file.getName().equals(pdfRoot + GrobidRunner.DOT_XML)) {
						dir0 = dir;
						break;
					}
				}
			}
		}
		return dir0;
	}

	public void moveFiles(File cTreeFile, String regex, String newDirName) throws IOException {
		List<File> files = new RegexPathFilter(regex).listNonDirectoriesRecursively(cTreeFile);
		if (files.size() > 0) {
			File newDir = new File(cTreeFile, newDirName);
			for (File imageFile : files) {
				FileUtils.moveFileToDirectory(imageFile, newDir, true);
			}
		}
	}

}
