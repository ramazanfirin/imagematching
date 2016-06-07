package org.slevin.util.tagging.model;

public class Caption{
	String text;
	String confidence;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
}
