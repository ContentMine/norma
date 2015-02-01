package org.xmlcml.norma.json;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

public class CatalogEntry {

	
	private static final Logger LOG = Logger.getLogger(CatalogEntry.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private Hits hits;
	private Collection<Shard> _shards;
	private Integer took;
	private Boolean timed_out;

	public CatalogEntry() {
		
	}

	public CatalogEntry(
		Hits hits,
		Collection<Shard> _shards,
		Integer took,
		Boolean timed_out
			) {
		setHits(hits);
		set_shards(_shards);
		setTook(took);
		setTimed_out(timed_out);
	}
	
	public static CatalogEntry createCatalogEntry(JsonObject jsonObject) {
		CatalogEntry catalogEntry = null;
		try {
			Hits hits                = Hits.createHits(jsonObject.get("hits").getAsJsonObject());
			Collection<Shard> shards = Shard.getAsShardArray(jsonObject, "_shards");
			Integer took             = jsonObject.get("took").getAsInt();
			Boolean timed_out        = jsonObject.get("timed_out").getAsBoolean();
	
			catalogEntry = new CatalogEntry(
				hits,
				shards,
				took,
				timed_out
				);
		} catch (Exception e) {
			throw new RuntimeException("cannot create CatalogEntry", e);
		}
		return catalogEntry;
	}
	
	public Hits getHits() {
		return hits;
	}
	public void setHits(Hits hits) {
		this.hits = hits;
	}
	public Collection<Shard> get_shards() {
		return _shards;
	}
	public void set_shards(Collection<Shard> _shards) {
		this._shards = _shards;
	}
	public Integer getTook() {
		return took;
	}
	public void setTook(Integer took) {
		this.took = took;
	}
	public Boolean getTimed_out() {
		return timed_out;
	}
	public void setTimed_out(Boolean timed_out) {
		this.timed_out = timed_out;
	}
	
	/**
		Hits hits,
		Collection<Shard> _shards,
		Integer took,
		Boolean timed_out
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(hits.toString()+" / ");
		sb.append(Shard.toString(_shards)+" / ");
		sb.append(took+" / ");
		sb.append(timed_out+" / ");
		return sb.toString();
		
	}

}

