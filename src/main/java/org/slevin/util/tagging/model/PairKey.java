package org.slevin.util.tagging.model;

public class PairKey{
	String name;
	String confidence;
	
	
	
	public PairKey(String name, String confidence) {
		super();
		this.name = name;
		this.confidence = confidence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
}
