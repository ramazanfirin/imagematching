package org.slevin.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slevin.util.tagging.model.PairKey;

public class AyonixUtil {

	static String key="d80b29ccc96546b2a333edd5641188b6";
	static String keyTagging = "b8a644dd5dc54109b8832fdef2013a8a";
	
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\ETR00529\\Desktop\\erhan.PNG");
		FileInputStream fin = new FileInputStream(file);
		byte fileContent[]= new byte[(int)file.length()];
        fin.read(fileContent);
        fin.close();
        //String result = enroll("erhan","yucel",fileContent);
       // ApiResult a =parseResponse(result);
        
       // String result=searchPerson(fileContent);
        //String result = deletePerson("10019");
        //System.out.println(result);
	
        String result = compare(fileContent,fileContent);
        System.out.println(result);
	}
	
	 public static String enroll(String name,String surname ,byte[] data) throws Exception
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://webapi.ayonix.com:8090/ayonixwebapi");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody fileBody = new ByteArrayBody(data,"aa");
	            
	            
	            StringBody function = new StringBody("enrollperson",ContentType.TEXT_PLAIN);
	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
	            StringBody surnameBody = new StringBody(surname,ContentType.TEXT_PLAIN);

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("files", fileBody)
	                    .addPart("function", function)
	                    .addPart("appid", appid)
	                    .addPart("format", format)
	                    .addPart("action", action)
	                    .addPart("name", nameBody)
	                    .addPart("surname", surnameBody)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	JSONParser parser = new JSONParser();
	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
                    String result = (String)obj.get("result");	
                    if(result.equals("OK")){
                    	String userId = (String)obj.get("userid");
                    	return userId;
                    }else
                    	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
	    }
	

	 public static List<PairKey>  searchPerson(byte[] data) throws Exception 
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://webapi.ayonix.com:8090/ayonixwebapi");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody fileBody = new ByteArrayBody(data,"aa");
	            
	            
	            StringBody function = new StringBody("identifyperson",ContentType.TEXT_PLAIN);
	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
//	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
//	            StringBody surnameBody = new StringBody(surname,ContentType.TEXT_PLAIN);

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("files", fileBody)
	                    .addPart("function", function)
	                    .addPart("appid", appid)
	                    .addPart("format", format)
	                    .addPart("action", action)
//	                    .addPart("name", nameBody)
//	                    .addPart("surname", surnameBody)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);

	            	JSONParser parser = new JSONParser();
	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
                    String result = (String)obj.get("result");	
                    if(result.equals("OK")){
                    	JSONArray array = (JSONArray)obj.get("matches");
                    	List<PairKey> resultList = new ArrayList<PairKey>();
                    	for (int i = 0; i < array.size(); i++) {
                    		JSONObject object = (JSONObject)array.get(i);
                    		long id = (Long)object.get("id");
                    		String score = (String)object.get("score");
							
                    		PairKey pairkey  = new PairKey(String.valueOf(id), String.valueOf(score));
                    		resultList.add(pairkey);
						}
                    	
                    	return resultList;
                    }else
                    	throw new Exception();	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return null;
	    }
	 
	 public static String deletePerson(String userId) 
	    {
	        HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	            URIBuilder builder = new URIBuilder("http://webapi.ayonix.com:8090/ayonixwebapi");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            //ByteArrayBody fileBody = new ByteArrayBody(data,"aa");
	            
	          
	            StringBody function = new StringBody("deleteperson",ContentType.TEXT_PLAIN);
	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
	            StringBody userIdBody = new StringBody(userId,ContentType.TEXT_PLAIN);
//	            StringBody surnameBody = new StringBody(surname,ContentType.TEXT_PLAIN);

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    //.addPart("files", fileBody)
	                    .addPart("function", function)
	                    .addPart("appid", appid)
	                    .addPart("format", format)
	                    .addPart("action", action)
	                    .addPart("userid", userIdBody)
//	                    .addPart("surname", surnameBody)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);
	            	JSONParser parser = new JSONParser();
	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
                    String result = (String)obj.get("result");	
                    if(result.equals("OK")){
                    	String userIdx = (String)obj.get("userid");
                    	return userIdx;
                    }else
                    	throw new Exception();
	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            return e.getMessage();
	        }
	        
	        return "";
	    }
	 
	 
	 
	 public static String compare(byte[] file1,byte[] file2) throws Exception 
	    {
		 HttpClient httpclient = HttpClients.createDefault();

	        try
	        {
	        	
	        	System.out.println(new Date()+ " compare methodu cağrildi");
	            URIBuilder builder = new URIBuilder("http://webapi.ayonix.com:8090/ayonixwebapi");
	            									// https://api.projectoxford.ai/vision/v1.0/analyze?visualFeatures=Categories

	            //builder.setParameter("visualFeatures", "All");
	            //builder.setParameter("faceRectangles", "{string}");

	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
	            RequestConfig config = RequestConfig.custom()
	            	    .setSocketTimeout(10 * 1000)
	            	    .setConnectTimeout(10 * 1000)
	            	    .build();
	           
	            HttpClients.custom()
	            .setDefaultRequestConfig(config);
	            
//	            request.setHeader("Content-Type", "application/octet-stream");
//	            request.setHeader("Ocp-Apim-Subscription-Key", keyTagging);


	            // Request body
	            
	            
	           // StringEntity reqEntity = new StringEntity("{body}");
	            
//	            File file = new File(path);
//	            FileInputStream fin = new FileInputStream(file);byte fileContent[]
//	             = new byte[(int)file.length()];
//	            fin.read(fileContent);
//	            fin.close();
	            
	            ByteArrayBody file1Body = new ByteArrayBody(file1,"aa");
	            ByteArrayBody file2Body = new ByteArrayBody(file2,"aa");
	            
	            StringBody function = new StringBody("verifymatch",ContentType.TEXT_PLAIN);
	            StringBody appid = new StringBody("3926BEDE-5E64-0F45-89B7-76504845DDC9",ContentType.TEXT_PLAIN);
	            StringBody format = new StringBody("json",ContentType.TEXT_PLAIN);
	            StringBody action = new StringBody("call",ContentType.TEXT_PLAIN);
//	            StringBody nameBody = new StringBody(name,ContentType.TEXT_PLAIN);
//	            StringBody surnameBody = new StringBody(surname,ContentType.TEXT_PLAIN);

	            HttpEntity reqEntity = MultipartEntityBuilder.create()
	                    .addPart("files", file1Body)
	                    .addPart("files", file2Body)
	                    .addPart("function", function)
	                    .addPart("appid", appid)
	                    .addPart("format", format)
	                    .addPart("action", action)
//	                    .addPart("name", nameBody)
//	                    .addPart("surname", surnameBody)
	                    .build();

	            
	            
	            
//	            ByteArrayEntity reqEntity = new ByteArrayEntity(data);
//	            request.setEntity(reqEntity);
//	           
	            request.setEntity(reqEntity);
	            
	            
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) 
	            {
	            	String aa=EntityUtils.toString(entity);

	            	JSONParser parser = new JSONParser();
	       		 	JSONObject obj = (JSONObject)parser.parse(aa);
                 String result = (String)obj.get("result");	
                 if(result.equals("OK")){
                	 String  array = (String)obj.get("score");
                	 System.out.println(new Date()+ " compare methodu sonlandi");
                 	return array;
                 }else
                 	throw new Exception();	            }
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.getMessage());
	            throw e;
	        }
	        
	        return "";
		 
	    }
	 
}
