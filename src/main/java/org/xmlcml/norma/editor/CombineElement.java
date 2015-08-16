package org.xmlcml.norma.editor;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CombineElement extends AbstractEditorElement {

	private static final String TARGET = "target";
	private static final String SOURCE = "source";
	public static final Logger LOG = Logger.getLogger(CombineElement.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	public static final String TAG = "combine";

	public CombineElement() {
		super(TAG);
	}

	public void combine(List<Extraction> extractionList) {
		String source = this.getAttributeValue(SOURCE);
		String target = this.getAttributeValue(TARGET);
		if (source == null || target == null) {
			LOG.error("must give source and target");
			return;
		}
		String[] sources = source.trim().split("\\s+");
		if (sources.length < 2) {
			LOG.error("need at least 2 sources");
			return;
		}
		if (target.trim().equals("")) {
			LOG.error("need non empty target");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (String id : sources) {
			LOG.trace(">id>"+id);
			Extraction extraction = Extraction.find(extractionList, id);
			if (extraction == null) {
				LOG.error("cannot find extraction for: "+id);
				return;
			}
			extractionList.remove(extraction);
			sb.append(extraction.getValue());
		}
		Extraction replacementExtraction = new Extraction(target, sb.toString());
		extractionList.add(replacementExtraction);
		
		
		
	}

}
