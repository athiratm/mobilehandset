package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MobileHandset;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HandsetService {

	public List<MobileHandset> getHandsets(Map<String, String> customQuery) {

		List<MobileHandset> mobileHandsetList = getHandSetList();
		List<MobileHandset> searchResultList = null;
		List<Predicate<MobileHandset>> filterPredicate = createFilterPredicate(customQuery);
		searchResultList = mobileHandsetList.stream().filter(filterPredicate.stream().reduce(x->true, Predicate::and)).collect(Collectors.toList());
		System.out.println("result size : "+searchResultList.size());
		
		return searchResultList;
	}

	private List<Predicate<MobileHandset>> createFilterPredicate(Map<String, String> customQuery) {
		
		 List<Predicate<MobileHandset>> allPredicates = new ArrayList<Predicate<MobileHandset>>();
		
		for (Entry entry : customQuery.entrySet()) {
			
			if(entry.getKey().equals("brand")) {
				Predicate<MobileHandset> brandPredicate = p->p.getBrand().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(brandPredicate);
			}
			if(entry.getKey().equals("phone")) {
				Predicate<MobileHandset> phonePredicate = p->p.getPhone().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(phonePredicate);
			}
			if(entry.getKey().equals("picture")) {
				Predicate<MobileHandset> picturePredicate = p->p.getPicture().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(picturePredicate);
			}
			if(entry.getKey().equals("priceEur")) {
				Predicate<MobileHandset> priceEurPredicate = p->p.getRelease().getPriceEur().equals(Integer.valueOf(entry.getValue().toString()));
				allPredicates.add(priceEurPredicate);
			}
			if(entry.getKey().equals("announceDate")) {
				Predicate<MobileHandset> announceDatePredicate = p->p.getRelease().getAnnounceDate().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(announceDatePredicate);
			}
			if(entry.getKey().equals("sim")) {
				Predicate<MobileHandset> simPredicate = p->p.getSim().toLowerCase().contains(entry.getValue().toString().toLowerCase());
				allPredicates.add(simPredicate);
			}
			if(entry.getKey().equals("resolution")) {
				Predicate<MobileHandset> resolutionPredicate = p->p.getResolution().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(resolutionPredicate);
			}
			if(entry.getKey().equals("audioJack")) {
				Predicate<MobileHandset> audioJackPredicate = p->p.getHardware().getAudioJack().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(audioJackPredicate);
			}
			if(entry.getKey().equals("gps")) {
				Predicate<MobileHandset> gpsPredicate = p->p.getHardware().getGps().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(gpsPredicate);
			}
			if(entry.getKey().equals("battery")) {
				Predicate<MobileHandset> batteryPredicate = p->p.getHardware().getBattery().equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(batteryPredicate);
			}
			
		}
		return allPredicates;
	}
	private List<MobileHandset> getHandSetList() {

		JSONArray handsetsJsonArray = readJsonFile();
		return parseHandsetsJson(handsetsJsonArray);
	}

	private List<MobileHandset> parseHandsetsJson(JSONArray handsetsJsonArray) {

		List<MobileHandset> handsetsList = null;
		String jsonStr = handsetsJsonArray.toJSONString();
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			MobileHandset[] jsonarr = mapper.readValue(jsonStr, MobileHandset[].class);
			System.out.println(jsonarr.length);
			handsetsList = Arrays.asList(mapper.readValue(jsonStr, MobileHandset[].class));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return handsetsList;
	}

	private JSONArray readJsonFile() {

		JSONParser jsonParser = new JSONParser();
		InputStream is = getClass().getResourceAsStream("/handsets.json");
		try (Reader reader = new InputStreamReader(is);) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray handsetList = (JSONArray) obj;
			System.out.println("json array : "+handsetList);
			return handsetList;
			// Iterate over employee array
			// handsetList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
