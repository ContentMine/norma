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

	/** combines members of extraction list.
	 * 
	 * @param extractionList
	 * @return 
	 * 
	 * @throws RuntimeException incorrect input (usually result of failed match)
	 */
	public void combine(List<Extraction> extractionList) throws RuntimeException {
		String source = this.getAttributeValue(SOURCE);
		String target = this.getAttributeValue(TARGET);
		if (source == null || target == null) {
			throw new RuntimeException("must give source and target");
		}
		String[] sources = source.trim().split("\\s+");
		if (sources.length < 2) {
			throw new RuntimeException("need at least 2 sources");
		}
		if (target.trim().equals("")) {
			throw new RuntimeException("need non empty target");
		}
		StringBuilder sb = new StringBuilder();
		for (String id : sources) {
			LOG.trace(">id>"+id);
			Extraction extraction = Extraction.find(extractionList, id);
			if (extraction == null) {
				throw new RuntimeException("cannot find extraction for: "+id);
			}
			extractionList.remove(extraction);
			sb.append(extraction.getValue());
		}
		Extraction replacementExtraction = new Extraction(target, sb.toString());
		extractionList.add(replacementExtraction);
		
		
		
	}

}
