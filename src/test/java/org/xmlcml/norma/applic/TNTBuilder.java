package org.xmlcml.norma.applic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cmine.util.CMineUtil;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class TNTBuilder {

	private EssoilDB essoilDB;

	private List<String> trivialNameList;

	private Map<String, Integer> serialByTrivialName;

	private static final Logger LOG = Logger.getLogger(TNTBuilder.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public TNTBuilder(EssoilDB essoilDB) {
		this.essoilDB = essoilDB;
	}

	/**
	xread 
	'Data re-extracted by Ross Mounce from: W. Opitz (2008). Classification, Natural History, and Evolution of Epiphloeinae (Coleoptera: Cleridae). Part VI. the Genera Epiphlaeus Spinola and Opitzius Barr. Annales Zoologici 58(1):1-34.'
	22 5	 * 
	outgroup 			1000000000000000000000
	Opitzius_thoracicus 		1000010110010100000101
	Epiphlaeus_tigrinus_group 	1111101001010001001111
	Epiphlaeus_fundurufus_group 	1111101000110010101111
	Epiphlaeus_adona_group 		1111101000101010111111
	;
	cnames
	{ 0 Cross_vein_MP4-MP5 present absent;
	{ 1 Scape not_very_long very_long;
	{ 2 Antennal_club not_abbreviated abbreviated;
	{ 3 Ocular_setal_tuft absent present;
	{ 4 Genal_setal_tuft absent present;
	{ 5 Maxillary_terminal_palpomere digitiform curvate-rectangulate;
	{ 6 Maxillary_terminal_palpomere not_subsecuriform subsecuriform;
	{ 7 Pronotal_disc smooth asperous;
	{ 8 Pronotal_lateral_carina not_entire entire;
	{ 9 Pronotal_disc without_transverse_carina with_transverse_carina;
	{ 10 Pronotal_disc not_bitumescent bitumescent;
	{ 11 Size_of_punctations_in_elytral_basal_half not_large large;
	{ 12 Size_of_elytral_punctations not_very_small very_small;
	{ 13 Elytral_punctations rowed not_rowed;
	{ 14 Density_of_elytral_2°_setae not_very_dense very_dense;
	{ 15 Elytral_disc without_spheroid_markings with_spheroid_markings;
	{ 16 Setal_patches_on_elytral_disc absent present;
	{ 17 Setal_patches_on_elytral_disc not_black black;
	{ 18 Pronotal_arch without_transverse_wrinkles with_transverse_wrinkles;
	{ 19 Size_of_pygidium not_very_large very_large;
	{ 20 Vertex without_furrow with_furrow;
	{ 21 Maxillary_terminal_palpomere digitiform not_digitiform;
	
	;
	proc/;
		 * @param file
		 * @throws IOException
		 */
	
		
		public void writeTNT(File file, int maxTaxa) throws IOException {
			Multiset<String> trivialNameSetAll = essoilDB.getOrCreateChemicalCollection().getTrivialNameSetAll();
			trivialNameList = new ArrayList<String>(trivialNameSetAll.elementSet());
			Collections.sort(trivialNameList);
			serialByTrivialName = new HashMap<String, Integer>();
			for (int i = 0; i < trivialNameList.size(); i++) {
				String trivialName = trivialNameList.get(i);
				serialByTrivialName.put(trivialName, new Integer(i));
			}
			List<String> lines = new ArrayList<String>();
			addTNTHeader(lines);
//			int total = observationIdList.size();
			int count = 0;
			List<String> lines1 = new ArrayList<String>();
			List<Observation> normalizedDataList = essoilDB.getOrCreateObservationCollection().getObservationList();
			for (int j = 0; j < normalizedDataList.size(); j++) {
				Observation observation = normalizedDataList.get(j);
				String plant = observation.getPlant();
				
				
				
				// check if there are multiple signatures
				Multiset<String> trivialNameSet = HashMultiset.create();
				StringBuilder sb = createZeroFilledStringBuilder(trivialNameList.size());
				// if chemical present, poke "1"
				List<Observation> observations = new ArrayList<Observation>(); // just so it compiles
				for (Observation observation1 : observations) {
					String trivialName = observation1.getChemical().getTrivialName();
					Integer serial = serialByTrivialName.get(trivialName);
					if (serial != null) {
						sb.setCharAt(serial.intValue(), '1');
					}
					trivialNameSet.add(trivialName);
				}
				String binarySig = sb.toString();
	//			LOG.debug(binarySig.substring(0,  200));
				List<Multiset.Entry<String>> trivialNameEntries = CMineUtil.getEntryListSortedByCount(trivialNameSet);
				String compoundId = null; 
				// FIXME just so it compiles
				if (trivialNameEntries.size() > 0) {
					Multiset.Entry<String> entry0 = trivialNameEntries.get(0);
					if (entry0.getCount() == 1) {
						PlantRecord plant1 = essoilDB.getOrCreatePlantCollection().getPlantById().get(compoundId);
						String binomial = plant1 == null ? null : plant1.getBinomial();
						if (binomial != null) {
							// have to replace spaces
							String line = binomial.replaceAll("\\s+", "_")+"     "+binarySig; // assume this is just whitespace
							lines1.add(line);
							count++;
						}
					}
				}
			}
			lines.add(""+trivialNameList.size()+" "+count);
			lines.addAll(lines1);
			lines.add(";");
			// could add trivial names here
			lines.add("proc/;");
			FileUtils.writeLines(file, lines);
		}

	private StringBuilder createZeroFilledStringBuilder(int size) {
		StringBuilder sb = new StringBuilder();
		// fill with zeros
		for (int i = 0; i < size; i++) {
			sb.append("0");
		}
		return sb;
	}

	private void addTNTHeader(List<String> lines) {
		lines.add("mxram 1000;");
		lines.add("xread");
		lines.add("'title from ESSoil'");
	}
}
