package org.slevin.util;

import java.io.File;
import java.io.FileInputStream;
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
	
	public static void main(String[] args) {
		System.out.println("test"+sendFile("E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\4761370.jpeg"));
	}
	
	 public static String sendFile(String path) 
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
	            
	            File file = new File(path);
	            FileInputStream fin = new FileInputStream(file);
	            byte fileContent[] = new byte[(int)file.length()];
	            fin.read(fileContent);
	            fin.close();
	            
	            ByteArrayEntity reqEntity = new ByteArrayEntity(fileContent);
	            request.setEntity(reqEntity);
	           
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) 
	            {
	                System.out.println(EntityUtils.toString(entity));
	                return EntityUtils.toString(entity);
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
