package org.xmlcml.norma.txt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class AnnotatedLineContainer implements Iterable<AnnotatedLine> {

	private static final Logger LOG = Logger.getLogger(AnnotatedLineContainer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private List<AnnotatedLine> annotatedLinesList;
	private AnnotatedLineContainer pageHeadingList;
	private Toc toc;
	private AnnotatedLineContainer chapterHeadingList;
	private int currentLine = -1;
	
	public AnnotatedLineContainer() {
		
	}

	public void add(AnnotatedLine line) {
		ensureSectionList();
		annotatedLinesList.add(line);
	}

	private void ensureSectionList() {
		if (annotatedLinesList == null) {
			annotatedLinesList = new ArrayList<AnnotatedLine>();
		}
	}

	public Iterator<AnnotatedLine> iterator() {
		ensureSectionList();
		return annotatedLinesList.iterator();
	}

	public void addLines(List<String> lines) {
		ensureSectionList();
		for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
			String line = lines.get(lineNumber);
			AnnotatedLine aLine = new AnnotatedLine(lineNumber, line);
			add(aLine);
		}
	}

	public AnnotatedLineContainer extractPageHeadings() {
		pageHeadingList = new AnnotatedLineContainer();
		for (AnnotatedLine section : annotatedLinesList) {
			if (section.containsKey(AnnotatedLine.L1_KEY) && section.size() == 1) {
				pageHeadingList.add(section);
			} else if (section.containsKey(AnnotatedLine.R1_KEY)) {
				pageHeadingList.add(section);
			}
		}
		checkPages();
		return pageHeadingList;
	}
	
	private void checkPages() {
		int currentPage = 1;
		String key = AnnotatedLine.R1_KEY;
		for (AnnotatedLine page : pageHeadingList) {
//			page.
		}
	}

//	public Toc extractToc() {
//		toc = new Toc();
//		for (AnnotatedLine section : annotatedLinesList) {
//			if (section.containsLeftDecimalKeys() 
//					&& section.containsKey(AnnotatedLine.R1_KEY)) {
//				toc.add(section);
//			}
//		}
//		checkToc();
//		return toc;
//	}

	public AnnotatedLineContainer extractChapterHeadings(int start, int end) {
		chapterHeadingList = new AnnotatedLineContainer();
		for (int i = start; i < end; i++) {
			AnnotatedLine section = annotatedLinesList.get(i);
			if (section.containsKey(AnnotatedLine.CHAPTER_KEY)) {
				chapterHeadingList.add(section);
			}
		}
		checkChapters();
		return chapterHeadingList;
	}

	private void checkChapters() {
		int current = 0;
		for (AnnotatedLine chapter : chapterHeadingList) {
			Integer chapterNo = chapter.getChapterNumber();
			if (chapterNo != current+1) {
				LOG.warn("break "+chapterNo+" ("+current+")");
			}
			current = chapterNo;
		}
	}

	public int readToL1R1(int start) {
		for (currentLine = start; currentLine < annotatedLinesList.size(); currentLine++) {
			AnnotatedLine aLine = annotatedLinesList.get(currentLine);
			if (aLine.containsKey(AnnotatedLine.L1_KEY) || aLine.containsKey(AnnotatedLine.R1_KEY)) {
				LOG.trace(">toc>"+currentLine);
				return currentLine;
			}
		}
		return -1;
	}
	
	public int readToChapter(int start) {
		for (currentLine = start; currentLine < annotatedLinesList.size(); currentLine++) {
			AnnotatedLine aLine = annotatedLinesList.get(currentLine);
			if (aLine.containsKey(AnnotatedLine.CHAPTER_KEY)) {
				LOG.trace(">chap>"+currentLine);
				return currentLine;
			}
		}
		return -1;
	}

	public Toc readToc(int start, int end) {
		toc = new Toc();
		for (int lineNumber = start; lineNumber < end; lineNumber++) {
			AnnotatedLine annotatedLine = annotatedLinesList.get(lineNumber);
			toc.add(annotatedLine);
			if (annotatedLine.containsLeftDecimalKeys()) {
				LOG.trace(">tt>"+annotatedLine);
			} else {
				LOG.trace(">non-toc>"+annotatedLine);
			}
		}
		return toc;

	}

	public AbstractSection readChapter(int start, int end) {
		AbstractSection chapter = new Chapter(this);
		for (int lineNumber = start; lineNumber < end; lineNumber++) {
			AnnotatedLine annotatedLine = annotatedLinesList.get(lineNumber);
			chapter.add(annotatedLine);
			if (annotatedLine.containsLeftDecimalKeys()) {
				LOG.trace(">tt>"+annotatedLine);
			} else {
				LOG.trace(">non-chapter>"+annotatedLine);
			}
		}
		return chapter;
	}

	public int size() {
		return annotatedLinesList == null ? 0 : annotatedLinesList.size();
	}

	public AnnotatedLine get(int i) {
		ensureSectionList();
		return i < 0 || i >= annotatedLinesList.size() ? null : annotatedLinesList.get(i);
	}

	public List<Chapter> extractChapters(AnnotatedLineContainer chapterHeadings) {
		return extractChapters(chapterHeadings, annotatedLinesList.size());
	}

	public List<Chapter> extractChapters(AnnotatedLineContainer chapterHeadings, int end) {
		List<Chapter> chapterList = new ArrayList<Chapter>();
		for (int i = 1; i < chapterHeadings.size(); i++) {
			Chapter chapter = extractChapter(chapterHeadings.get(i-1).getLineNumber(), chapterHeadings.get(i).getLineNumber());
			chapterList.add(chapter);
		}
		Chapter chapter = extractChapter(chapterHeadings.get(chapterHeadings.size()-1).getLineNumber(), end);
		chapterList.add(chapter);
		return chapterList;
	}

	private Chapter extractChapter(int start, int end) {
		Chapter chapter = new Chapter(this);
		chapter.addLines(start, end);
		return chapter;
	}

	public List<Chapter> extractChapters() {
		AnnotatedLineContainer chapterHeadings = extractChapterHeadings(0, size());
		List<Chapter> chapterList = extractChapters(chapterHeadings);
		return chapterList;
	}

	public int getFirstLineNumber() {
		return get(0).getLineNumber();
	}
	
//	/** extracts from annotatedLine to end of document.
//	 * 
//	 * @param annotatedLine
//	 * @return
//	 */
//	private Chapter extractChapter(AnnotatedLine annotatedLine) {
//		Chapter chapter = new Chapter();
//		for (int i = annotatedLine.getLineNumber(); i < annotatedLinesList.size(); i++) {
//			chapter.add(annotatedLinesList.get(i));
//		}
//		return chapter;
//	}
	
}
