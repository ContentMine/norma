package org.xmlcml.norma;

public class Prototypes {

	public static void main(String[] args) {
//		runHalThesis1();
//		runHalThesis2();
//		runHalThesis3();
		runHalManyThesis();
	}

	private static void runHalThesis1() {
		new Norma().run("-i examples/theses/HalThesis1.pdf -o examples/theses/");
		new Norma().run("-q examples/theses/HalThesis1 -i fulltext.pdf -o fulltext.pdf.txt --xsl pdf2txt");
	}

	private static void runHalThesis2() {
		new Norma().run("-i examples/theses/HalThesis2.pdf -o examples/theses/");
		new Norma().run("-q examples/theses/HalThesis2 -i fulltext.pdf -o fulltext.pdf.txt --xsl pdf2txt");
	}
	private static void runHalThesis3() {
		new Norma().run("-i examples/theses/These_Nathalie_Mitton.pdf -o examples/theses/");
		new Norma().run("-q examples/theses/These_Nathalie_Mitton -i fulltext.pdf -o fulltext.pdf.txt --xsl pdf2txt");
	}
	
	private static void runHalManyThesis() {
		createPDFTXT("These_Nathalie_Mitton");
		createPDFTXT("Thesis_Calligari");
		createPDFTXT("20130912_Fei_YAO");
		createPDFTXT("HalThesis2");
		createPDFTXT("smigaj");
		createPDFTXT("TH2013PEST1177");
	}

	private static void createPDFTXT(String name) {
		new Norma().run("-i examples/theses/"+name+".pdf -o examples/theses/");
		new Norma().run("-q examples/theses/"+name+" -i fulltext.pdf -o fulltext.pdf.txt --xsl pdf2txt");
	}
}
