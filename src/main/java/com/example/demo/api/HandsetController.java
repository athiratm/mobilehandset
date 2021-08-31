package com.example.demo.api;

import java.util.List;
import java.util.Map;

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
	
	@Autowired
	HandsetService handsetService;

	@GetMapping({ "/search" })
	public List<MobileHandset> searchHandSets(@RequestParam Map<String, String> customQuery) {

		System.out.println("here");
		System.out.println(customQuery);
		return handsetService.getHandsets(customQuery);
		
	}
}
