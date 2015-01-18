package org.xmlcml.norma;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.norma.pubstyle.DefaultPubstyleReader;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.xml.XMLUtil;

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
	public HtmlElement htmlElement;
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

	public HtmlElement transform(Pubstyle style) throws Exception {
		this.pubstyle = style;
		findInputFormat();
		LOG.debug("html "+htmlElement);
		normalizeToXHTML(pubstyle); // creates htmlElement
		LOG.debug(">> "+htmlElement.toXML());
		XMLUtil.debug(htmlElement, new FileOutputStream("target/norma.html"), 1);
		if (pubstyle == null) {
			this.pubstyle = Pubstyle.deducePubstyle(htmlElement);
		}
		if (pubstyle != null) {
			pubstyle.applyTagger(inputFormat, htmlElement);
		} else {
			LOG.debug("No pubstyle/s declared or deduced");
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

		pubstyleReader.getOrCreateXHtmlFromRawHtml();
		pubstyleReader.normalize();
		htmlElement = pubstyleReader.getHtmlElement();
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
