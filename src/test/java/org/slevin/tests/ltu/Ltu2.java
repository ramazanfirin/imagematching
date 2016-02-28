package org.slevin.tests.ltu;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Ltu2 {
	static String MODIFY_URL="https://api.ltu-engine.com/v2/ltumodify/json/";	
	static String QUERY_URL="https://api.ltu-engine.com/v2/ltuquery/json/";
	
	static String key="dvgXuncJ7QsXdWJ6hFwD7fBwkZwV6HxY";
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		File file = new File("E:\\ramazan\\ramazan\\resim\\gurkan\\DSCI0001.JPG");
		sendFile(file);
	}
	
	public static void sendFile(File file) throws ClientProtocolException, IOException{
		HttpClient client = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(MODIFY_URL+"AddImage");
		String message = "This is a multipart post";
		byte[] bytes = "binary code".getBytes(); 
		// 
		
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
		
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("image_content", bFile, ContentType.DEFAULT_BINARY, file.getName());
		builder.addTextBody("application_key", key, ContentType.TEXT_PLAIN);
		builder.addTextBody("image_id", file.getName(), ContentType.TEXT_PLAIN);
		// 
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		
		HttpEntity entity2 = response.getEntity();

        if (entity2 != null) 
        {
            System.out.println(EntityUtils.toString(entity2));
        }
	}
}
