package org.xmlcml.norma;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.args.DefaultArgProcessor;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.pubstyle.PubstyleReader;
import org.xmlcml.xml.XMLUtil;

public class Norma {

	private static final Logger LOG = Logger.getLogger(Norma.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private DefaultArgProcessor argProcessor;

	public static void main(String[] args) {
		Norma norma = new Norma();
		norma.run(args);
	}

	public void run(String[] args) {
		argProcessor = new NormaArgProcessor(args);
		argProcessor.runAndOutput();
	}

	public DefaultArgProcessor getArgProcessor() {
		return argProcessor;
	}
}
