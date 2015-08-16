package org.xmlcml.norma.editor;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nu.xom.Element;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.xml.XMLUtil;

public class AbstractEditorElementTest {
	
	public static final Logger LOG = Logger.getLogger(AbstractEditorElementTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testReadEditor() {
		InputStream is = this.getClass().getResourceAsStream("/org/xmlcml/norma/editor/speciesEditor.xml");
		EditorElement editorElement = (EditorElement) AbstractEditorElement.createEditorElement(is);
		List<Element> patterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern");
		Assert.assertEquals(2, patterns.size());
		PatternElement pattern0 = (PatternElement) patterns.get(0);
		PatternElement pattern1 = (PatternElement) patterns.get(1);
	}
	
	@Test
	public void testCreateRegex() throws FileNotFoundException {
		InputStream is = new FileInputStream("src/test/resources/org/xmlcml/norma/editor/speciesEditor.xml");
		EditorElement editorElement = (EditorElement) AbstractEditorElement.createEditorElement(is);
		List<Element> patterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern");
		PatternElement pattern0 = (PatternElement) patterns.get(0);
		String regex = pattern0.createRegex();
		Pattern.compile(regex);
		Assert.assertEquals(
				"\\s*((?:\\u2018?)[A-Z](?:[a-z]{2,}|[a-z]{1,2}\\.))\\s+([a-z]{2,}(?:\\u2019?))\\s+([^\\s\\(]+)\\s+((?:\\()(?:[A-Z]{1,2}|NC_))([0-9]{5,6}(?:\\)))\\s*",
				regex);
		PatternElement pattern1 = (PatternElement) patterns.get(1);
		regex = pattern1.createRegex();
		Pattern.compile(regex);
		Assert.assertEquals("\\s*((?:\\u2018?)[A-Z](?:[a-z]{2,}|[a-z02S/]?\\.))\\s+([a-z/]+(?:\\u2019?))\\s+([^\\s\\(]+(?:\\s+[^\\s\\(]+)?)\\s+((?:\\()(?:[A-Z123580]{1,2}|NC_))([0-9BIOSZ]{5,6}(?:\\)))\\s*",  regex);
	}
	
	
	@Test
	public void testRegexAgainstMatchingStrings() throws FileNotFoundException {
		InputStream is = new FileInputStream("src/test/resources/org/xmlcml/norma/editor/speciesEditor.xml");
		EditorElement editorElement = (EditorElement) AbstractEditorElement.createEditorElement(is);
		List<Element> patterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern");
		Pattern pattern0 = Pattern.compile(((PatternElement) patterns.get(0)).createRegex());
		Element level0 = XMLUtil.parseQuietlyToDocument(new File("src/test/resources/org/xmlcml/norma/editor/level0Test.xml")).getRootElement();
		List<Element> otuList = XMLUtil.getQueryElements(level0, "/otus/otu");
		for (Element otu : otuList) {
			String otuValue = otu.getValue();
			Assert.assertTrue(""+otuValue, pattern0.matcher(otuValue).matches());
		}
		Pattern pattern1 = Pattern.compile(((PatternElement) patterns.get(1)).createRegex());
		for (Element otu : otuList) {
			String otuValue = otu.getValue();
			Assert.assertTrue(""+otuValue, pattern1.matcher(otuValue).matches());
		}
	}
	
	@Test
	public void testRegexAgainstNonMatchingStrings() throws FileNotFoundException {
		InputStream is = new FileInputStream("src/test/resources/org/xmlcml/norma/editor/speciesEditor.xml");
		EditorElement editorElement = (EditorElement) AbstractEditorElement.createEditorElement(is);
		List<Element> patterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern");
		Pattern pattern0 = Pattern.compile(((PatternElement) patterns.get(0)).createRegex());
//		LOG.debug(pattern);
		Element level1 = XMLUtil.parseQuietlyToDocument(new File("src/test/resources/org/xmlcml/norma/editor/level1Test.xml")).getRootElement();
		List<Element> otuList = XMLUtil.getQueryElements(level1, "/otus/otu");
		// the garbles should fail the normall regexes
		for (Element otu : otuList) {
			String otuValue = otu.getValue();
			Assert.assertFalse("should fail: "+otuValue, pattern0.matcher(otuValue).matches());
		}
		// but the forgiving one will match
		Pattern pattern1 = Pattern.compile(((PatternElement) patterns.get(1)).createRegex());
		for (Element otu : otuList) {
			String otuValue = otu.getValue();
			Assert.assertTrue(""+otuValue, pattern1.matcher(otuValue).matches());
		}
	}
	
	@Test
	public void testCorrectNonMatchingString() throws FileNotFoundException {
		InputStream is = new FileInputStream("src/test/resources/org/xmlcml/norma/editor/speciesEditor.xml");
		EditorElement editorElement = (EditorElement) AbstractEditorElement.createEditorElement(is);
		List<Element> patterns = XMLUtil.getQueryElements(editorElement, "/editor/patternList/pattern");
		PatternElement patternElement1 = (PatternElement) patterns.get(1);
		Pattern pattern1 = patternElement1.createPattern();
		String badTarget = "Streptococcus gordonii CH1 (NC_OO9785)";
		Matcher matcher = pattern1.matcher(badTarget);
		Assert.assertTrue("should match", matcher.matches());
		List<String> groups = new ArrayList<String>();
		for (int i = 1; i <= matcher.groupCount(); i++) {
			String group = matcher.group(i);
			FieldElement field = patternElement1.getField(i - 1);
			LOG.trace(field.getOrCreateSubstitutionList().size());
			group = field.applySubstitutions(group);
			LOG.trace(">"+group);
			groups.add(group);
		}
	}
	

}
