package org.xmlcml.norma.applic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

/** holds chemical-only information.
 * 
 * @author pm286
 *
 */
public class ChemicalCollection {

	private static final Logger LOG = Logger.getLogger(ChemicalCollection.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private Map<String, Multiset<String>> iupacByTrivialName;
	private Multiset<String> trivialNameSetAll;
	private EssoilDB essoilDB;

	public ChemicalCollection(EssoilDB essoilDB) {
		this.essoilDB = essoilDB;
	}

	void checkChemicals(File file) {
		ObservationCollection observationCollection = essoilDB.getOrCreateObservationCollection();
		Multimap<String, Observation> observationListById = observationCollection.getOrCreateObservationListById();
		
			List<String> ids = new ArrayList<String>(observationListById.keySet());
			List<Observation> observationList = new ArrayList<Observation>();
			
			iupacByTrivialName = new HashMap<String, Multiset<String>>();
			for (String id : ids) {
				List<Observation> observations = new ArrayList<Observation>(observationListById.get(id));
				observationList.addAll(observations);
			}
			
	//		Multiset<Chemical> chemicalMultiset = HashMultiset.create();
			for (Observation observation : observationList) {
				String trivialName = observation.getChemical().getTrivialName();
				String formula = observation.getChemical().getFormula();
				String iupac = observation.getChemical().getIupac();
				Chemical chemical = new Chemical(trivialName, formula, iupac);
	//			chemicalMultiset.add(chemical);
				EssoilDB.addToIndexedMultiset(iupacByTrivialName, trivialName, iupac);
			}
	//		CMineUtil. getEntryListSortedByCount(chemicalMultiset);
			
		}

	void addTrivialNames(List<Observation> observations) {
		trivialNameSetAll = HashMultiset.create();
		for (Observation observation : observations) {
			trivialNameSetAll.add(observation.getChemical().getTrivialName());
		}
	}

	public void writeIupacByTrivialName(File file) throws IOException {
		EssoilDB.writeIndexedMultiset(iupacByTrivialName, file);
	}

	void checkAndIndexChemicals() throws IOException {
		checkChemicals(new File(EssoilDB.ESSOIL, "chemicalList.txt"));
		writeIupacByTrivialName(new File(EssoilDB.ESSOIL, "iupacByTrivialName.txt"));
	}

	public Multiset<String> getTrivialNameSetAll() {
		return trivialNameSetAll;
	}
}
