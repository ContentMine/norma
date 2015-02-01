package org.xmlcml.norma.pubstyle.plosone;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.norma.InputFormat;
import org.xmlcml.norma.pubstyle.PubstyleReader;

public class PlosoneReader extends PubstyleReader {

	private static final Logger LOG = Logger.getLogger(PlosoneReader.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static List<String> EXTRANEOUS_XPATHS = Arrays.asList(new String[] {
		   "//*[local-name()='div' and @id='topbanner']", 
		   "//*[local-name()='div' and @id='pagehdr-wrap']", 
		   "//*[local-name()='div' and @id='nav-article']", 
		   "//*[local-name()='div' and @id='nav-article-page']", 
		   "//*[local-name()='div' and @id='figure-thmbs']", 
		   "//*[local-name()='div' and starts-with(normalize-space(.), 'Download:')]", 
		   "//*[local-name()='div' and @id='pageftr']", 
		   "//*[local-name()='div' and @id='subject-area-sidebar-block']", 
		   "//*[local-name()='div' and @class='sidebar']", 
		   
	});


	
	public PlosoneReader() {
		super();
	}

	public PlosoneReader(InputFormat type) {
		super(type);
	}

	@Override
	protected void addTaggers() {
//		this.addTagger(InputFormat.HTML, new HTMLPlosoneTagger());
//		this.addTagger(InputFormat.XML, new XMLPlosoneTagger());
	}
	
	@Override
	public List<String> getExtraneousXPaths() {
		return EXTRANEOUS_XPATHS;
	}

}
