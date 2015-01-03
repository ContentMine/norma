package org.xmlcml.norma.json;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

}

