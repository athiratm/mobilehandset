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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MobileHandset;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HandsetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandsetService.class);

	
	/**
	 * To search handsets based on criteria provided
	 * 
	 * @param customQuery
	 * @return
	 */
	public List<MobileHandset> getHandsets(Map<String, String> customQuery) {

		List<MobileHandset> searchResultList = null;

		try {
			List<MobileHandset> mobileHandsetList = getHandSetList();
			List<Predicate<MobileHandset>> filterPredicate = createFilterPredicate(customQuery);
			searchResultList = mobileHandsetList.stream()
					.filter(filterPredicate.stream().reduce(x -> true, Predicate::and)).collect(Collectors.toList());
			LOGGER.info("Search result size : " + searchResultList.size());

		} catch (Exception e) {
			LOGGER.error("Issue while seaching handsets" + e.getMessage(), e);
		}

		return searchResultList;
	}

	/**
	 * To generate a list of predicates from the provided criteria
	 * 
	 * @param customQuery
	 * @return
	 */
	private List<Predicate<MobileHandset>> createFilterPredicate(Map<String, String> customQuery) {

		List<Predicate<MobileHandset>> allPredicates = new ArrayList<Predicate<MobileHandset>>();

		for (Entry entry : customQuery.entrySet()) {

			if (entry.getKey().equals("brand")) {
				Predicate<MobileHandset> brandPredicate = p -> p.getBrand()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(brandPredicate);
			}
			if (entry.getKey().equals("phone")) {
				Predicate<MobileHandset> phonePredicate = p -> p.getPhone()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(phonePredicate);
			}
			if (entry.getKey().equals("picture")) {
				Predicate<MobileHandset> picturePredicate = p -> p.getPicture()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(picturePredicate);
			}
			if (entry.getKey().equals("priceEur")) {
				Predicate<MobileHandset> priceEurPredicate = p -> p.getRelease().getPriceEur()
						.equals(Integer.valueOf(entry.getValue().toString()));
				allPredicates.add(priceEurPredicate);
			}
			if (entry.getKey().equals("announceDate")) {
				Predicate<MobileHandset> announceDatePredicate = p -> p.getRelease().getAnnounceDate()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(announceDatePredicate);
			}
			if (entry.getKey().equals("sim")) {
				Predicate<MobileHandset> simPredicate = p -> p.getSim().toLowerCase()
						.contains(entry.getValue().toString().toLowerCase());
				allPredicates.add(simPredicate);
			}
			if (entry.getKey().equals("resolution")) {
				Predicate<MobileHandset> resolutionPredicate = p -> p.getResolution()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(resolutionPredicate);
			}
			if (entry.getKey().equals("audioJack")) {
				Predicate<MobileHandset> audioJackPredicate = p -> p.getHardware().getAudioJack()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(audioJackPredicate);
			}
			if (entry.getKey().equals("gps")) {
				Predicate<MobileHandset> gpsPredicate = p -> p.getHardware().getGps()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(gpsPredicate);
			}
			if (entry.getKey().equals("battery")) {
				Predicate<MobileHandset> batteryPredicate = p -> p.getHardware().getBattery()
						.equalsIgnoreCase(entry.getValue().toString());
				allPredicates.add(batteryPredicate);
			}

		}
		return allPredicates;
	}

	
	/**
	 * To retrieve mobile handset json file as a list
	 * 
	 * @return
	 */
	private List<MobileHandset> getHandSetList() {

		JSONArray handsetsJsonArray = readJsonFile();
		return parseHandsetsJson(handsetsJsonArray);
	}

	
	/**
	 * To convert handset jsonArray to arraylist using objectmapper
	 * 
	 * @param handsetsJsonArray
	 * @return
	 */
	private List<MobileHandset> parseHandsetsJson(JSONArray handsetsJsonArray) {

		List<MobileHandset> handsetsList = null;
		String jsonStr = handsetsJsonArray.toJSONString();
		ObjectMapper mapper = new ObjectMapper();

		try {

			MobileHandset[] jsonarr = mapper.readValue(jsonStr, MobileHandset[].class);
			LOGGER.info("json array size :"+jsonarr.length);
			handsetsList = Arrays.asList(mapper.readValue(jsonStr, MobileHandset[].class));
		} catch (JsonParseException e) {
			LOGGER.error("json parsing issue :" + e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOGGER.error("json mapping issue :" + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("json IOException :" + e.getMessage(), e);
		}

		return handsetsList;
	}

	
	/**
	 * To read handsets json file as a jsonArray
	 * 
	 * @return
	 */
	private JSONArray readJsonFile() {

		JSONParser jsonParser = new JSONParser();
		InputStream is = getClass().getResourceAsStream("/handsets.json");
		try (Reader reader = new InputStreamReader(is);) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray handsetJsonArray = (JSONArray) obj;
			return handsetJsonArray;

		} catch (FileNotFoundException e) {
			LOGGER.error("json file not found :" + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("json file IOException :" + e.getMessage(), e);
		} catch (ParseException e) {
			LOGGER.error("json file parse issue :" + e.getMessage(), e);
		}
		return null;
	}
}
