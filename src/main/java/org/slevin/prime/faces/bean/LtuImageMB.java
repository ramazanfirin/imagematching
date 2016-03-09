package org.slevin.prime.faces.bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
	
	private UploadedFile uploadfile;
	private UploadedFile searchFile;
	 private Part file2;
	private String lastUploadedFileName;
	private String lastSearchFileName;
	
	private StreamedContent myImage;

	
	@PostConstruct
	public void init() throws Exception{
		images = imageService.findAll();
	}
	
	public void temp(){
		
	}
	
	
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
//			String url = "http://www.example.com/some/path/to/a/file.xml";
			System.out.println("sdsfsd");
			uploadfile = event.getFile();
			
			System.out.println(uploadfile.getFileName());
			if(uploadfile.getFileName().equals(lastUploadedFileName)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tekrar deneyin", "Tekrar Deneyin"));
				lastUploadedFileName="";
				return;
			}
			
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String newFileName = servletContext.getRealPath("") + File.separator + "resources" + 
                    File.separator + "ltu"  + File.separator + uploadfile.getFileName() ;
			
			InputStream in = new ByteArrayInputStream(uploadfile.getContents());
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "jpg",new File(newFileName));
			
			
//			if(1==1)
//				return;
			
	        LtuUtil.insertFile(uploadfile.getContents(),uploadfile.getFileName()); 
	        LtuImage image = new LtuImage();
	        image.setName(uploadfile.getFileName());
	        imageService.persist(image);
	        lastUploadedFileName = uploadfile.getFileName();
	       
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
			
			System.out.println(searchFile.getFileName());
			if(searchFile.getFileName().equals(lastSearchFileName)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tekrar deneyin", "Tekrar Deneyin"));
				searchResultImages.clear();
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

			 ByteArrayInputStream bis = new ByteArrayInputStream(searchFile.getContents());
			 StreamedContent   myImageasd = new DefaultStreamedContent(bis, "image/png");
			 setMyImage(myImageasd);
		        System.out.println(searchFile.getContents().length +" byte arandi");
		       
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
		return uploadfile;
	}

	public void setFile(UploadedFile file) {
		this.uploadfile = file;
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
	

	
	
	

	
}
