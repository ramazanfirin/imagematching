package org.slevin.dao.service;


import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slevin.common.EmotionImage;
import org.slevin.dao.EmotionImageDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class EmotionImageService extends EntityService<EmotionImage> implements EmotionImageDao {

	String key="d80b29ccc96546b2a333edd5641188b6";
	
	

	public String sendFile(byte[] data) {
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
            
//            File file = new File(path);
//            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//             = new byte[(int)file.length()];
//            fin.read(fileContent);
//            fin.close();
            
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

