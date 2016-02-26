package org.xmlcml.norma.biblio;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class BiblioParser {

	protected List<String> lines;

	public void read(InputStream is) throws IOException {
		lines = IOUtils.readLines(is);
		removeBOM();
	}

	private void removeBOM() {
		if (lines.size() > 0) {
			if (lines.get(0).length() > 0 && (int)lines.get(0).charAt(0) == 0xfeff) {
				lines.set(0, lines.get(0).substring(1));
			}
		}
	}

}
