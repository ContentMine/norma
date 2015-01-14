package org.xmlcml.norma;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nu.xom.Nodes;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.norma.pubstyle.DefaultPubstyleReader;
import org.xmlcml.norma.pubstyle.PubstyleReader;

/** wraps the input, optionally determing its type.
 * 
 * @author pm286
 *
 */
public class InputWrapper {

	
	private static final Logger LOG = Logger.getLogger(InputWrapper.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private String inputName;
	private URL url;
	private File file;
	private String content;
	private Pubstyle pubstyle;
	private HtmlElement htmlElement;
	private InputFormat inputFormat;
	private PubstyleReader pubstyleReader;

	public InputWrapper(File file, String inputName) {
		this.file = file;
		this.inputName = inputName;
	}

	public InputWrapper(URL url, String inputName) {
		this.url = url;
		this.inputName = inputName;
	}

	public static List<InputWrapper> expandDirectories(File dir, List<String> extensions, boolean recursive) {
		List<InputWrapper> inputWrapperList = new ArrayList<InputWrapper>();
		Iterator<File> fileList = FileUtils.iterateFiles(dir, extensions.toArray(new String[0]), recursive);
		while (fileList.hasNext()) {
			inputWrapperList.add(new InputWrapper(fileList.next(), null));
		}
		return inputWrapperList;
	}

	public HtmlElement transform(Pubstyle pubstyle) throws Exception {
		this.pubstyle = pubstyle;
		findInputFormat();
		normalizeToXHTML(pubstyle);
		if (pubstyle == null) {
			pubstyle = Pubstyle.deducePubstyle(htmlElement);
		}
		if (pubstyle != null) {
			pubstyle.applyTagger(inputFormat, htmlElement);
		}
		return htmlElement;
	}


	/** maybe move to Pubstyle later.
	 * 
	 * @param pubstyle
	 */
	private void normalizeToXHTML(Pubstyle pubstyle) throws Exception {
		if (InputFormat.PDF.equals(inputFormat)) {
			try {
				PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
				htmlElement = converter.readAndConvertToXHTML(new File(inputName));
			} catch (Exception e) {
				throw new RuntimeException("cannot convert PDF: "+inputName, e);
			}
		} else if (InputFormat.SVG.equals(inputFormat)) {
			LOG.error("cannot turn SVG into XHTML yet");
		} else if (InputFormat.XML.equals(inputFormat)) {
			LOG.debug("using XML; not yet implemented");
		} else if (InputFormat.HTML.equals(inputFormat)) {
			readRawHTML(pubstyle);
		} else if (InputFormat.XHTML.equals(inputFormat)) {
			LOG.debug("using XHTML; not yet  implemented");
		} else {
			LOG.error("no processor found to convert "+inputName+" ("+inputFormat+") into XHTML yet");
		}
	}

	private void readRawHTML(Pubstyle pubstyle) throws Exception {
		LOG.trace("using HTML");
		pubstyleReader = (pubstyle == null) ? new DefaultPubstyleReader() : pubstyle.getPubstyleReader();
		pubstyleReader.setFormat(inputFormat);
		pubstyleReader.readFile(new File(inputName));
		// may need to go to this at some stage
//		HtmlUnitWrapper htmlUnitWrapper = new HtmlUnitWrapper();
//		HtmlElement htmlElement = htmlUnitWrapper.readAndCreateElement(url);

		htmlElement = pubstyleReader.getOrCreateXHtmlFromRawHtml();
		
		removeExtraneousHtmlTags();
		LOG.trace("read HTML");
	}

	private void removeExtraneousHtmlTags() {
		Nodes nodes = htmlElement.query( 
				"//*["
				+ "local-name()='script'"
				+ " or local-name()='link'"
				+ " or local-name()='object'"
				+ " or local-name()='iframe' "
				+ " or local-name()='fieldset' "
				+ " or local-name()='button' "
				+ " or local-name()='style' "
				+ " or @class='mobile-hidden' "
				+ " or @id='left-article-box' "
				+ " or @id='branding' "
				+ " or @id='pagehdr-wrap' "
				+ " or @id='topbanner' "
				+ " or @id='pageftr' "
				+ " or @class='sidebar' "
				+ "] "
				+ "| //comment()"
				+ "");
		for (int i = nodes.size()-1; i >= 0; i--) {
			nodes.get(i).detach();
		}
	}

	private void findInputFormat() {
		inputFormat = InputFormat.getInputFormat(inputName);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (file != null) sb.append(file);
		if (url != null) sb.append(url);
		return sb.toString();
	}
}
