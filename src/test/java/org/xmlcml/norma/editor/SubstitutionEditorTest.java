package org.xmlcml.norma.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.text.SVGWord;
import org.xmlcml.xml.XMLUtil;

import nu.xom.Element;

public class SubstitutionEditorTest {

	public static final Logger LOG = Logger.getLogger(SubstitutionEditorTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testCreateSubstitutionManager() {
		SubstitutionEditor substitutionManager = new SubstitutionEditor();
		substitutionManager.addSubstitution(new Substitution("a", "b"));
		Substitution subA = substitutionManager.get("a");
		Assert.assertNotNull(subA);
		Assert.assertEquals("b", subA.getEdited());
	}
	
		
	@Test
	@Ignore("Substitions are driven by pattern XML")
	public void testSubstituteWord() {
		SVGWord svgWord = new SVGWord();
		String wordValue = "acdaf";
		SVGRect rect = new SVGRect();
		SubstitutionEditor substitutionManager = new SubstitutionEditor();
		substitutionManager.addSubstitution(new Substitution("a", "b"));
		wordValue = substitutionManager.applySubstitutions(svgWord, wordValue, rect);
		Assert.assertEquals("bcdbf", wordValue);
		Assert.assertEquals("[a__b, a__b]", substitutionManager.getEditRecord().toString());
	}

	@Test
	/**
	 * NOTE - this tests and depends on the logic in speciesEditor.xml and code should be developed against this
	 * 
	 * @throws FileNotFoundException
	 */
	public void testCorrectNonMatchingStrings() throws FileNotFoundException {
		SubstitutionEditor substitutionEditor = new SubstitutionEditor();
		substitutionEditor.addEditor(new FileInputStream("src/test/resources/org/xmlcml/norma/editor/speciesEditor.xml"));
		Element mixedOtus = XMLUtil.parseQuietlyToDocument(new File("src/test/resources/org/xmlcml/norma/editor/mixedOtus.xml")).getRootElement();
		List<Element> otuList = XMLUtil.getQueryElements(mixedOtus, "/otus/otu");
		String[] original = {
	        "Bacillus subtilis 168 (NC_00964)",
	        "Lactoba",
	        "Desulfo",
	        "Proprinogenum modestus DSM 2376T (AJ307978)",
	        "Clostridium botulinum serotype e (M94261)",
	        "Streptococcus gordonii CH1 (NC_OO9785)",
	        "Jonquetella anthropi E3_33 (EU840722)",
	        "Pseudomonas aeruginosa PAO1 (NC_OO2516)",
	        "Thermotoga maritime MSBBT (NC_O00853)",
	        "Synechococcus elongatus PCC 6301 (NC_0O6576)",
	        "Mycobacterium tuberculosis H37Ra (NC_0O9525)",
	        "Ochrobactrum anthropi ATCC 49188T (NC_0O9667)",
	        "Fusobacterium nucleatum DSM 20482 (AJ307974)",
	        "Caulobacter crescentus CB15 (NC_0O2696)",
	        "Es",
	        "Borrelia burgdorferi B31T (NC_O01218)",
	        "Chlorobium tepidum TLST (NC_OO2932)",
	        "Finegoldia magna ATCC 29328 (NC_010376)",
	        "Bordetella pertussis Tohama (NC_0O2929)",
	        "Neisseria gonorrhoeae FA1090 (NC_002946)",
	        "Pyramidobacter piscolens W5455T (EU379932)",
	        "Haemophilus influenzae RdKW20 (U32697)",
	        "",
	        "Synergistes jonesii ATCC 49833T (EU840723)",
	        "Optiutus terrae PBQO-1T (NC_010571)",
	        "Porphyromonas gingivalis W83 (AEO15924)",
	        "Bacteroides fragi/is ATCC 252857 (NC_OO3228)",
	        "Aquifex aeolicus VF5 (NC_000918)",
	        "Bifidobacterium longum NCC2705 (NC_0O4307)",
	        "Rhodopirellula baltica SH 1T (NC_005027)",
	        "Mycoplasma pneumoniae M129 (NC_00O912)",
		};
		String[] values = new String[]{
			"Bacillus subtilis 168 NC_00964",
			"null",
			"null",
			"Proprinogenum modestus DSM 2376T AJ307978",
			"Clostridium botulinum serotype e M94261",
			"Streptococcus gordonii CH1 NC_009785",
			"Jonquetella anthropi E3_33 EU840722",
			"Pseudomonas aeruginosa PAO1 NC_002516",
			"Thermotoga maritime MSBBT NC_000853",
			"Synechococcus elongatus PCC 6301 NC_006576",
			"Mycobacterium tuberculosis H37Ra NC_009525",
			"Ochrobactrum anthropi ATCC 49188T NC_009667",
			"Fusobacterium nucleatum DSM 20482 AJ307974",
			"Caulobacter crescentus CB15 NC_002696",
			"null",
			"Borrelia burgdorferi B31T NC_001218",
			"Chlorobium tepidum TLST NC_002932",
			"Finegoldia magna ATCC 29328 NC_010376",
			"Bordetella pertussis Tohama NC_002929",
			"Neisseria gonorrhoeae FA1090 NC_002946",
			"Pyramidobacter piscolens W5455T EU379932",
			"Haemophilus influenzae RdKW20 U32697",
			"null",
			"Synergistes jonesii ATCC 49833T EU840723",
			"Optiutus terrae PBQO-1T NC_010571",
			"Porphyromonas gingivalis W83 AE015924",
			"Bacteroides fragilis ATCC 252857 NC_003228",
			"Aquifex aeolicus VF5 NC_000918",
			"Bifidobacterium longum NCC2705 NC_004307",
			"Rhodopirellula baltica SH 1T NC_005027",
			"Mycoplasma pneumoniae M129 NC_000912",
		};
		String[] edit = new String[]{
			"[]",
			"[]",
			"[]",
			"[]",
			"[]",
			"[O__0, O__0]",
			"[]",
			"[O__0, O__0]",
			"[O__0]",
			"[O__0]",
			"[O__0]",
			"[O__0]",
			"[]",
			"[O__0]",
			"[]",
			"[O__0]",
			"[O__0, O__0]",
			"[]",
			"[O__0]",
			"[]",
			"[]",
			"[]",
			"[]",
			"[]",
			"[]",
			"[O__0]",
			"[/__l, O__0, O__0]",
			"[]",
			"[O__0]",
			"[]",
			"[O__0]",
		};
		String[] extractions = {
			"[genus=Bacillus, species=subtilis, strain=168, id=NC_00964]",
			"[]",
			"[]",
			"[genus=Proprinogenum, species=modestus, strain=DSM 2376T, id=AJ307978]",
			"[genus=Clostridium, species=botulinum, strain=serotype e, id=M94261]",
			"[genus=Streptococcus, species=gordonii, strain=CH1, id=NC_009785]",
			"[genus=Jonquetella, species=anthropi, strain=E3_33, id=EU840722]",
			"[genus=Pseudomonas, species=aeruginosa, strain=PAO1, id=NC_002516]",
			"[genus=Thermotoga, species=maritime, strain=MSBBT, id=NC_000853]",
			"[genus=Synechococcus, species=elongatus, strain=PCC 6301, id=NC_006576]",
			"[genus=Mycobacterium, species=tuberculosis, strain=H37Ra, id=NC_009525]",
			"[genus=Ochrobactrum, species=anthropi, strain=ATCC 49188T, id=NC_009667]",
			"[genus=Fusobacterium, species=nucleatum, strain=DSM 20482, id=AJ307974]",
			"[genus=Caulobacter, species=crescentus, strain=CB15, id=NC_002696]",
			"[]",
			"[genus=Borrelia, species=burgdorferi, strain=B31T, id=NC_001218]",
			"[genus=Chlorobium, species=tepidum, strain=TLST, id=NC_002932]",
			"[genus=Finegoldia, species=magna, strain=ATCC 29328, id=NC_010376]",
			"[genus=Bordetella, species=pertussis, strain=Tohama, id=NC_002929]",
			"[genus=Neisseria, species=gonorrhoeae, strain=FA1090, id=NC_002946]",
			"[genus=Pyramidobacter, species=piscolens, strain=W5455T, id=EU379932]",
			"[genus=Haemophilus, species=influenzae, strain=RdKW20, id=U32697]",
			"[]",
			"[genus=Synergistes, species=jonesii, strain=ATCC 49833T, id=EU840723]",
			"[genus=Optiutus, species=terrae, strain=PBQO-1T, id=NC_010571]",
			"[genus=Porphyromonas, species=gingivalis, strain=W83, id=AE015924]",
			"[genus=Bacteroides, species=fragilis, strain=ATCC 252857, id=NC_003228]",
			"[genus=Aquifex, species=aeolicus, strain=VF5, id=NC_000918]",
			"[genus=Bifidobacterium, species=longum, strain=NCC2705, id=NC_004307]",
			"[genus=Rhodopirellula, species=baltica, strain=SH 1T, id=NC_005027]",
			"[genus=Mycoplasma, species=pneumoniae, strain=M129, id=NC_000912]",
		};
		for (int i = 0; i < original.length; i++) {
			String value = original[i];
			String newValue = substitutionEditor.createEditedValueAndRecord(value);
			EditList editRecord = substitutionEditor.getEditRecord();
			List<Extraction> extractionList = substitutionEditor.getExtractionList();
			LOG.trace(value+" => "+newValue+((editRecord == null || editRecord.size() == 0) ? "" :"; "+editRecord));
			Assert.assertEquals(i+"", values[i], String.valueOf(newValue));
			Assert.assertEquals(i+"", edit[i], String.valueOf(editRecord));
			Assert.assertEquals(i+"", extractions[i], String.valueOf(extractionList));
//			System.out.println("new value "+newValue+" / "+editRecord);
		}
	}

}
