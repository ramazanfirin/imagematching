package org.slevin.util.tagging.model;

import java.util.ArrayList;
import java.util.List;

public class Categories {
	String score;
	String name;
	List<String> celebrities= new ArrayList<String>();
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getCelebrities() {
		return celebrities;
	}
	public void setCelebrities(List<String> celebrities) {
		this.celebrities = celebrities;
	}
}
