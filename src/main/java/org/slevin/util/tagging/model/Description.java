package org.slevin.util.tagging.model;

import java.util.ArrayList;
import java.util.List;

public class Description {

	List<String> tags = new ArrayList<String>();
	Caption caption = new Caption();
	
	public void addCaption(String text,String confidence){
		caption.setConfidence(confidence);
		caption.setText(text);
	}
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}
	
	
	
}


