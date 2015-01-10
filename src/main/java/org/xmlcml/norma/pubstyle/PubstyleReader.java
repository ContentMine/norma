package org.xmlcml.norma.pubstyle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.RawInput;
import org.xmlcml.norma.input.InputReader;
import org.xmlcml.xml.XMLUtil;

public abstract class PubstyleReader {

	private URL url;
	private RawInput rawInput;
	private InputReader inputReader;
	private File file;
	private HtmlElement htmlElement;
	
	protected PubstyleReader() {
		setDefaults();
	}

	public PubstyleReader(InputFormat type) {
		this();
		setFormat(type);
	}

	public void setFormat(InputFormat type) {
		inputReader = InputReader.createReader(type);
	}

	private void setDefaults() {
	}

	public InputReader getInputReader() {
		return this.inputReader;
	}
	
	public void readURL(String urlString) throws Exception {
		if (inputReader != null && urlString != null) {
			url = new URL(urlString);
			InputStream inputStream = url.openStream();
			this.rawInput = inputReader.read(inputStream);
		}
	}

	public void readFile(File file) throws Exception {
		if (inputReader != null && file != null) {
			this.file = file;
			InputStream inputStream = new FileInputStream(file);
			this.rawInput = inputReader.read(inputStream);
		}
	}

	public RawInput getRawInput() {
		return this.rawInput;
	}

	public HtmlElement getOrCreateXHtmlFromRawHtml() throws Exception {
		byte[] rawBytes = (rawInput == null) ? null : rawInput.getRawBytes();
		if (rawBytes != null) {
			HtmlFactory htmlFactory = new HtmlFactory();
			ByteArrayInputStream bais = new ByteArrayInputStream(rawBytes);
			// see if it's well-formed to start with... // poor strategy
//			nu.xom.Document document = XMLUtil.parseQuietlyToDocument(bais);
//			if (document != null) {
//				htmlElement = htmlFactory.parse(document.getRootElement());
//			} else {
				htmlElement = htmlFactory.parse(bais);
//			}
		}
		return htmlElement;
	}
}
