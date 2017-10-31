package org.xmlcml.norma.txt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.IntArray;
import org.xmlcml.graphics.html.HtmlDiv;

public class Chapter extends AbstractSection {

	private static final Logger LOG = Logger.getLogger(Chapter.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private HtmlDiv chapterElement;
	private List<AnnotatedLine> localSectionHeadings;
	private List<Section> localSections;
	private Section firstSection;
	private List<Section> sectionList;

	public Chapter(AnnotatedLineContainer lineContainer) {
		this.parentLineContainer = lineContainer;
	}
	
	boolean checkChapter() {
//		IntArray lastSection = null;
//		
//		for (int i = 1; i < annotatedLineContainer.size(); i++) {
//			AnnotatedLine annotatedLine = annotatedLineContainer.get(i);
//			LOG.trace(annotatedLine);
//			IntArray section = annotatedLine.getLeftSection();
//			if (lastSection == null) {
//				lastSection = section;
//			} else {
//				if (!AnnotatedLine.checkArrayIncrement(annotatedLine.getLineNumber(), lastSection, section)) {
////					LOG.warn("possible bad increment "+lastSection+" => "+section);
//				}
//			}
//			lastSection = section;
//		}
		LOG.warn("checkChapter NYI");
		return true;
	}

	public List<AnnotatedLine> getDecimalSectionHeadings() {
		if (localSectionHeadings == null) {
			localSectionHeadings = new ArrayList<AnnotatedLine>();
			Set<IntArray> sectionHeadingSet = new HashSet<IntArray>();
			for (int i = 0; i < localLineContainer.size(); i++) {
				AnnotatedLine localLine = localLineContainer.get(i);
				IntArray leftSection = localLine.getLeftSection();
				if (localLine.getLeftSection() != null && !sectionHeadingSet.contains(leftSection)) {
					localSectionHeadings.add(localLine);
					sectionHeadingSet.add(leftSection);
				}
			}
		}
		return localSectionHeadings;
	}
	
	public List<Section> getOrCreateDecimalSections() {
		if (localSections == null) {
			localSections = new ArrayList<Section>();
			getDecimalSectionHeadings();
			if (localSections.size() > 0) {
				makeFirstSection();
				makeSections();
			}
		}
		return localSections;
	}
	
	
	private void makeSections() {
		for (int i = 1; i < localSectionHeadings.size(); i++) {
			int start = localSectionHeadings.get(i - 1).getLineNumber();
			int end = localSectionHeadings.get(i).getLineNumber();
			Section section = new Section(parentLineContainer);
			makeSection(section, start, end);
			localSections.add(section);
		}
	}

	private void makeFirstSection() {
		int start = localLineContainer.getFirstLineNumber();
		int end = localSections.get(0).getFirstLineNumber();
		firstSection = new Section(parentLineContainer);
		makeSection(firstSection, start, end);
	}


	public HtmlDiv getOrCreateHtmlElement() {
		if (chapterElement == null) {
			chapterElement = new HtmlDiv();
			getOrCreateDecimalSections();
			for (int i = 0; i < localSections.size(); i++) {
				Section localSection = localSections.get(i);
				chapterElement.appendChild(localSection.getOrCreateHtmlElement());
			}
		}
		return chapterElement;
	}
	
}
