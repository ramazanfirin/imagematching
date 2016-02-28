package org.slevin.prime.faces.bean;
 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.primefaces.event.CaptureEvent;
import org.slevin.common.EmotionImage;
import org.slevin.dao.EmotionImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Component(value="photoCamView")
@RequestScoped
public class PhotoCamView {
	
	@Autowired
	EmotionImageDao emotionImageDao;
    
	List<EmotionImage> list = new ArrayList<EmotionImage>();
	
	EmotionImage selectedImage;
	
	String faceCount;
	
	String contempt;
	String surprise;
	String happiness;
	String neutral;
	String sadness;
	String disgust;
	String anger;
	String fear;
	
    private String filename;
    @PostConstruct
    public void init() throws Exception{
    	list = emotionImageDao.findAll();
    }
     
    public String getRectangle() throws ParseException{
    	if(selectedImage==null)
    		return "";
    	
    	String aa= selectedImage.getResult();
    	JSONParser parser = new JSONParser();
		Object obj = parser.parse(aa);

		JSONArray jsonArray = (JSONArray) obj;
		faceCount = jsonArray.size()+"";
		
		JSONObject jsonObject = (JSONObject)jsonArray.get(0);
		JSONObject jsonObjectRect = (JSONObject)jsonObject.get("faceRectangle");
		
		String top=jsonObjectRect.get("top").toString();
		String left=jsonObjectRect.get("left").toString();
		String width=jsonObjectRect.get("width").toString();
		String heigth=jsonObjectRect.get("height").toString();
		String result=heigth+","+left+","+top+","+width;
		
		JSONObject jsonObjectScores = (JSONObject)jsonObject.get("scores");
		contempt = jsonObjectScores.get("contempt").toString();
		surprise = jsonObjectScores.get("surprise").toString();
		happiness = jsonObjectScores.get("happiness").toString();
		neutral = jsonObjectScores.get("neutral").toString();
		sadness = jsonObjectScores.get("sadness").toString();
		disgust = jsonObjectScores.get("disgust").toString();
		anger = jsonObjectScores.get("anger").toString();
		fear = jsonObjectScores.get("fear").toString();

		return result;
    }
    
    
    private String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);
         
        return String.valueOf(i);
    }
 
    public String getFilename() {
        return filename;
    }
    
    public static String getURLWithContextPath() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    	}
     
    public void oncapture(CaptureEvent captureEvent) throws Exception {
    	System.out.println(getURLWithContextPath());
    	
    	filename = getRandomImageName();
        byte[] data = captureEvent.getData();
         
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String newFileName = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" +
                                    File.separator + "images" + File.separator + "photocam" + File.separator + filename + ".jpeg";
         
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(newFileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            
            EmotionImage emotionImage = new EmotionImage();
            emotionImage.setInsertDate(new Date());
            emotionImage.setName(filename);
            emotionImage.setUrl(getURLWithContextPath()+"/javax.faces.resource/demo/images/photocam/"+filename+".jpeg.xhtml");
            emotionImage.setPath(newFileName);
            emotionImageDao.persist(emotionImage);
            
            String result=emotionImageDao.sendFile(data);result.length();
            emotionImage.setResult(result);
            emotionImageDao.merge(emotionImage);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tamamandi","Tamamlandi"));
        }
        catch(IOException e) {
        	 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage(),e.getMessage()));
            
        }
        
    }
    
    public void testRectangle() throws IOException{
    File imageFile = new File("images/template.jpg");
    BufferedImage img = ImageIO.read(imageFile);

    Graphics2D graph = (Graphics2D) img.getGraphics();
    graph.setColor(Color.BLACK);
    graph.fill(new Rectangle(0, 0, 100, 200));
    graph.dispose();

    //graph.
    ImageIO.write(img, "jpg", new File("images/template.jpg"));
    }

	public List<EmotionImage> getList() {
		return list;
	}

	public void setList(List<EmotionImage> list) {
		this.list = list;
	}

	public EmotionImage getSelectedImage() {
		return selectedImage;
	}

	public void setSelectedImage(EmotionImage selectedImage) {
		this.selectedImage = selectedImage;
	}

	public String getFaceCount() {
		return faceCount;
	}

	public void setFaceCount(String faceCount) {
		this.faceCount = faceCount;
	}

	public EmotionImageDao getEmotionImageDao() {
		return emotionImageDao;
	}

	public void setEmotionImageDao(EmotionImageDao emotionImageDao) {
		this.emotionImageDao = emotionImageDao;
	}

	public String getContempt() {
		return contempt;
	}

	public void setContempt(String contempt) {
		this.contempt = contempt;
	}

	public String getSurprise() {
		return surprise;
	}

	public void setSurprise(String surprise) {
		this.surprise = surprise;
	}

	public String getHappiness() {
		return happiness;
	}

	public void setHappiness(String happiness) {
		this.happiness = happiness;
	}

	public String getNeutral() {
		return neutral;
	}

	public void setNeutral(String neutral) {
		this.neutral = neutral;
	}

	public String getSadness() {
		return sadness;
	}

	public void setSadness(String sadness) {
		this.sadness = sadness;
	}

	public String getDisgust() {
		return disgust;
	}

	public void setDisgust(String disgust) {
		this.disgust = disgust;
	}

	public String getAnger() {
		return anger;
	}

	public void setAnger(String anger) {
		this.anger = anger;
	}

	public String getFear() {
		return fear;
	}

	public void setFear(String fear) {
		this.fear = fear;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}