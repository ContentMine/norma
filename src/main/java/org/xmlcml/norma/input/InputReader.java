package org.xmlcml.norma.input;

import java.io.IOException;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.RawInput;
import org.xmlcml.norma.image.ocr.HOCRReader;
import org.xmlcml.norma.input.html.HtmlReader;

public class InputReader {

	private static final Logger LOG = Logger.getLogger(InputReader.class);

	public static InputReader createReader(InputFormat type) {
		InputReader reader = null;
		if (type == null) {
			LOG.debug("no input type");
		} else if (type.equals(InputFormat.HTML)) {
			reader = new HtmlReader();
		} else if (type.equals(InputFormat.HOCR)) {
			reader = new HOCRReader();
		} else {
			throw new RuntimeException("Unknown/unsupported input type: "+type);
		}
		return reader;
	}

	public RawInput read(InputStream inputStream) throws IOException {
		byte[] rawBytes = IOUtils.toByteArray(inputStream);
		RawInput rawInput = new RawInput(rawBytes);
		return rawInput;
	}

}
