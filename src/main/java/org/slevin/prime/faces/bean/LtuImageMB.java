package org.slevin.prime.faces.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.slevin.common.LtuImage;
import org.slevin.dao.LtuImageDao;
import org.slevin.util.LtuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.visenze.visearch.Image;
import com.visenze.visearch.ImageResult;
import com.visenze.visearch.InsertStatus;
import com.visenze.visearch.InsertTrans;
import com.visenze.visearch.PagedSearchResult;
import com.visenze.visearch.UploadSearchParams;
import com.visenze.visearch.ViSearch;

@Component(value="ltuImageMB")
@ViewScoped


public class LtuImageMB {
	
	//http://image5.sahibinden.com/photos/09/00/91/2280900916zz.jpg

	//ViSearch client = new ViSearch("d09d93ea1cf82810e7cd2c4e366eb828", "75ac5f6c38c8a9d52edb0d558395fc58");
	//Map<String,String> imageList = new HashMap 
	
	@Autowired
	LtuImageDao imageService;
	
	List<org.slevin.common.LtuImage> images= new ArrayList<org.slevin.common.LtuImage>();
	List<org.slevin.common.LtuImage> searchResultImages= new ArrayList<org.slevin.common.LtuImage>();
	
	
	private UploadedFile file;
	
	
	
	@PostConstruct
	public void init() throws Exception{
		images = imageService.findAll();
	}
	
	public void upload(){
		try {
//			String url = "http://www.example.com/some/path/to/a/file.xml";

	        LtuUtil.insertFile(file.getContents(),file.getFileName()); 
	        LtuImage image = new LtuImage();
	        image.setName(file.getFileName());
	        imageService.persist(image);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			
		}
	}
	
	public void match() throws Exception{
		try {
			List list = LtuUtil.listFile(file.getContents(),file.getFileName()); 
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				
			}

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

	public List<org.slevin.common.LtuImage> getSearchResultImages() {
		return searchResultImages;
	}

	public void setSearchResultImages(
			List<org.slevin.common.LtuImage> searchResultImages) {
		this.searchResultImages = searchResultImages;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	

	
	
	

	
}
