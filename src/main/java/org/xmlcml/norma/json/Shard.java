package org.xmlcml.norma.json;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Shard {
	
	private static final Logger LOG = Logger.getLogger(Shard.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	private String name;
	private Integer value;
	
	public Shard(
		String name, 
		Integer value
		) {
		setName(name);
		setValue(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
