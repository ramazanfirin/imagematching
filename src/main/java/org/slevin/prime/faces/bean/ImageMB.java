package org.slevin.prime.faces.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slevin.dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.visenze.visearch.Image;
import com.visenze.visearch.ImageResult;
import com.visenze.visearch.InsertStatus;
import com.visenze.visearch.InsertTrans;
import com.visenze.visearch.PagedSearchResult;
import com.visenze.visearch.UploadSearchParams;
import com.visenze.visearch.ViSearch;

import org.apache.commons.io.FilenameUtils;

@Component(value="imageMB")
@ViewScoped


public class ImageMB {
	
	//http://image5.sahibinden.com/photos/09/00/91/2280900916zz.jpg
	//http://demo.ltutech.com/static/media_root/presentation/a9ab7ad1-c2d2-4ee5-b51f-13822a3a6222/thumbs/01.portrait.jpg
	
	//https://image5.sahibinden.com/photos/64/17/63/203641763m86.jpg

	//ViSearch client = new ViSearch("d09d93ea1cf82810e7cd2c4e366eb828", "75ac5f6c38c8a9d52edb0d558395fc58");
	
	ViSearch client = new ViSearch("0b22888aa60aa36cf03d9c45fa734d21", "3649a4d895068fcd8994e4a6cd174bdc");
	
	//0b22888aa60aa36cf03d9c45fa734d21 3649a4d895068fcd8994e4a6cd174bdc
	//Map<String,String> imageList = new HashMap 
	
	@Autowired
	ImageDao imageService;
	
	List<org.slevin.common.Image> images= new ArrayList<org.slevin.common.Image>();
	List<org.slevin.common.Image> searchResultImages= new ArrayList<org.slevin.common.Image>();
	
	
	String url;
	String urlMatching;
	String minScore;
	
	@PostConstruct
	public void init() throws Exception{
		images = imageService.findAll();
		System.out.println(images.size() + "image bulundu");
	}
	
	public void upload(){
		try {
//			String url = "http://www.example.com/some/path/to/a/file.xml";

	        String baseName = FilenameUtils.getBaseName(url);
			String transId=insertIMage(client, baseName, url);
			
			List<org.slevin.common.Image> list=imageService.findByProperty("url", url);
			if(list==null || list.size()==0){
			org.slevin.common.Image image=new org.slevin.common.Image();
			prepareImage(image, transId,baseName);
			imageService.persist(image);
			}else{
				org.slevin.common.Image image=list.get(0);
				prepareImage(image, transId,baseName);
				imageService.merge(image);
			}
			images.clear();
			images.addAll(imageService.findAll());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			
		}
	}
	
	public void match() throws Exception{
		try {
			Date startDate = new Date();
			PagedSearchResult result = searchImage(client,urlMatching,minScore);
			Date endDate = new Date();
			List<ImageResult> list = result.getResult();
			
			System.out.println(endDate.getTime()-startDate.getTime());
			searchResultImages.clear();
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ImageResult imageResult = (ImageResult) iterator.next();
				org.slevin.common.Image image = new org.slevin.common.Image();
				image.setName(imageResult.getImName());
				image.setUrl(findImageUrl(imageResult.getImName()));
				image.setTransId(imageResult.getScore().toString());
				searchResultImages.add(image);
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	public  String findImageUrl(String name) throws Exception{
		List<org.slevin.common.Image> list =imageService.findByProperty("name", name);
		if(list.size()>0){
			return list.get(0).getUrl();
		}else
			return "";
	}
	
	public void updateStatus() throws Exception{
		List<org.slevin.common.Image> list=imageService.findByProperty("status", "NOT_READY");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			org.slevin.common.Image image = (org.slevin.common.Image) iterator.next();
			InsertStatus status=client.insertStatus(image.getTransId());
			if(status.getSuccessCount()==1)
				image.setStatus("READY");
				imageService.merge(image);
		}
		
		images.clear();
		images.addAll(imageService.findAll());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
		
	}
	
	public void prepareImage(org.slevin.common.Image image,String transId,String name){
		image.setName(name);
		image.setStatus("NOT_READY");
		image.setTransId(transId);
		image.setUrl(url);
	}
	
	public static String insertIMage(ViSearch client,String name,String url) throws Exception{
		
		// the list of images to be indexed
		List<Image> images = new ArrayList<Image>();
		// the unique identifier of the image 'im_name'
		String imName = name;
		// the publicly downloadable url of the image 'im_url'
		String imUrl = url;
		images.add(new Image(imName, imUrl));
		// calls the insert endpoint to index the image
		InsertTrans insertTrans = client.insert(images);
		if(insertTrans.getErrorList()!=null && insertTrans.getErrorList().size()>0){
			throw new Exception(insertTrans.getErrorList().get(0).getErrorMessage());
			
		}
		System.out.println(name +" inserted."+insertTrans.getTransId());
		return insertTrans.getTransId();
		
	}
	
	public static PagedSearchResult searchImage(ViSearch client,String url ,String minScore){
		UploadSearchParams params = new UploadSearchParams(url);
		
		params.setScore(true);
		params.setScoreMin(Float.parseFloat(minScore));
		params.setLimit(20);
		params.setPage(1);
		
		PagedSearchResult searchResult = client.uploadSearch(params);
		searchResult.getResult();

		return searchResult;
	}

	public ImageDao getImageService() {
		return imageService;
	}

	public void setImageService(ImageDao imageService) {
		this.imageService = imageService;
	}

	public List<org.slevin.common.Image> getImages() {
		return images;
	}

	public void setImages(List<org.slevin.common.Image> images) {
		this.images = images;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlMatching() {
		return urlMatching;
	}

	public void setUrlMatching(String urlMatching) {
		this.urlMatching = urlMatching;
	}

	public String getMinScore() {
		return minScore;
	}

	public void setMinScore(String minScore) {
		this.minScore = minScore;
	}

	public List<org.slevin.common.Image> getSearchResultImages() {
		return searchResultImages;
	}

	public void setSearchResultImages(
			List<org.slevin.common.Image> searchResultImages) {
		this.searchResultImages = searchResultImages;
	}
	
}
