package org.xmlcml.norma;

public class Connector {

	private SVGTextBox headBox;
	private SVGTextBox tailBox;

	public Connector(SVGTextBox tailBox, SVGTextBox headBox) {
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
