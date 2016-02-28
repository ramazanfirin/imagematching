package org.slevin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EmotionUtil {

	static String key="d80b29ccc96546b2a333edd5641188b6";
	
	public static void main(String[] args) throws IOException {
		//System.out.println("test"+sendFile("E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\4761370.jpeg"));
        String a ="E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\5700058.jpeg";
		File file = new File("E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\4761370.jpeg");
		file = new File(a);
		FileInputStream fin = new FileInputStream(file);byte fileContent[]
         = new byte[(int)file.length()];
        fin.read(fileContent);
        fin.close();
        System.out.println(sendFile(fileContent));
	}
	
	 public static String sendFile(byte[] data) 
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/emotion/v1.0/recognize");

	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            request.setHeader("Content-Type", "application/octet-stream");
	            request.setHeader("Ocp-Apim-Subscription-Key", key);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
	            request.setEntity(reqEntity);
	           
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	System.out.println(aa);
	                return aa;
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        
	        return "";
	    }
	
}
