package com.mycompany.pojo;

import java.util.List;

public class TinDetailExplosion {
	private String tinDetails; // 20.51.17.000408
	private String currentDate; // :2015-10-12-
	private String dealerName; // TRIVENI STEEL
	private String tinNumber; // 27480508740V
	private String effectiveOrCancelledDate; // 20051230
	private String oldRCNo;
	private String locationName;
	private String actName;
	private Address tinDetailExplosionAddress;
	List<FrequncyData> freqeuncyData;

	public String getTinDetails() {
		return tinDetails;
	}
	public void setTinDetails(String tinDetails) {
		this.tinDetails = tinDetails;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public String getEffectiveOrCancelledDate() {
		return effectiveOrCancelledDate;
	}
	public void setEffectiveOrCancelledDate(String effectiveOrCancelledDate) {
		this.effectiveOrCancelledDate = effectiveOrCancelledDate;
	}
	public String getOldRCNo() {
		return oldRCNo;
	}
	public void setOldRCNo(String oldRCNo) {
		this.oldRCNo = oldRCNo;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public void setTinDetailExplosionAddress(Address tinDetailExplosionAddress) {
		this.tinDetailExplosionAddress = tinDetailExplosionAddress;
	}
	public Address getTinDetailExplosionAddress() {
		return tinDetailExplosionAddress;
	}
	public List<FrequncyData> getFreqeuncyData() {
		return freqeuncyData;
	}
	public void setFreqeuncyData(List<FrequncyData> freqeuncyData) {
		this.freqeuncyData = freqeuncyData;
	}

}
