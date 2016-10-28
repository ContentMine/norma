package org.xmlcml.norma.applic;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class EssoilDB {

	static final Logger LOG = Logger.getLogger(EssoilDB.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	final static File ESSOIL = new File(new File(System.getProperty("user.home")), "phyto/EssoilDB");
	
	private List<String> signatureList;
	private HashSet<String> unmatchedObservationIds;

	boolean useBestPlantName = true;
	private Bibliography bibliography;

	private PlantCollection plantCollection;
	private ObservationCollection observationCollection;
	private TNTBuilder tntBuilder;
	private ChemicalCollection chemicalCollection;

	private Object plantRecordFile;

	public EssoilDB() {
		
	}
	

	public static void main(String[] args) throws IOException {
		EssoilDB db = new EssoilDB();
		db.readDataAndProcess();
	}

	ChemicalCollection getOrCreateChemicalCollection() {
		if (chemicalCollection == null) {
			this.chemicalCollection = new ChemicalCollection(this);
		}
		return chemicalCollection;
	}

	private TNTBuilder getOrCreateTNTBuilder() {
		if (tntBuilder == null) {
			tntBuilder = new TNTBuilder(this);
		}
		return tntBuilder;
		
	}

	ObservationCollection getOrCreateObservationCollection() {
		if (observationCollection == null) {
			observationCollection = new ObservationCollection(this);
		}
		return observationCollection;
	}

	public void writeSignatures(File file) throws IOException {
		FileUtils.writeLines(file, signatureList);
	}
	
	void addPlantWithSignatureToList(List<String> signatureList, String observationId, List<Observation> observations) {
		PlantRecord plant = getOrCreatePlantCollection().getPlantById().get(observationId);
		String binomial = plant == null ? null : plant.getBinomial();
		String line = binomial+"\n ... "+Observation.makeSignature(observations);
		signatureList.add(">>"+line);
	}

	public static void addToIndexedMultiset(Map<String, Multiset<String>> multisetByKey, String key, String value) {
		Multiset<String> multiset = multisetByKey.get(key);
		if (multiset == null) {
			multiset = HashMultiset.create();
			multisetByKey.put(key, multiset);
		}
		multiset.add(value);
	}

	public static void writeIndexedMultiset(Map<String, Multiset<String>> indexedMultiset, File file) throws IOException {
		List<String> lines = new ArrayList<String>();
		for (String key : indexedMultiset.keySet()) {
			Multiset<String> values = indexedMultiset.get(key);
			if (values.elementSet().size() == 1) {
				String v = values.elementSet().toArray(new String[0])[0];
				if ("NA".equals(v) || v.equals("")) {
					String s = key+" ?";
					if (values.size() > 1) {
						s += " ("+values.size()+")";
					}
					lines.add(s);
				}
			} else {
				// disagreement
				lines.add(key);
				lines.add(" ... "+values.size()+": "+values.toString());
			}
		}
		FileUtils.writeLines(file, lines);
	}
	
	PlantCollection getOrCreatePlantCollection() {
		if (plantCollection == null) {
			plantCollection = new PlantCollection(this);
		}
		return plantCollection;
	}

	void ensureBibliography() {
		if (bibliography == null) {
			bibliography = new Bibliography();
		} 
	}

	public void setUseBestPlantName(boolean useBestName) {
		useBestPlantName = useBestName;
	}

	public boolean isUseBestPlantName() {
		return useBestPlantName;
	}
	

	private void mergeRecords() {
//		List<String> observationIdList = new ArrayList<String>(observationListById.keySet());
//		Collections.sort(observationIdList);
//		unmatchedObservationIds = new HashSet<String>();
//		for (String observationId : observationIdList) {
//			List<Observation> observations = new ArrayList<Observation>(observationListById.get(observationId));
//			int size = observations.size();
//			LOG.trace(observationId+": "+size);
//			if (!getOrCreatePlantCollection().getPlantById().containsKey(observationId)) {
//				unmatchedObservationIds.add(observationId);
//			}
//		}
	}
	
	private void writeUnmatchedObservationsIds(File file) throws IOException {
		if (unmatchedObservationIds != null) {
			FileUtils.writeLines(file, new ArrayList<String>(unmatchedObservationIds));
		}
	}

//	void readPlantRecordsAndCreateIndexes() throws IOException {
//		if (true) throw new RuntimeException("obsolete");
//		PlantCollection plantCollection = getOrCreatePlantCollection();
//		plantCollection.readPlantSynonyms(new File(EssoilDB.ESSOIL, "trns.txt"));
//		plantCollection.readRawPlantRecords(new File(EssoilDB.ESSOIL, "info_plant_08102015.csv"));
//	
//		plantCollection.writePlantsCSV(new File(EssoilDB.ESSOIL, "info_plant.csv"));
//		plantCollection.writeBinomialNames(new File(EssoilDB.ESSOIL, "info_plant.txt"));
//		plantCollection.writeDuplicatePlantIds(new File(EssoilDB.ESSOIL, "duplicatePlantIds.txt"));
//	}

//	ObservationCollection readObservationsAndCreateIndexes() throws IOException {
//		ObservationCollection observationCollection = getOrCreateObservationCollection();
//		observationCollection.readObservationsAndCreateIndexes();
//		return observationCollection;
//	}

	void checkAndIndexChemicals() throws IOException {
		EssoilDB.LOG.debug("checkChemicals");
		ChemicalCollection chemicalCollection = getOrCreateChemicalCollection();
		chemicalCollection.checkAndIndexChemicals();
	}

	void createTNTOutput() throws IOException {
		TNTBuilder tntBuilder = getOrCreateTNTBuilder();
		tntBuilder.writeTNT(new File(EssoilDB.ESSOIL, "essoil2000.tnt"), 2000);
	}


	void readDataAndProcess() throws IOException {
		PlantCollection plantCollection = getOrCreatePlantCollection();

		plantCollection.setPlantSynonymFile(new File(EssoilDB.ESSOIL, "trns.txt"));
		
		plantCollection.setRawPlantRecordFile(new File(EssoilDB.ESSOIL, "info_plant_08102015.csv"));
		plantCollection.setReplacementFile(new File(EssoilDB.ESSOIL, "replacementPlantNames.csv"));
		plantCollection.setDuplicatePlantIdFile(new File(EssoilDB.ESSOIL, "duplicatePlantIds.csv"));
		plantCollection.setBinomialOutputFile(new File(EssoilDB.ESSOIL, "binomialOutput.csv"));
		plantCollection.setBibliographyOutputFile(new File(EssoilDB.ESSOIL, "bibliography.csv"));
		plantCollection.setPlantOutputFile(new File(EssoilDB.ESSOIL, "plants.txt"));
		plantCollection.setPlantCSVFile(new File(EssoilDB.ESSOIL, "plants.csv"));
		getOrCreatePlantCollection();
		plantCollection.readPlantRecordsAndProcess();

		ObservationCollection observationCollection = getOrCreateObservationCollection();
		observationCollection.setRawObservationRecordFile(new File(EssoilDB.ESSOIL, "info_compound_08102015.csv"));
//		readObservationsAndCreateIndexes();
		observationCollection.readObservations();
		checkAndIndexChemicals();
		writeSignatures(new File(EssoilDB.ESSOIL, "signature.txt"));
		
		createTNTOutput();
		
		mergeRecords();
		writeUnmatchedObservationsIds(new File(EssoilDB.ESSOIL, "unmatchedCompoundDataIds.txt"));
	}




	


}
