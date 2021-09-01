package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.api.HandsetController;
import com.example.demo.entity.MobileHandset;
import com.example.demo.entity.Release;
import com.example.demo.service.HandsetService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class HandsetControllerTest {

	
	@InjectMocks
	HandsetController handsetController;
	
	@Mock
	HandsetService handsetService;
	
	Map<String, String> customQuery = new HashMap<String, String>();
	List<MobileHandset> searchResult =  new ArrayList<MobileHandset>();
	
	
	@BeforeEach
	void setMockOutput() {
		
		customQuery.put("priceEur", "200");
		customQuery.put("announceDate", "1999");
		
		MobileHandset handset1 = new MobileHandset();
		handset1.setId(28354);
		handset1.setBrand("Ericsson");
		handset1.setPhone("Ericsson A1018s");
		Release release1 = new Release();
		release1.setPriceEur(200);
		release1.setAnnounceDate("1999");
		handset1.setRelease(release1);
		
		
		MobileHandset handset2 = new MobileHandset();
		handset2.setId(26894);
		handset2.setBrand("Ericsson");
		handset2.setPhone("Ericsson I 888");
		Release release2 = new Release();
		release2.setPriceEur(200);
		release2.setAnnounceDate("1999");
		handset2.setRelease(release2);
		
		searchResult.add(handset1);
		searchResult.add(handset2);
		
		when(handsetService.getHandsets(customQuery)).thenReturn(searchResult);
		
	}
	
	@Test
	public void testSearchHandSets() {
		
		List<MobileHandset> resultList = handsetService.getHandsets(customQuery);
		assertThat(resultList.size()).isEqualTo(2);
		
	}
}
