package org.xmlcml.norma.applic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PlantRecord {

	private static final Logger LOG = Logger.getLogger(PlantRecord.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	/**
	 * 
	 */

	public static final List<String> HEADERS = Arrays.asList(new String[]{
			"id",
			"binomial",
			"place",
			"family",
			"plant",
			"year",
			"bib",
			"title",
			"authors",
			"jtitle",
			
			});

	private String id;
	private String binomial;
	private String place;
	private String family;
	private String plant;

	private BibliographicRecord bibliographicRecord;
	
	public PlantRecord() {
		this.bibliographicRecord = new BibliographicRecord();
	}

	public BibliographicRecord getBibliographicRecord() {
		return bibliographicRecord;
	}

	public void setBibliographicRecord(BibliographicRecord bibliographicRecord) {
		this.bibliographicRecord = bibliographicRecord;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBinomial() {
		return binomial;
	}

	public void setBinomial(String binomial) {
		this.binomial = binomial;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public static PlantRecord create(String line) {
		PlantRecord plantRecord = new PlantRecord();
		String[] field = line.split("#");
		if (field.length != 11) {
			LOG.debug("Bad line "+line+"/"+field.length);
			return null;
		}
		plantRecord.setId(field[1]);
		plantRecord.setBinomial(field[2]);
		plantRecord.setPlace(field[3]);
		plantRecord.getBibliographicRecord().setYear(field[4]);
		plantRecord.getBibliographicRecord().setBib(field[5]);
		plantRecord.getBibliographicRecord().setTitle(field[6]);
		plantRecord.getBibliographicRecord().setAuthors(field[7]);
		plantRecord.getBibliographicRecord().setJournalTitle(field[8]);
		plantRecord.setFamily(field[9]);
		plantRecord.setPlant(field[10]);
		return plantRecord;
	}

	public List<String> getValueList() {
		
		List<String> list = new ArrayList<String>();
		list.add(id);
		list.add(binomial);
		list.add(place);
//		list.addAll(bibliographicRecord.getValues());
		list.add(String.valueOf(bibliographicRecord.hashCode())); // good enough for now
		list.add(family);
		list.add(plant);
		
		return list;
	}

}
