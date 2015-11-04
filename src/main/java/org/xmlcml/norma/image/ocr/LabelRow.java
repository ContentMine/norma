package org.xmlcml.norma.image.ocr;

import java.util.ArrayList;
import java.util.List;

public class LabelRow {

	private List<HOCRLabel> labels;
	
	public LabelRow() {
		labels = new ArrayList<HOCRLabel>();
	}

	public boolean contains(HOCRChunk label) {
		return labels.contains(label);
	}
	
	public void add(HOCRLabel label) {
		if (!contains(label)) {
			labels.add(label);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (HOCRChunk label : labels) {
			sb.append(label+" ");
		}
		return sb.toString();
	}
	
}
