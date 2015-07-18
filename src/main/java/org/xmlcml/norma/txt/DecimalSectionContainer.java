package org.xmlcml.norma.txt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.euclid.IntArray;

public class DecimalSectionContainer implements Iterable<DecimalSection> {

	private static final Logger LOG = Logger.getLogger(DecimalSectionContainer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private List<DecimalSection> sectionList;
	private List<DecimalSection> pageList;
	private List<DecimalSection> tocList;
	private List<DecimalSection> chapterList;
	
	public DecimalSectionContainer() {
		
	}

	public void add(DecimalSection decimalSection) {
		ensureSectionList();
		sectionList.add(decimalSection);
	}

	private void ensureSectionList() {
		if (sectionList == null) {
			sectionList = new ArrayList<DecimalSection>();
		}
	}

	public Iterator<DecimalSection> iterator() {
		ensureSectionList();
		return sectionList.iterator();
	}

	public void addLines(List<String> lines) {
		ensureSectionList();
		for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
			String line = lines.get(lineNumber);
			DecimalSection decimalSection = new DecimalSection(lineNumber, line);
			int count = decimalSection.size();
			if (count > 0) {
				add(decimalSection);
				LOG.trace(lineNumber+": "+decimalSection.toString());
			}
		}
	}

	public List<DecimalSection> extractPages() {
		pageList = new ArrayList<DecimalSection>();
		for (DecimalSection section : sectionList) {
			if (section.containsKey(DecimalSection.L1_KEY) || section.containsKey(DecimalSection.R1_KEY)) {
				pageList.add(section);
			}
		}
		return pageList;
	}
	
	public List<DecimalSection> extractToc() {
		tocList = new ArrayList<DecimalSection>();
		for (DecimalSection section : sectionList) {
			if ((section.containsKey(DecimalSection.L1_KEY) ||
					section.containsKey(DecimalSection.L12_KEY) ||
					section.containsKey(DecimalSection.L123_KEY) ||
					section.containsKey(DecimalSection.L1234_KEY)) 
					&& section.containsKey(DecimalSection.R1_KEY)) {
				tocList.add(section);
			}
		}
		checkToc();
		return tocList;
	}

	private boolean checkToc() {
		IntArray lastSection = null;
		for (DecimalSection toc : tocList) {
			LOG.trace(toc);
			IntArray section = toc.getLeftSection();
			if (lastSection == null) {
				lastSection = section;
			} else {
				if (!checkArrayIncrement(toc.getLineNumber(), lastSection, section)) return false;
			}
			lastSection = section;
		}
		return true;
	}
	
	private boolean checkArrayIncrement(int lineNumber, IntArray lastSection, IntArray section) {
		int lastSize = lastSection.size();
		int thisSize = section.size();
		if (lastSize == thisSize) {
			if (!checkEquals(lastSection, section, lastSize - 1)) {
				return badIncrement(lineNumber, lastSection, section);
			}
			if (lastSection.elementAt(lastSize - 1) != section.elementAt(thisSize - 1) - 1) {
				return badIncrement(lineNumber, lastSection, section);
			}
		} else if (lastSize >= thisSize + 1) {
			// 1.2.3 => 1.3
			if (!checkEquals(lastSection, section, thisSize - 1)) {
				return badIncrement(lineNumber, lastSection, section);
			}
			if (lastSection.elementAt(thisSize - 1) != section.elementAt(thisSize - 1) - 1) {
				LOG.debug("xxx "+lastSize+"; "+thisSize);
				return badIncrement(lineNumber, lastSection, section);
			}
		} else if (lastSize == thisSize - 1) {
			// 1.2.3 => 1.2.3.1
			if (!checkEquals(lastSection, section, lastSize)) {
				return badIncrement(lineNumber, lastSection, section);
			}
			if (section.elementAt(thisSize - 1) != 1) {
				return badIncrement(lineNumber, lastSection, section);
			}
		} else if (lastSize >= thisSize - 1) {
			return badIncrement(lineNumber, lastSection, section);
		} else {
			return badIncrement(lineNumber, lastSection, section);
		}
		return true;
	}

	private boolean badIncrement(int lineNumber, IntArray lastSection,
			IntArray section) {
		LOG.debug("bad section increment ("+lineNumber+")"+lastSection+" -> "+section);
		return false;
	}

	private boolean checkEquals(IntArray lastSection, IntArray section, int toCheck) {
		for (int i = 0; i < toCheck; i++) {
			if (lastSection.elementAt(i) != section.elementAt(i)) {
				LOG.debug("bad section increment "+lastSection+" -> "+section);
				return false;
			}
		}
		return true;
	}

	public List<DecimalSection> extractChapters() {
		chapterList = new ArrayList<DecimalSection>();
		for (DecimalSection section : sectionList) {
			if (section.containsKey(DecimalSection.CHAPTER_KEY)) {
				chapterList.add(section);
			}
		}
		checkChapters();
		return chapterList;
	}

	private void checkChapters() {
		int current = 0;
		for (DecimalSection chapter : chapterList) {
			Integer chapterNo = chapter.getChapterNumber();
			LOG.debug(chapterNo);
			if (chapterNo != current+1) {
				LOG.debug("break "+chapterNo+" ("+current+")");
			}
			current = chapterNo;
		}
	}
	
	
}
