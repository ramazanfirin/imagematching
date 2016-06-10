package org.slevin.prime.faces.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;

import org.json.simple.parser.ParseException;
import org.primefaces.component.tabview.TabView;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slevin.common.AyonixPerson;
import org.slevin.dao.AyonixPersonDao;
import org.slevin.util.AyonixUtil;
import org.slevin.util.tagging.model.PairKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="faceMB")
@ApplicationScoped


public class FaceMB {
	
	//http://image5.sahibinden.com/photos/09/00/91/2280900916zz.jpg

	//ViSearch client = new ViSearch("d09d93ea1cf82810e7cd2c4e366eb828", "75ac5f6c38c8a9d52edb0d558395fc58");
	//Map<String,String> imageList = new HashMap 
	
	@Autowired
	AyonixPersonDao ayonixPersonService;
	
	private TabView tabView;

	private UploadedFile uploadfile;
	private UploadedFile searchFile;
	private UploadedFile identityFile;
	List<AyonixPerson> personList;
	List<PairKey> resultList;
	
	private StreamedContent myImage;
	
	String name;
	String surname;

	String userIdForDelete;
	
	@PostConstruct
	public void init() throws Exception{
		updateUserList();
	}
	
	public void updateUserList() throws Exception{
		personList = ayonixPersonService.findAll();
	}
	
	public void upload() throws ParseException{
		try {
			System.out.println("geldi");
			String userId = AyonixUtil.enroll(name, surname, searchFile.getContents());

			ayonixPersonService.persist(new AyonixPerson(name,surname,userId));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
			updateUserList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", e.getMessage()));
		}
	}
	
	public void deletePerson() throws ParseException{
		try {
			System.out.println("geldi");
			

			List<AyonixPerson> list = ayonixPersonService.findByProperty("userId", userIdForDelete);
//			if(list.size()==0){
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", "user bulunamadı"));
//				return;
//			}
			AyonixUtil.deletePerson(userIdForDelete);
			//ayonixPersonService.remove(list.get(0).getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
			updateUserList();
			tabView.setActiveIndex(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", e.getMessage()));
		}
	}

	public void identityPerson() throws ParseException{
		try {
			System.out.println("geldi");
		
			resultList = AyonixUtil.searchPerson(identityFile.getContents());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"tamamlandi", "tamamlandi"));
			updateUserList();
			tabView.setActiveIndex(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"hata", e.getMessage()));
		}
	}


	public UploadedFile getFile() {
		return uploadfile;
	}

	public void setFile(UploadedFile file) {
		this.uploadfile = file;
	}

	

	public UploadedFile getSearchFile() {
		return searchFile;
	}

	public void setSearchFile(UploadedFile searchFile) {
		this.searchFile = searchFile;
	}

	

	
	public StreamedContent getMyImage() {
		//System.out.println(myImage.getStream());
		return myImage;
	}

	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	public UploadedFile getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(UploadedFile uploadfile) {
		this.uploadfile = uploadfile;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<AyonixPerson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<AyonixPerson> personList) {
		this.personList = personList;
	}

	public String getUserIdForDelete() {
		return userIdForDelete;
	}

	public void setUserIdForDelete(String userIdForDelete) {
		this.userIdForDelete = userIdForDelete;
	}

	public UploadedFile getIdentityFile() {
		return identityFile;
	}

	public void setIdentityFile(UploadedFile identityFile) {
		this.identityFile = identityFile;
	}

	public List<PairKey> getResultList() {
		return resultList;
	}

	public void setResultList(List<PairKey> resultList) {
		this.resultList = resultList;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	

	
	
	

	
}
