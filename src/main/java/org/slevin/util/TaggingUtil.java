package org.slevin.util;

import java.io.File;
import java.io.FileInputStream;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slevin.util.tagging.model.ApiResult;

public class TaggingUtil {

	static String key="d80b29ccc96546b2a333edd5641188b6";
	static String keyTagging = "b8a644dd5dc54109b8832fdef2013a8a";
	
	public static void main(String[] args) throws IOException, ParseException {
		//System.out.println("test"+sendFile("E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\4761370.jpeg"));
//        String a ="E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\5700058.jpeg";
//		File file = new File("E:\\calismalar\\tineye\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\primefaces-spring-jpa-tutorial\\resources\\demo\\images\\photocam\\4761370.jpeg");
		
		File file = new File("C:\\Users\\ETR00529\\Desktop\\erhan.PNG");
		FileInputStream fin = new FileInputStream(file);
		byte fileContent[]= new byte[(int)file.length()];
        fin.read(fileContent);
        fin.close();
        String result = sendFile(fileContent);
        ApiResult a =parseResponse(result);
        System.out.println("bitti");
	}
	
	 public static String sendFile(byte[] data) 
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories,Tags,Description,Adult&details=Celebrities");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            request.setHeader("Content-Type", "application/octet-stream");
	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


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
	
	 public static ApiResult parseResponse(String string) throws ParseException{
		 JSONParser parser = new JSONParser();
		 JSONObject obj = (JSONObject)parser.parse(string);

		 ApiResult result= new ApiResult();
		 
		 	JSONArray categories = (JSONArray)obj.get("categories");
			JSONObject jsonObject= (JSONObject)categories.get(0);
			Double score = (Double)jsonObject.get("score");
			String name = (String)jsonObject.get("name");
		 	result.getCategories().setName(name);
		 	result.getCategories().setScore(String.valueOf(score));
		 	
		 	JSONObject adult = (JSONObject)obj.get("adult");
		 	Double racyScore =(Double)adult.get("racyScore");
		 	Double adultScore =(Double)adult.get("adultScore");
		 	Boolean isAdultContent =(Boolean)adult.get("isAdultContent");
		 	Boolean isRacyContent =(Boolean)adult.get("isRacyContent");
		 	result.getAdult().setAdultScore(String.valueOf(adultScore));
		 	result.getAdult().setRacyScore(String.valueOf(racyScore));
		 	result.getAdult().setIsRacyContent(String.valueOf(isRacyContent));
		 	result.getAdult().setIsAdultContent(String.valueOf(isAdultContent));
		 	
		 	
			JSONArray tags = (JSONArray)obj.get("tags");
			for (int i = 0; i < tags.size(); i++) {
				JSONObject obja=(JSONObject)tags.get(i);
				String tagName = (String)obja.get("name");
				Double confidence= (Double)obja.get("confidence");
				result.getTag().add(tagName, String.valueOf(confidence));
			}
			
			JSONObject description = (JSONObject)obj.get("description");
			JSONArray captions = (JSONArray)description.get("captions");
			JSONObject captions1 = (JSONObject)captions.get(0);
			String captionText = (String)captions1.get("text");
			Double captionConfidence = (Double)captions1.get("confidence");
			
			JSONArray descriptionTags = (JSONArray)description.get("tags");
			for (int i = 0; i < descriptionTags.size(); i++) {
				String temp1 = (String)descriptionTags.get(i);
				result.getDescription().getTags().add(temp1);
			}
			
			result.getDescription().addCaption(captionText, String.valueOf(captionConfidence));
			
			System.out.println("bitti");
			
			return result;
	 }
	 
}
