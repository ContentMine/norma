package org.xmlcml.norma;

public class SVGConnector {

	private SVGTextBox headBox;
	private SVGTextBox tailBox;

	public SVGConnector(SVGTextBox tailBox, SVGTextBox headBox) {
		this.headBox = headBox;
		this.tailBox = tailBox;
	}
	
	public String toString() {
		String s = tailBox.toString();
		s += "\n IS PARENT OF \n";
		s += headBox.toString();
		return s;
	}

}
