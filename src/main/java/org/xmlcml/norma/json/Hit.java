package org.xmlcml.norma.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/** a hit (entry) from the ContentMine catalog.
 * 
 * @author pm286
 *
 */
public class Hit {

	private static final Logger LOG = Logger.getLogger(Hit.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private Double _score;
    private String _type;
    private String _id;
    private BibSource _source;
    private String _index;
    
    public Hit() {
    	
    }
    public Hit(
		Double _score,
	    String _type,
	    String _id,
	    BibSource _source,
	    String _index
    		) {
    	set_score(_score);
    	set_type(_type);
    	set_id(_id);
    	set_source(_source);
    	set_index(_index);
    }

    public static Hit createHit(JsonObject jsonObject) {
		Double _score           = jsonObject.get("_score").getAsDouble();
		String _type            = jsonObject.get("_type").getAsString();
		String _id              = jsonObject.get("_id").getAsString();
		BibSource _source       = BibSource.createBibSource(jsonObject.get("_source").getAsJsonObject());
		String _index           = jsonObject.get("_index").getAsString();
		Hit hit = new Hit(
				_score,
				_type,
				_id,
				_source,
				_index
				);
		return hit;
    }
    
    public Double get_score() {
		return _score;
	}

	public void set_score(Double _score) {
		this._score = _score;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public BibSource get_source() {
		return _source;
	}

	public void set_source(BibSource _source) {
		this._source = _source;
	}

	public String get_index() {
		return _index;
	}

	public void set_index(String _index) {
		this._index = _index;
	}

	/** extract an array of Hit from a Json object.
	 * 
	 * @param jsonObject
	 * @param name
	 * @return
	 */
	public static List<Hit> getAsHitArray(JsonObject jsonObject, String name ) {
		JsonArray jsonArray = jsonObject.get(name).getAsJsonArray();
		List<Hit> hitList = new ArrayList<Hit>();
		for (int i = 0; i < jsonArray.size(); i++) {
			hitList.add(Hit.createHit(jsonArray.get(i).getAsJsonObject()));
		}
		return hitList;
	}
	
	/**
	private Double _score;
    private String _type;
    private String _id;
    private BibSource _source;
    private String _index;
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(_score + " / ");
		sb.append(_id  + " / ");
		sb.append(_source  + " / ");
		sb.append(_index  + " / ");
		return sb.toString();
	}

}
