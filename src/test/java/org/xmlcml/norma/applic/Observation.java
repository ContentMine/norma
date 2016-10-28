package org.xmlcml.norma.applic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Observation {

	private static final Logger LOG = Logger.getLogger(Observation.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public static final List<String> HEADERS = Arrays.asList(new String[]{
			"id",
			"trivial",
			"cas",
			"percent",
			"part",
			"method",
			"formula",
			"type",
			"activities",
			"plant",
			"conditions",
			"iupac",
			
			});

	private String id;
	private String percent;
	private String part;
	private String method;
	private String plant;
	private String conditions;
	private Chemical chemical;
	
	public Observation() {
		chemical = new Chemical();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		part = part.equals("Aerial parts") ? "aerial part" : part;
		part = part.equals("Leaf") ? "leaf" : part;
		this.part = part.equals("Leaves") ? "leaf" : part;
	}


	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlantType(String plant) {
		plant = plant.equals("Weed") ? "Invasive" : plant;
		plant = plant.equals("native") ? "Native" : plant;
		plant = plant.equals("NA") ? null : plant;
		this.plant = plant;
	}

	public static Observation create(String line) {
		Observation experimentalData = new Observation();
		String[] field = line.split("#");
		if (field.length != 13) {
			LOG.debug("Bad line "+line);
			return null;
		}
		experimentalData.setId(field[1]);
		experimentalData.getChemical().setTrivialName(field[2]);
		experimentalData.getChemical().setCas(field[3]);
		experimentalData.setPercent(field[4]);
		experimentalData.setPart(field[5]);
		experimentalData.setMethod(field[6]);
		experimentalData.getChemical().setFormula(field[7]);
		experimentalData.getChemical().setChemicalType(field[8]);
		experimentalData.getChemical().setActivities(field[9]);
		experimentalData.setPlantType(field[10]);
		experimentalData.setConditions(field[11]);
		experimentalData.getChemical().setIupac(field[12]);
		LOG.debug(experimentalData.getChemical());
		return experimentalData;
		

	}

	public List<String> getValueList() {
		
		List<String> list = new ArrayList<String>();
		list.add(id);
		list.add(percent);
		list.add(part);
		list.add(method);
//		list.add(activities.toString());
		list.add(plant);
		list.add(conditions);
		list.addAll(this.getChemical().getValues());
		
		return list;
	}

	public boolean matchesRecordComponents(Observation experimental) {
		if (!matches("id", this.id, experimental.id, this, experimental)) {
			return false;
		}
		//trivial omitted
		// cas omitted
		if (!matches("part", this.part, experimental.part, this, experimental)) {
//			return false;
		}
		// this is too variable
//		if (!matches("method", this.method, compound.method, this, compound)) {
//			return false;
//		}
		// formula omitted
		// type omitted
		// activities omitted
		if (!matches("plant", this.plant, experimental.plant, this, experimental)) {
//			return false;
		}
		return true;
	}

	private boolean matches(String type, String s, String ss, Observation thisExperimental, Observation experimental) {
		if (s.equals(ss)) {
			return true;
		}
		LOG.trace(type+": "+s+" != "+ss
//				+"\n ....  "+thisCompound+" ... "+compound
				);
		return false;
	}
	
	public String toString() {
		return 	id +" t: "+getChemical().getTrivialName()/*+" c: "+cas*//*+" %: "+percent*/+" p: "+part+": "+method+" m: "/*+formula*//*+"\n"*/
	        /*+"t: "+chemicalType*//*+" a: "+toString(activities)*/+" p: "+plant+" c: "+conditions /*+": "+iupac*/;

	}

	private String toString(String[] activities) {
		return Arrays.asList(activities).toString();
	}

	/** gets binomial by following key.
	 * 
	 * @param plantCollection TODO
	 * @return
	 */
	public String getBinomial(PlantCollection plantCollection) {
		String binomial = null;
		if (this != null) {
			String id = getId();
			if (id != null) {
				PlantRecord plant = plantCollection.plantById.get(id);
				binomial = plant == null ? null : plant.getBinomial();
			}
		}
		return binomial;
	}

	public static String makeSignature(List<Observation> experimentals) {
		StringBuilder sb = new StringBuilder();
		sb.append(experimentals.get(0).getId()+":: ");
		for (Observation experimental : experimentals) {
			sb.append(""+experimental.getChemical().getTrivialName()+": "+experimental.getPercent()+"; ");
		}
		return sb.toString();
	}

	
	public Chemical getChemical() {
		return chemical;
	}
	

}
