package org.slevin.util.tagging.model;

import java.util.ArrayList;
import java.util.List;

public class Tag {

List<PairKey> tagList = new ArrayList<PairKey>();

public void add(String name,String confidence){
	getTagList().add(new PairKey(name, confidence));
}

public List<PairKey> getTagList() {
	return tagList;
}

public void setTagList(List<PairKey> tagList) {
	this.tagList = tagList;
}



}


