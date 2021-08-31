package com.example.demo.api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
	
	public static void main(String[] args) {
		 JSONParser jsonParser = new JSONParser();
		 try (FileReader reader = new FileReader("D:\\pinkWS\\mobilehandset\\src\\main\\resources\\handsets.json"))
	        {
	            //Read JSON file
	            Object obj = jsonParser.parse(reader);
	 
	            JSONArray handsetList = (JSONArray) obj;
	            System.out.println(handsetList.toString());
	             
	            //Iterate over employee array
	        //    handsetList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
	 
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	}

}
