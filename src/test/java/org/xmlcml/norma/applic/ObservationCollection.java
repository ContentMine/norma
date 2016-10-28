package org.xmlcml.norma.applic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.util.CMineUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public class ObservationCollection {

	private static final Logger LOG = Logger.getLogger(ObservationCollection.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private EssoilDB essoilDB;
	private List<Observation> normalizedDataList;
	private Multimap<String, Observation> observationByTrivialName;
	private List<Observation> observationListWithUniqueTrivialNames;
	private List<Observation> observationList;
	private List<String> observationIdList;
	private Multimap<String, Observation> observationListById;
	private HashSet<String> trivialNamesWithinSameObservations;
	private List<List<String>> compoundValueListList;
	private File rawObservationRecordFile;

	public ObservationCollection(EssoilDB essoilDB) {
		this.essoilDB = essoilDB;
	}

	void createObservationListWithUniqueBinomials() {
		PlantCollection plantCollection = essoilDB.getOrCreatePlantCollection();
		normalizedDataList = new ArrayList<Observation>();
		Set<String> binomialSet = new HashSet<String>();
		for (Observation observation : observationList) {
			String binomial = plantCollection.getNormalizedBinomial(observation);
			if (!binomialSet.contains(binomial)) {
				normalizedDataList.add(observation);
				binomialSet.add(binomial);
			}
		}
		LOG.debug("Norm: "+normalizedDataList.size());
	}

/**
#JEMlofl2007Lea#1,8-cineole#470-82-6#3.2#leaf#GC-FID and GC/MS#C10H18O#monoterpene derivative#NA#Plant#Normal#2,2,4-trimethyl-3-oxabicyclo[2.2.2]octane
#JEMlofl2007Lea#allo-aromadendrene#25246-27-9#0.7#leaf#GC-FID and GC/MS#C15H24#sesquiterpene#NA#Plant#Normal#(4as,7r,7ar)-1,1,7-trimethyl-4-methylidene-2,3,4a,5,6,7,7a,7b-octahydro-1ah-cyclopropa[e]azulene
#JEMlofl2007Lea#alpha-calacorene#21391-99-1#0.2#leaf#GC-FID and GC/MS#C15H20#NA#NA#Plant#Normal#(1s)-4,7-dimethyl-1-propan-2-yl-1,2-dihydronaphthalene	 * 
	 * @throws IOException
	 */
	public void readObservationsOld(File file) throws IOException {
		ChemicalCollection chemicalCollection = essoilDB.getOrCreateChemicalCollection();
		List<String> lines = FileUtils.readLines(file);
		observationList = new ArrayList<Observation>();
		getOrCreateObservationListById();
		
//			compoundValueListList = new ArrayList<List<String>>();
		createObservationsListsAndIndexes(lines);
		
		trivialNamesWithinSameObservations = new HashSet<String>();
		observationIdList = new ArrayList<String>(observationListById.keySet());
		Collections.sort(observationIdList);
//			trivialNameSetAll = HashMultiset.create();
		List<String> signatureList = new ArrayList<String>();
		for (String observationId : observationIdList) {
			List<Observation> observations = new ArrayList<Observation>(observationListById.get(observationId));
			int size = observations.size();
			Multiset<String> trivialNameSet = HashMultiset.create();
			for (Observation observation : observations) {
				String trivialName = observation.getChemical().getTrivialName();
				trivialNameSet.add(trivialName);
			}
			List<Multiset.Entry<String>> trivialNameEntries = CMineUtil.getEntryListSortedByCount(trivialNameSet);
			
			Multiset.Entry<String> entry0 = trivialNameEntries.get(0);
			// needs manual correction
			if (entry0.getCount() > 1) {
				// chemicals in same oservation
				for (Observation observation : observations) {
					String trivialName = observation.getChemical().getTrivialName();
					if (trivialName.equals(entry0.getElement())) {
						trivialNamesWithinSameObservations.add(trivialName);
					}
				}
			} else {
				essoilDB.addPlantWithSignatureToList(signatureList, observationId, observations);
				chemicalCollection.addTrivialNames(observations);
			}
		}
		LOG.info("Read ("+observationListById.size()+") observations from: "+file);

	}

/**
#JEMlofl2007Lea#1,8-cineole#470-82-6#3.2#leaf#GC-FID and GC/MS#C10H18O#monoterpene derivative#NA#Plant#Normal#2,2,4-trimethyl-3-oxabicyclo[2.2.2]octane
#JEMlofl2007Lea#allo-aromadendrene#25246-27-9#0.7#leaf#GC-FID and GC/MS#C15H24#sesquiterpene#NA#Plant#Normal#(4as,7r,7ar)-1,1,7-trimethyl-4-methylidene-2,3,4a,5,6,7,7a,7b-octahydro-1ah-cyclopropa[e]azulene
#JEMlofl2007Lea#alpha-calacorene#21391-99-1#0.2#leaf#GC-FID and GC/MS#C15H20#NA#NA#Plant#Normal#(1s)-4,7-dimethyl-1-propan-2-yl-1,2-dihydronaphthalene	 * 
 -- plantID --    -- trivial --     --cas--    --part-- --method--          --formula--  --??-- --??--   --iupac--
	 * @throws IOException
	 */
	public void readObservations(File file) throws IOException {
		ChemicalCollection chemicalCollection = essoilDB.getOrCreateChemicalCollection();
		List<String> lines = FileUtils.readLines(file);
		observationList = new ArrayList<Observation>();
		getOrCreateObservationListById();
		createObservationsListsAndIndexes(lines);
		
		trivialNamesWithinSameObservations = new HashSet<String>();
		observationIdList = new ArrayList<String>(observationListById.keySet());
		Collections.sort(observationIdList);
//				trivialNameSetAll = HashMultiset.create();
		List<String> signatureList = new ArrayList<String>();
		for (String observationId : observationIdList) {
			List<Observation> observations = new ArrayList<Observation>(observationListById.get(observationId));
			int size = observations.size();
			Multiset<String> trivialNameSet = HashMultiset.create();
			for (Observation observation : observations) {
				String trivialName = observation.getChemical().getTrivialName();
				trivialNameSet.add(trivialName);
			}
			List<Multiset.Entry<String>> trivialNameEntries = CMineUtil.getEntryListSortedByCount(trivialNameSet);
			
			Multiset.Entry<String> entry0 = trivialNameEntries.get(0);
			// needs manual correction
			if (entry0.getCount() > 1) {
				// chemicals in same oservation
				for (Observation observation : observations) {
					String trivialName = observation.getChemical().getTrivialName();
					if (trivialName.equals(entry0.getElement())) {
						trivialNamesWithinSameObservations.add(trivialName);
					}
				}
			} else {
				essoilDB.addPlantWithSignatureToList(signatureList, observationId, observations);
				chemicalCollection.addTrivialNames(observations);
			}
		}
		LOG.info("Read ("+observationListById.size()+") observations from: "+file);

	}

	Multimap<String, Observation> getOrCreateObservationListById() {
		if (observationListById == null) {
			observationListById = ArrayListMultimap.create();
		}
		return observationListById;
	}

	public void createObservationListWithUniqueTrivialNames() {
		createObservationsMultisetByTrivialName();
		observationListWithUniqueTrivialNames = new ArrayList<Observation>();
		for (String trivialName : observationByTrivialName.keySet()) {
			Observation observation = new ArrayList<Observation>(observationByTrivialName.get(trivialName)).get(0);
			observationListWithUniqueTrivialNames.add(observation);
		}
		observationByTrivialName = ArrayListMultimap.create();
		for (Observation observation : observationList) {
			String trivial = observation.getChemical().getTrivialName();
			observationByTrivialName.put(trivial, observation);
		}
		LOG.trace("UNIQ1 "+observationByTrivialName.keySet().size());
	}

	private void createObservationsListsAndIndexes(List<String> lines) {
		compoundValueListList = new ArrayList<List<String>>();
		LOG.debug("reading ...");
		observationListById = ArrayListMultimap.create();
		for (String line : lines) {
			Observation observation = Observation.create(line);
			String observationId = observation.getId();
			observationListById.put(observationId, observation);
		}
		LOG.debug("observation ids: "+observationListById.keySet().size());
//			if (!observationListById.containsKey(observationId)) {
//				observationListById.put(observationId, observation);
//			} else {
//				Observation observation1 = new ArrayList<Observation> (observationListById.get(observationId)).get(0);
//				if (!observation1.matchesRecordComponents(observation)) {
//					LOG.debug("no match: "+observation1+"/"+observation);
//				}
//				observationListById.put(observationId, observation);
//			}
//		}
		LOG.debug("observation size: "+observationListById.size());
	}

	private void createObservationsMultisetByTrivialName() {
		observationByTrivialName = ArrayListMultimap.create();
		for (Observation observation : observationList) {
			String trivial = observation.getChemical().getTrivialName();
			observationByTrivialName.put(trivial, observation);
		}
		LOG.trace("UNIQ "+observationByTrivialName.keySet().size());
	}

	public void writeObservations(File file) {
		CMineUtil.writeCSV(file.toString(), Observation.HEADERS, compoundValueListList);
	}

	public void writeTrivialNamesWithinSameObservations(File file) throws IOException {
		FileUtils.writeLines(file, new ArrayList<String>(trivialNamesWithinSameObservations));
	}

	public void summarizeObservations(File observationListFile) throws IOException {
//		List<Multiset.Entry<String>> names = CMineUtil.getEntryListSortedByCount(trivialNameSetAll);
//		List<String> observationCountList = new ArrayList<String>();
//		for (Multiset.Entry<String> entry : names) {
//			observationCountList.add(entry.toString());
//		}
//		FileUtils.writeLines(observationListFile, observationCountList);
	}

	void readObservationsAndCreateIndexes() throws IOException {
		EssoilDB.LOG.debug("read observations");
		readObservations(new File(EssoilDB.ESSOIL, "info_compound_08102015.csv"));
		EssoilDB.LOG.debug("writeObservations");
		writeObservations(new File(EssoilDB.ESSOIL, "info_observation.csv"));
		EssoilDB.LOG.debug("writeSameTrivial");
		writeTrivialNamesWithinSameObservations(new File(EssoilDB.ESSOIL, "trivialNamesWithinSame.txt"));
		summarizeObservations(new File(EssoilDB.ESSOIL, "compoundList.txt"));
		createObservationListWithUniqueBinomials();
		createObservationListWithUniqueTrivialNames();
	}
	
	void readObservations() throws IOException {
		if (rawObservationRecordFile != null) {
			readObservations(rawObservationRecordFile);
		}
	}

	public List<Observation> getObservationList() {
		return observationList;
	}

	public void setRawObservationRecordFile(File file) {
		this.rawObservationRecordFile = file;
	}

	private void processAndWriteChemicals(File chemicalOutputFile) throws IOException {
		List<Observation> observationRecords = new ArrayList<Observation>(observationListById.values());
		Multiset<Observation> observationSet = HashMultiset.create();
		for (Observation observationRecord : observationRecords) {
			Chemical chemical = observationRecord.getChemical();
//			if (chemical != null) {
//				if (observationSet.contains(chemical)) {
//					LOG.trace("dup:"+chemical);
//				}
//				observationSet.add(chemical);
//			}
		}
		List<String> lines = new ArrayList<String>();
		for (Multiset.Entry<Observation> record : observationSet.entrySet()) {
//			LOG.debug(">>>>"+record.getCount()+">"+record.getElement());
			lines.add(record.getElement().toString());
		}
		FileUtils.writeLines(chemicalOutputFile, lines);
		LOG.info("Wrote ("+lines.size()+") observations to: "+chemicalOutputFile);
	}

}
