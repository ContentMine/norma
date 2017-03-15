package org.xmlcml.norma.applic;

import java.io.File;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.util.CMineUtil;
import org.xmlcml.cproject.util.RectangularTable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

/** collection of information on Plants.
 * 
 * @author pm286
 *
 */
public class PlantCollection {

	private static final Logger LOG = Logger.getLogger(PlantCollection.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	Map<String, PlantRecord> plantById;
	Multiset<String> plantMultiset;
	Set<String> duplicatePlantIds;
	List<List<String>> plantValueListList;
	private EssoilDB essoilDB;
	private Map<String, String> acceptedNameBySubmittedName;
	private Map<String, String> matchedNameBySubmittedName;
	private Multimap<String, String> replacementNameListByOriginal;
	
	private File plantSynonymFile;
	
	private File replacementFile;
	private File duplicatePlantIdFile;
	private File rawPlantRecordFile;
	private File binomialOutputFile;
	private File bibliographyOutputFile;
	private File plantOutputFile;
	private File plantCSVFile;

	public PlantCollection(EssoilDB essoilDB) {
		this.essoilDB = essoilDB;
	}

	public void add(PlantRecord plant) {
		ensurePlantById();
		plantById.put(plant.getId(), plant);
	}

	private void ensurePlantById() {
		if (plantById == null) {
			plantById = new HashMap<String, PlantRecord>();
		}
	}
	
	public PlantRecord get(String id) {
		ensurePlantById();
		return plantById.get(id);
	}
	
	public int size() {
		return plantById.size();
	}

	public boolean containsKey(Object key) {
		return plantById.get(key) != null;
//		return plantById.containsKey(key);
	}

	public PlantRecord get(Object key) {
		return plantById.get(key);
	}

	public Collection<PlantRecord> values() {
		return plantById.values();
	}

	/** raw input (labelled CSV but not).
	 * 
 
#JEAcakoprtu2002Roo#Acorus calamus#Konyo Province, Turkey#1997#VOL. 14, 366-368 (Sep/Oct 2002)#Volatile Constituents of the Essential Oil of Acorus calamus L. Growth in Konya Province (Turkey).#M. Ozcan, A. Akgul, J.C Chalchat.#Journal of Essential Oil Research#Acoraceae#Plant
#JEAcaceli2003lea#Acorus calamus#Central Lithuania#1998#VOL. 15, 313-318 (Sep/Oct 2003)#Composition of Essential Oil of Sweet Flag(Acorus calamus L.) Leaves at Different Growing Phases#Petras R. Venskutonis,Audrone Dagilyte#Journal of Essential Oil Research#Acoraceae#Plant
     id              species           place        date  --- biblio ---                                title                                                                                     authors                              journal                      family?  plantType?  
	 * 
	 * @param file
	 * @throws IOException
	 */
	void readPlantRecordsAndProcess() throws IOException {
		if (plantSynonymFile != null) {
			readPlantSynonyms(plantSynonymFile);
		}
		if (rawPlantRecordFile != null) {
			readRawPlantRecords(rawPlantRecordFile);
			if (replacementFile != null) {
				writeReplacementNames(replacementFile);
			}
			if (duplicatePlantIdFile != null) {
				writeDuplicatePlantIds(duplicatePlantIdFile);
			}
			if (binomialOutputFile != null) {
				writeBinomialNames(binomialOutputFile);
			}
			if (bibliographyOutputFile != null) {
				processAndWriteBibliography(bibliographyOutputFile);
			}
			if (plantOutputFile != null) {
				writePlantRecordsOutputFile(plantOutputFile);
			}
			if (plantCSVFile != null) {
				writePlantRecordsCSVOutputFile(plantCSVFile);
			}
		}
	}

	private void writePlantRecordsOutputFile(File plantRecordsOutputFile) {
		LOG.debug("skipped writePlantRecordsOutputFile");
	}

	private void processAndWriteBibliography(File bibliographyOutputFile) throws IOException {
		List<PlantRecord> plantRecords = new ArrayList<PlantRecord>(plantById.values());
		Multiset<BibliographicRecord> bibliographySet = HashMultiset.create();
		for (PlantRecord plantRecord : plantRecords) {
			BibliographicRecord bibliographicRecord = plantRecord.getBibliographicRecord();
			if (bibliographicRecord != null) {
				if (bibliographySet.contains(bibliographicRecord)) {
					LOG.trace("dup:"+bibliographicRecord);
				}
				bibliographySet.add(bibliographicRecord);
			}
		}
		List<String> lines = new ArrayList<String>();
		for (Multiset.Entry<BibliographicRecord> record : bibliographySet.entrySet()) {
//			LOG.debug(">>>>"+record.getCount()+">"+record.getElement());
			lines.add(record.getElement().toString());
		}
		FileUtils.writeLines(bibliographyOutputFile, lines);
		LOG.info("Wrote ("+lines.size()+") bibliographyRecords to: "+bibliographyOutputFile);
	}

	void readRawPlantRecords(File plantRecordFile) throws IOException {
		List<String> lines = FileUtils.readLines(plantRecordFile);
		plantMultiset = HashMultiset.create();
		plantById = new HashMap<String, PlantRecord>();
		plantValueListList = new ArrayList<List<String>>();
		duplicatePlantIds = new HashSet<String>();
		essoilDB.ensureBibliography();
		essoilDB.getOrCreatePlantCollection();
		replacementNameListByOriginal = ArrayListMultimap.create();
		for (String line : lines) {
			PlantRecord plantRecord = PlantRecord.create(line);
			String plantId = plantRecord.getId();
			if (containsKey(plantId)) {
				duplicatePlantIds.add(plantId);
				LOG.trace("duplicate: "+plantId);
			}
			plantById.put(plantId, plantRecord);
			String binomial = plantRecord.getBinomial().trim().replaceAll("\\s+", " ");
			binomial = getBestPlantName1(binomial);
			plantMultiset.add(binomial);
		}
	}

	private void writeReplacementNames(File file) {
		List<Multiset.Entry<String>> replacedNames = CMineUtil.getKeysSortedByCount(replacementNameListByOriginal);
		if (replacedNames != null && replacedNames.size() > 0) {
			List<String> csvHeaders = Arrays.asList(new String[]{"old", "count", "new"});
			List<List<String>> valueListList = new ArrayList<List<String>>();
			for (Multiset.Entry<String> replacedName : replacedNames) {
				List<String> valueList = new ArrayList<String>();
				valueListList.add(valueList);
				valueList.add(String.valueOf(replacedName.getElement()));
				valueList.add(String.valueOf(replacedName.getCount()));
				String newname = new ArrayList<String>(replacementNameListByOriginal.get(replacedName.getElement())).get(0);
				valueList.add(String.valueOf(newname));
			}
			CMineUtil.writeCSV(file.toString(), csvHeaders, valueListList);
			LOG.info("Wrote ("+valueListList.size()+") replacementNames to: "+file);
		}
	}

	private String getBestPlantName1(String binomial) {
		String binomial1 = binomial;
		if (essoilDB.useBestPlantName) {
			binomial1 = this.getBestPlantName(binomial);
			if (!binomial.equals(binomial1)) {
				replacementNameListByOriginal.put(binomial, binomial1);
				LOG.trace("replaced "+binomial+" by "+binomial1);
			}
		}
		return binomial1;
	}

    /** restricts to 2 (or 1) strings and normalizes spaces.
	 *  
	 * @param observation
	 * @return
	 */
    public String getNormalizedBinomial(Observation observation) {
    	String binomial = observation.getBinomial(this);
    	binomial = getNormalizedBinomial(binomial);
		return binomial;
	}

	public static String getNormalizedBinomial(String binomial) {
		if (binomial != null) {
    		binomial = binomial.trim().replace("\\s+",  " ");
    		if (binomial.length() > 0) {
	    		String[] bits = binomial.split(" ");
	    		if (bits.length == 1) {
	    			binomial = bits[0];
	    		} else {
	    			binomial = bits[0]+" "+bits[1];
	    		}
    		}
    	}
		return binomial;
	}

	public void writePlantRecordsCSVOutputFile(File file) throws IOException {
		CMineUtil.writeCSV(file.toString(), PlantRecord.HEADERS, plantValueListList);
		LOG.info("Wrote CSV ("+plantValueListList.size()+") plants to: "+file);
	}
	
	public void writeBinomialNames(File file) throws IOException {
		CMineUtil.writeCSV(file.toString(), PlantRecord.HEADERS, plantValueListList);
		List<Multiset.Entry<String>> plantList = CMineUtil.getEntryListSortedByCount(plantMultiset);
		List<String> lines = new ArrayList<String>();
		for (Multiset.Entry<String> set : plantList) {
			String line = set.toString();
			lines.add(line);
		}
		FileUtils.writeLines(file, lines);
		LOG.info("Wrote ("+lines.size()+") plants to: "+file);
	}
	
	public void writeDuplicatePlantIds(File file) throws IOException {
		if (duplicatePlantIds != null && duplicatePlantIds.size() > 0) {
			List<String> ids = new ArrayList<String>(duplicatePlantIds);
			Collections.sort(ids);
			FileUtils.writeLines(file, ids);
			LOG.info("Wrote ("+ids.size()+") duplicatePlantRecords to: "+file);
		}
	}

	public Map<String, PlantRecord> getPlantById() {
		return plantById;
	}

	/** synonyms from TNRS.
	 * 
	 * @param file
	 * @throws IOException
	 */
	void readPlantSynonyms(File file) throws IOException {
		boolean useHeader = true;
		String s = FileUtils.readFileToString(file);
		// strings are interleaved with <NUL> - Aargh - manually corrected now
//		s = s.replaceAll(""+(char)0, "");
//		FileUtils.write(new File("trns.txt"), s);
		/**
		 * 
[N a m e _ s u b m i t t e d ,  N a m e _ m a t c h e d ,  A u t h o r _ m a t c h e d ,  O v e r a l l _ s c o r e ,  T a x o n o m i c _ s t a t u s ,  A c c e p t e d _ n a m e ,  A c c e p t e d _ a u t h o r ,  A c c e p t e d _ f a m i l y ,  S o u r c e ,  W a r n i n g s ,  A c c e p t e d _ n a m e _ l s i d ]
      0               1               2              3                4               5               6                7            8        9           10
		 */
		RectangularTable table = RectangularTable.readCSVTable(new StringReader(s), useHeader, CSVFormat.TDF);
		List<String> submittedNames = table.getColumn("Name_submitted");
		List<String> matchedNames = table.getColumn("Name_matched");
		List<String> acceptedNames = table.getColumn("Accepted_name");
		acceptedNameBySubmittedName = new HashMap<String, String>();
		matchedNameBySubmittedName = new HashMap<String, String>();
		for (int i = 0; i < table.getRows().size(); i++) {
			String submittedName = submittedNames.get(i).trim().replaceAll("\\s+", " ");
			String matchedName = matchedNames.get(i).trim().replaceAll("\\s+", " ");
			String acceptedName = acceptedNames.get(i).trim().replaceAll("\\s+", " ");
			acceptedNameBySubmittedName.put(submittedName, acceptedName);
			matchedNameBySubmittedName.put(submittedName, matchedName);
		}
		LOG.info("Read ("+table.getRows().size()+") plant synonyms from: "+file);
	}

	/** 
	 * look for accepted name. If null or empty, use the best match to submitted name.
	 * 
	 * @param name
	 * @return
	 */
	private String getBestPlantName(String name) {
		name = name.trim();
		String bestName = acceptedNameBySubmittedName.get(name);
		if (bestName == null || bestName.trim().equals(name) || bestName.trim().equals("")) {
			bestName = matchedNameBySubmittedName.get(name);
		}
		return (bestName != null && !bestName.trim().equals("")) ? bestName : name;
	}

	public void setReplacementFile(File file) {
		this.replacementFile = file;
	}

	public void setDuplicatePlantIdFile(File file) {
		this.duplicatePlantIdFile = file;
	}

	public void setBinomialOutputFile(File file) {
		this.binomialOutputFile = file;
	}

	public void setPlantSynonymFile(File file) {
		this.plantSynonymFile = file;
	}

	public void setRawPlantRecordFile(File file) {
		this.rawPlantRecordFile = file;
	}

	public void setBibliographyOutputFile(File file) {
		this.bibliographyOutputFile = file;
	}

	public void setPlantOutputFile(File file) {
		this.plantOutputFile = file;
	}

	public void setPlantCSVFile(File file) {
		this.plantCSVFile = file;
	}

}
