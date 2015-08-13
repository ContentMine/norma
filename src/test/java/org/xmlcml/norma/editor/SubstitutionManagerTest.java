package org.xmlcml.norma.editor;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.graphics.svg.SVGRect;
import org.xmlcml.graphics.svg.text.SVGWord;

public class SubstitutionManagerTest {

	public static final Logger LOG = Logger.getLogger(SubstitutionManagerTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testCreateSubstitutionManager() {
		SubstitutionManager substitutionManager = new SubstitutionManager();
		substitutionManager.addSubstitution(new Substitution("a", "b"));
		Substitution subA = substitutionManager.get("a");
		Assert.assertNotNull(subA);
		Assert.assertEquals("b", subA.getEdited());
	}
	
	
	@Test
	public void testSubstituteString() {
		String target = "acdaf";
		SubstitutionManager substitutionManager = new SubstitutionManager();
		substitutionManager.addSubstitution(new Substitution("a", "b"));
		substitutionManager.addSubstitution(new Substitution("f", "pqr"));
		String wordValue = substitutionManager.applySubstitutions(target);
		Assert.assertEquals("bcdbpqr", wordValue);
		Assert.assertEquals("a=>b a=>b f=>pqr", substitutionManager.getEditRecord().toString());
	}
	
	@Test
	public void testSubstituteWord() {
		SVGWord svgWord = new SVGWord();
		String wordValue = "acdaf";
		SVGRect rect = new SVGRect();
		SubstitutionManager substitutionManager = new SubstitutionManager();
		substitutionManager.addSubstitution(new Substitution("a", "b"));
		wordValue = substitutionManager.applySubstitutions(svgWord, wordValue, rect);
		Assert.assertEquals("bcdbf", wordValue);
		Assert.assertEquals("a=>b a=>b", substitutionManager.getEditRecord().toString());
	}

}
