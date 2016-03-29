package org.xmlcml.norma.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.xmlcml.norma.sections.JATSPMCitation;

public class DottyPlotter {
	
	private static final String LINK_ARROW = "\" -> \"";
	private static final String QUOTE = "\"";
	private static final String DIGRAPH_START = "digraph ";
	private static final String DOT_START = " {\n";
	private static final String PARAM_START = "  graph [";
	private static final String NODESEP = " nodesep=";
	private static final String RANKSEP = " ranksep=";
	private static final String PARAM_END = "]\n";
	private static final String DOT_END = "}";

	private Double nodesep = 0.4;
	private Double ranksep = 3.7;
	private File outfile;
	private List<? extends DottyLink> linkList;
	private String title;

	/**
	digraph jatsrefs {
		  graph [nodesep=0.4 ranksep=3.7]
		"PMC4472270" -> "23563266"
		"PMC4553499" -> "23563266"
		"PMC4568054" -> "23563266"
	}
	*/

	public void createLinkGraph() throws IOException {
		StringBuilder sball = new StringBuilder();
		sball.append(DIGRAPH_START);
		sball.append(title);
		sball.append(DOT_START);
		sball.append(PARAM_START);
		sball.append(NODESEP);
		sball.append(nodesep);
		sball.append(RANKSEP);
		sball.append(ranksep);
		sball.append(PARAM_END);
		for (DottyLink link : linkList) {
			sball.append(QUOTE);
			sball.append(link.getHead());
			sball.append(LINK_ARROW);
			sball.append(link.getTail());
			sball.append(QUOTE);
			sball.append("\n");
		}
		sball.append(DOT_END);
		FileUtils.write(outfile, sball.toString());
	}

	public void setNodesep(double d) {
		nodesep = d;
	}

	public void setRanksep(double d) {
		ranksep = d;
	}

	public void setOutputFile(File file) {
		this.outfile = file;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLinkList(List<JATSPMCitation> citationList) {
		this.linkList = citationList;
	}
}
