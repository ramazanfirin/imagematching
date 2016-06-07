package org.slevin.util.tagging.model;

public class ApiResult {

	Categories categories = new Categories();
	Tag tag=new Tag();
	Adult adult=new Adult();
	Description description=new Description();
	
	public Categories getCategories() {
		return categories;
	}
	public void setCategories(Categories categories) {
		this.categories = categories;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public Adult getAdult() {
		return adult;
	}
	public void setAdult(Adult adult) {
		this.adult = adult;
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	
}
