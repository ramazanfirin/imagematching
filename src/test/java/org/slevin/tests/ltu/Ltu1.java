package org.slevin.tests.ltu;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Ltu1 {

	static String MODIFY_URL="https://api.ltu-engine.com/v2/ltumodify/json/";	
	static String QUERY_URL="https://api.ltu-engine.com/v2/ltuquery/json/";
	
	static String key="dvgXuncJ7QsXdWJ6hFwD7fBwkZwV6HxY";
					   //dvgXuncJ7QsXdWJ6hFwD7fBwkZwV6HxY
	
	//https://ondemand.ltu-engine.com/api/modify/AddImage/dvgXuncJ7QsXdWJ6hFwD7fBwkZwV6HxY;
	
	public static void main(String[] args) {
		File file = new File("E:\\ramazan\\ramazan\\resim\\gurkan\\DSCI0001.JPG");
		addImage(file);
		System.out.println();
	}
	
	public static void addImage(File file){
		
		HttpClient httpclient = HttpClients.createDefault();

        try
        {
        	String url=MODIFY_URL+"AddImage";
        	//url ="http://localhost:8091/v2/ltumodify/json/AddImage";
        	//url ="https://ondemand.ltu-engine.com/api/modify/AddImage/dvgXuncJ7QsXdWJ6hFwD7fBwkZwV6HxY";
            URIBuilder builder = new URIBuilder(url);
            System.out.println(MODIFY_URL+"AddImage");
           // builder.setParameter("faceRectangles", "{string}");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "multipart/form-data");
//        request.setHeader("Content-Type", "application/octet-stream");
     //       request.setHeader("Ocp-Apim-Subscription-Key", "{subscription key}");

            byte[] bFile = new byte[(int) file.length()];
            FileInputStream fileInputStream=null;
            try {
                //convert file into array of bytes
    	    fileInputStream = new FileInputStream(file);
    	    fileInputStream.read(bFile);
    	    fileInputStream.close();
    	    
            }catch(Exception e){
            	e.printStackTrace();
            }
            
            String data  =new String(bFile);
           // data = "a";
            // Request body
            String a = "{";
            a=a+"\"application_key\":\""+key+"\"";
            a=a+",";
            a=a+"\"image_id\":\""+file.getName()+"\"";
            a=a+",";
            a=a+"\"image_content\":\""+data+"\"";
            a=a+"}";
            System.out.println(a);
            StringEntity reqEntity = new StringEntity(a);
           
            
            
            MultipartEntityBuilder builder2 = MultipartEntityBuilder.create();
            
            builder2.addTextBody("application_key", key, ContentType.TEXT_PLAIN);
            builder2.addTextBody("image_id", file.getName(), ContentType.TEXT_PLAIN);
            builder2.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, "file.ext");
            HttpEntity multipart = builder2.build();
            request.setEntity(multipart);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
		
	
}
