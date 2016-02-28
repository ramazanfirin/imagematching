package org.slevin.prime.faces.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.model.UploadedFile;
import org.slevin.common.LtuImage;
import org.slevin.dao.LtuImageDao;
import org.slevin.util.LtuDto;
import org.slevin.util.LtuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="ltuImageMB")
@ApplicationScoped


public class LtuImageMB {
	
	//http://image5.sahibinden.com/photos/09/00/91/2280900916zz.jpg

	//ViSearch client = new ViSearch("d09d93ea1cf82810e7cd2c4e366eb828", "75ac5f6c38c8a9d52edb0d558395fc58");
	//Map<String,String> imageList = new HashMap 
	
	@Autowired
	LtuImageDao imageService;
	
	List<org.slevin.common.LtuImage> images= new ArrayList<org.slevin.common.LtuImage>();
	List<LtuDto> searchResultImages= new ArrayList<LtuDto>();
	
	private UploadedFile file;
	private UploadedFile searchFile;
	 private Part file2;
	private String lastUploadedFileName;
	private String lastSearchFileName;
	
	
	@PostConstruct
	public void init() throws Exception{
		images = imageService.findAll();
	}
	
	public void upload(){
		try {
//			String url = "http://www.example.com/some/path/to/a/file.xml";

			System.out.println(file.getFileName());
			if(file.getFileName().equals(lastUploadedFileName)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tekrar deneyin", "Tekrar Deneyin"));
				lastUploadedFileName="";
				return;
			}
				
			
	        LtuUtil.insertFile(file.getContents(),file.getFileName()); 
	        LtuImage image = new LtuImage();
	        image.setName(file.getFileName());
	        imageService.persist(image);
	        lastUploadedFileName = file.getFileName();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			
		}
	}
	
	public void match() throws Exception{
		try {
			
			System.out.println(searchFile.getFileName());
			if(searchFile.getFileName().equals(lastSearchFileName)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tekrar deneyin", "Tekrar Deneyin"));
				lastSearchFileName="";
				return;
			}
			String result= LtuUtil.listFile(searchFile.getContents(),searchFile.getFileName()); 
			searchResultImages.clear();
			searchResultImages.addAll(LtuUtil.parseJson(result));
			lastSearchFileName= searchFile.getFileName();
//			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//				Object object = (Object) iterator.next();
//				
//			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	public  String findImageUrl(String name) throws Exception{
		List<org.slevin.common.LtuImage> list =imageService.findByProperty("name", name);
		if(list.size()>0){
			return list.get(0).getPath();
		}else
			return "";
	}

	public LtuImageDao getImageService() {
		return imageService;
	}

	public void setImageService(LtuImageDao imageService) {
		this.imageService = imageService;
	}

	public List<org.slevin.common.LtuImage> getImages() {
		return images;
	}

	public void setImages(List<org.slevin.common.LtuImage> images) {
		this.images = images;
	}



	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getLastUploadedFileName() {
		return lastUploadedFileName;
	}

	public void setLastUploadedFileName(String lastUploadedFileName) {
		this.lastUploadedFileName = lastUploadedFileName;
	}

	public UploadedFile getSearchFile() {
		return searchFile;
	}

	public void setSearchFile(UploadedFile searchFile) {
		this.searchFile = searchFile;
	}

	public String getLastSearchFileName() {
		return lastSearchFileName;
	}

	public void setLastSearchFileName(String lastSearchFileName) {
		this.lastSearchFileName = lastSearchFileName;
	}

	public List<LtuDto> getSearchResultImages() {
		return searchResultImages;
	}

	public void setSearchResultImages(List<LtuDto> searchResultImages) {
		this.searchResultImages = searchResultImages;
	}
	

	
	
	

	
}
