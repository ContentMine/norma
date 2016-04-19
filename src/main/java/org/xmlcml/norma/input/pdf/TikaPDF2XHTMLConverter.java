package org.xmlcml.norma.input.pdf;

public class TikaPDF2XHTMLConverter {

	/*
	All,
	  Raymond Wu recently opened TIKA-1679 and recommended that we switch to per-page processing so that if there's an exception on one page, we'll still be able to extract contents from other pages.

	  The proposed fix is along these lines:

	             int nop = document.getNumberOfPages();
	            for(int i=1;i<=nop;i++) {
	                PDF2XHTML pdf2XHTML = new PDF2XHTML(handler, metadata,
	                extractAnnotationText, enableAutoSpace,
	                suppressDuplicateOverlappingText, sortByPosition);
	                try {
	                    pdf2XHTML.setStartPage(i);
	                    pdf2XHTML.setEndPage(i);
	                    pdf2XHTML.writeText(document, dummyWriter);
	                } catch(Exception e) {
	                    // TODO ...
	                }

*/
}
