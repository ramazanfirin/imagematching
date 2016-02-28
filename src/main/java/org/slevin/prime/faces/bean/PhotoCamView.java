package org.slevin.prime.faces.bean;
 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.CaptureEvent;
import org.slevin.common.EmotionImage;
import org.slevin.dao.EmotionImageDao;
import org.slevin.util.EmotionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
 
@Component(value="photoCamView")
@ViewScoped
public class PhotoCamView {
	
	@Autowired
	EmotionImageDao emotionImageDao;
     
    private String filename;
     
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
            
            String result=EmotionUtil.sendFile(emotionImage.getPath());
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
}