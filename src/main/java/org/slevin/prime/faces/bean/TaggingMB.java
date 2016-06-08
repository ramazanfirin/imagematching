package org.slevin.prime.faces.bean;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.json.simple.parser.ParseException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slevin.dao.LtuImageDao;
import org.slevin.util.LtuDto;
import org.slevin.util.TaggingUtil;
import org.slevin.util.tagging.model.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="taggingMB")
@ApplicationScoped


public class TaggingMB {
	
	//http://image5.sahibinden.com/photos/09/00/91/2280900916zz.jpg

	//ViSearch client = new ViSearch("d09d93ea1cf82810e7cd2c4e366eb828", "75ac5f6c38c8a9d52edb0d558395fc58");
	//Map<String,String> imageList = new HashMap 
	
	private DashboardModel model;
	
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
	
	ApiResult apiResult;

	
	@PostConstruct
	public void init() throws Exception{
		images = imageService.findAll();
		
	    model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        DashboardColumn column4 = new DefaultDashboardColumn();
        DashboardColumn column5 = new DefaultDashboardColumn();
         
        column1.addWidget("Categories");
        column2.addWidget("Adult");
         
        column3.addWidget("Tag");
        column4.addWidget("Description");
        
        column1.addWidget("Faces");
        column2.addWidget("ImageType");
        column3.addWidget("Color"); 
      
 
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        model.addColumn(column4);
        model.addColumn(column5);
	}
	
	public void temp(){
		
	}
	
	public void upload() throws ParseException{
		System.out.println("geldi");
		
		
		//System.out.println(searchFile.getFileName());
//		if(searchFile.getFileName().equals(lastUploadedFileName)){
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tekrar deneyin", "Tekrar Deneyin"));
//			lastUploadedFileName="";
//			return;
//		}
		
		String result =TaggingUtil.sendFile(searchFile.getContents());
		apiResult = TaggingUtil.parseResponse(result);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(searchFile.getContents());
		 StreamedContent   myImageasd = new DefaultStreamedContent(bis, "image/png");
		 setMyImage(myImageasd);
		 lastUploadedFileName = searchFile.getFileName();
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
			
//			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//			String newFileName = servletContext.getRealPath("") + File.separator + "resources" + 
//                    File.separator + "ltu"  + File.separator + uploadfile.getFileName() ;
//			
//			InputStream in = new ByteArrayInputStream(uploadfile.getContents());
//			BufferedImage bImageFromConvert = ImageIO.read(in);
//			ImageIO.write(bImageFromConvert, "jpg",new File(newFileName));
			
			
String result = TaggingUtil.sendFile(uploadfile.getContents());
			
apiResult =   TaggingUtil.parseResponse(result);	        
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("tamamlandi", "tamamlandi"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(), e.getMessage()));
			
		}
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

	public DashboardModel getModel() {
		return model;
	}

	public void setModel(DashboardModel model) {
		this.model = model;
	}

	public ApiResult getApiResult() {
		return apiResult;
	}

	public void setApiResult(ApiResult apiResult) {
		this.apiResult = apiResult;
	}
	

	
	
	

	
}
