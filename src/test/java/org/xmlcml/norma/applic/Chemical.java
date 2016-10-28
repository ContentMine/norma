package org.xmlcml.norma.applic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** holds  chemical information for isolated compound.
 * 
 * @author pm286
 *
 */
public class Chemical {

	private static final Logger LOG = Logger.getLogger(Chemical.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private static final String NA = "NA";

	private String trivialName;
	private String formula;
	private String iupac;
	private String cas;
	private String chemicalType;
	private String[] activities;

	public Chemical(String trivialName, String formula, String iupac) {
		this.trivialName = trivialName;
		this.formula = formula;
		this.iupac = iupac;
		
	}
	
	public Chemical() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		result = prime * result + ((iupac == null) ? 0 : iupac.hashCode());
		result = prime * result + ((trivialName == null) ? 0 : trivialName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chemical other = (Chemical) obj;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		if (iupac == null) {
			if (other.iupac != null)
				return false;
		} else if (!iupac.equals(other.iupac))
			return false;
		if (trivialName == null) {
			if (other.trivialName != null)
				return false;
		} else if (!trivialName.equals(other.trivialName))
			return false;
		return true;
	}

	public String getTrivialName() {
		return trivialName;
	}

	public void setTrivialName(String trivialName) {
		this.trivialName = trivialName;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = NA.equals(formula) ? "" : formula;
	}

	public String getIupac() {
		return iupac;
	}

	public String getCas() {
		return cas;
	}

	public void setCas(String cas) {
		this.cas = NA.equals(cas) ? "" : cas;
	}

	public void setChemicalType(String chemicalType) {
		this.chemicalType = chemicalType;
	}


	public void setActivities(String acts) {
		if (NA.equals(acts)) {
			this.activities = new String[0];
		} else {
			this.activities = acts.split("\\s*,\\s*");
			LOG.trace(activities.length);
		}
	}


	public void setIupac(String iupac) {
		if (NA.equals(iupac)) {
			iupac ="";
		} else {
			iupac = replaceLowerCaseStereo(iupac);
		}
		this.iupac = iupac;
	}

	private String replaceLowerCaseStereo(String iupac) {
		for (int i = 1; i <= 9; i++) {
			iupac = iupac.replaceAll(""+i+"s", ""+i+"S");
			iupac = iupac.replaceAll(""+i+"as", ""+i+"aS");
			iupac = iupac.replaceAll(""+i+"r", ""+i+"R");
			iupac = iupac.replaceAll(""+i+"ar", ""+i+"aR");
			iupac = iupac.replaceAll(""+i+"z", ""+i+"Z");
			iupac = iupac.replaceAll(""+i+"e", ""+i+"E");
			iupac = iupac.replaceAll(""+i+"h", ""+i+"H");
		}
		return iupac;
	}

	public List<String> getValues() {
		List<String> values = new ArrayList<String>();
		values.add(trivialName);
		values.add(cas);
		values.add(formula);
		values.add(chemicalType);
		values.add(iupac);
		values.add(Arrays.asList(activities).toString());
		return values;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(trivialName+": ");
		sb.append(cas+": ");
		sb.append(formula+": ");
		sb.append(chemicalType+": ");
		sb.append(iupac);
		sb.append(activities);
		return sb.toString();
	}

}
