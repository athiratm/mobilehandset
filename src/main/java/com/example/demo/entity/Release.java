package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Release {
	
	private String announceDate;
	private Integer priceEur;
	public String getAnnounceDate() {
		return announceDate;
	}
	public void setAnnounceDate(String announceDate) {
		this.announceDate = announceDate;
	}
	public Integer getPriceEur() {
		return priceEur;
	}
	public void setPriceEur(Integer priceEur) {
		this.priceEur = priceEur;
	}
	
	

}
