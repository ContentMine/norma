package org.xmlcml.norma.json;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

/** a collection of hits from the ContentMine catalog.
 * 
 * @author pm286
 *
 */
public class Hits {

	
	private static final Logger LOG = Logger.getLogger(Hits.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	private Collection<Hit> hits;
	private Integer total;
	private Double max_score;
	
	public Hits() {
		
	}
	
	public Hits(
			Collection<Hit> hits,
			Integer total,
			Double max_score
		) {
		setHits(hits);
		setTotal(total);
		setMax_score(max_score);
	}

	public static Hits createHits(JsonObject jsonObject) {
		Collection<Hit> hits      = Hit.getAsHitArray(jsonObject, "hits");
		Integer total             = jsonObject.get("total").getAsInt();
		Double max_score          = jsonObject.get("max_score").getAsDouble();

		Hits hitx = new Hits(
				hits,
				total,
				max_score
				);
		return hitx;
	}
	

	public Collection<Hit> getHits() {
		return hits;
	}

	public void setHits(Collection<Hit> hits) {
		this.hits = hits;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Double getMax_score() {
		return max_score;
	}

	public void setMax_score(Double max_score) {
		this.max_score = max_score;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(hits.toString() + " / ");
		sb.append(total+" / ");
		sb.append(max_score+" / ");
		return sb.toString();
	}

}
