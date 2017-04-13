package org.xmlcml.norma.grobid.runner;

import java.util.Arrays;
import java.util.List;

public enum GrobidOption {
	CLOSE("close"),
	PROCESS_FULL_TEXT("processFullText"),
	PROCESS_HEADER("processHeader"),
	PROCESS_DATE("processDate"),
	PROCESS_AUTHORS_HEADER("processAuthorsHeader"),
	PROCESS_AUTHORS_CITATION("processAuthorsCitation"),
	PROCESS_AFILIATION("processAffiliation"),
	PROCESS_RAW_REFERENCES("processRawReference"),
	PROCESS_REFERENCES("processReferences"),
	CREATE_TRAINING_HEADER("createTrainingHeader"),
	CREATE_TRAINING_FULL_TEXT("createTrainingFulltext"),
	CREATE_TRAINING_SEGMENTATION("createTrainingSegmentation"),
	CREATE_TRAINING_REFRERENCE_SEGMENTATION("createTrainingReferenceSegmentation"),
	CREATE_TRAINING_CITATION_PATENT("createTrainingCitationPatent"),
	PROCESS_CITATION_PATENT_TEI("processCitationPatentTEI"),
	PROCESS_CITATION_PATENT_ST36("processCitationPatentST36"),
	PROCESS_CITATION_PATENT_TXT("processCitationPatentTXT"),
	PROCESS_CITATION_PATENT_PDF("processCitationPatentPDF")
	;
	
	public final static List<GrobidOption> PROCESS_FULL_TEXT_OPTIONS = Arrays.asList(new GrobidOption[] {GrobidOption.PROCESS_FULL_TEXT});

	private String option;
	private GrobidOption(String opt) {
		setOption(opt);
	}
	public GrobidOption getOption(String opt) {
		for (GrobidOption grobidOption : values()) {
			if (grobidOption.option.equals(opt)) {
				return grobidOption;
			}
		}
		return null;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
}
