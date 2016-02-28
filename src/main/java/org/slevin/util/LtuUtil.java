package org.slevin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LtuUtil {

	static String MODIFY_URL="https://api.ltu-engine.com/v2/ltumodify/json/";	
	static String QUERY_URL="https://api.ltu-engine.com/v2/ltuquery/json/";
	
	static String key="dvgXuncJ7QsXdWJ6hFwD7fBwkZwV6HxY";
	
	public static void main(String[] args) throws Exception {
		File file = new File("E:\\image_matching\\01.portrait.jpg");
      byte[] bFile = new byte[(int) file.length()];
      FileInputStream fileInputStream=null;
    
          //convert file into array of bytes
	    fileInputStream = new FileInputStream(file);
	    fileInputStream.read(bFile);
	    fileInputStream.close();
	    String result=listFile(bFile, "aa");
	    parseJson(result);
	}
	
	public static void insertFile(byte[] bFile,String fileName) throws Exception{
		HttpClient client = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(MODIFY_URL+"AddImage");
		String message = "This is a multipart post";
		byte[] bytes = "binary code".getBytes(); 
		// 
		
//        byte[] bFile = new byte[(int) file.length()];
//        FileInputStream fileInputStream=null;
//        try {
//            //convert file into array of bytes
//	    fileInputStream = new FileInputStream(file);
//	    fileInputStream.read(bFile);
//	    fileInputStream.close();
//	    
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
		
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("image_content", bFile, ContentType.DEFAULT_BINARY, fileName);
		builder.addTextBody("application_key", key, ContentType.TEXT_PLAIN);
		builder.addTextBody("image_id", fileName, ContentType.TEXT_PLAIN);
		// 
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		
		HttpEntity entity2 = response.getEntity();

        if (entity2 != null) 
        {
            System.out.println(EntityUtils.toString(entity2));
        }
        
        if(response.getStatusLine().getStatusCode()!=200)
        	throw new Exception("error Code="+response.getStatusLine().getStatusCode());
	}
	
	
	public static String listFile(byte[] bFile,String fileName) throws Exception{
		HttpClient client = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(QUERY_URL+"SearchImageByUpload");
		String message = "This is a multipart post";
		byte[] bytes = "binary code".getBytes(); 
		// 
		
//        byte[] bFile = new byte[(int) file.length()];
//        FileInputStream fileInputStream=null;
//        try {
//            //convert file into array of bytes
//	    fileInputStream = new FileInputStream(file);
//	    fileInputStream.read(bFile);
//	    fileInputStream.close();
//	    
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
		
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody("image_content", bFile, ContentType.DEFAULT_BINARY, fileName);
		builder.addTextBody("application_key", key, ContentType.TEXT_PLAIN);
		builder.addTextBody("nb_results", "10", ContentType.TEXT_PLAIN);
		// 
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		
		HttpEntity entity2 = response.getEntity();

        if (entity2 != null) 
        {
            String result=EntityUtils.toString(entity2);
        	System.out.println(result);
        	return result;
        }
        
        if(response.getStatusLine().getStatusCode()!=200)
        	throw new Exception("error Code="+response.getStatusLine().getStatusCode());
        
        return "";
	}
	
	public static List<LtuDto> parseJson(String result) throws FileNotFoundException, IOException, ParseException{
		List<LtuDto> resultList = new ArrayList<LtuDto>() ;
		
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(result);

		JSONObject jsonObject = (JSONObject) obj;
		JSONArray msg = (JSONArray)jsonObject.get("images");
		
	for (int i = 0; i < msg.size(); i++) {
		JSONObject aa = (JSONObject) msg.get(i);
		LtuDto dto= new LtuDto();
		dto.setId(aa.get("id").toString());
		
		String aaas=(String)aa.get("result_info");
		Object obj2 = parser.parse(aaas);
		JSONObject jsonObject2 = (JSONObject) obj2;
		
	
		dto.setScore(jsonObject2.get("distance").toString());
		resultList.add(dto);
	}
		
		System.out.println("bitti");
	return resultList;
	
	}
	
}
