package com.example.demo.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MobileHandset;
import com.example.demo.service.HandsetService;

@RestController
@RequestMapping({ "/mobile" })
public class HandsetController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandsetController.class);
	
	@Autowired
	HandsetService handsetService;

	
	/**
	 * To search mobile handsets based on specified criteria
	 * 
	 * @param customQuery
	 * @return
	 */
	@GetMapping({ "/search" })
	public List<MobileHandset> searchHandSets(@RequestParam Map<String, String> customQuery) {

		LOGGER.info("Search criteria :"+customQuery);
		return handsetService.getHandsets(customQuery);
		
	}
}
