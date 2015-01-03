package org.xmlcml.norma.json;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

}
