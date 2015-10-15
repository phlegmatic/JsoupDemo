package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.pojo.Address;
import com.mycompany.pojo.FrequncyData;
import com.mycompany.pojo.TinDetail;
import com.mycompany.pojo.TinDetailExplosion;

public class ObjectMappers {

	public List<TinDetail> populateTinDetails(String requiredTinDetailsData)
			throws IOException {
		// TODO Auto-generated method stub
		List<TinDetail> tinDetaillist = new ArrayList<TinDetail>();
		String[] row = requiredTinDetailsData.split("\\,\\$");

		for (String rowText : row) {
			int index = 0;
			String[] column = rowText.split(ScrapingConstants.columnDelimeter);
			TinDetail tinDetail = new TinDetail();
			tinDetail.setSearialNO(column[index++]);
			tinDetail.setOldRcNo(column[index++]);
			tinDetail.setTinNumber(column[index++]);
			tinDetail.setDealerName(column[index++]);
			tinDetail.setStatus(column[index++]);
			tinDetaillist.add(tinDetail);
		}

		return tinDetaillist;
	}
	public TinDetailExplosion populateExplsionTinDetails(
			String requiredTinExplosionDetailsData) {

		TinDetailExplosion tinDetailExplosion = new TinDetailExplosion();
		String[] tinPopUpdata = requiredTinExplosionDetailsData.split("%%%");
		String[] row = tinPopUpdata[0].split("\\@");
		int rowindex = 0;

		tinDetailExplosion.setDealerName(row[rowindex++]);
		tinDetailExplosion.setTinNumber(row[rowindex++]);
		tinDetailExplosion.setEffectiveOrCancelledDate(row[rowindex++]);

		Address address = new Address();
		address.setAddressLineOne(row[rowindex++]);
		address.setStreetName(row[rowindex++]);
		address.setAddressLineTwo(row[rowindex++]);
		address.setAddressLineThree(row[rowindex++]);
		address.setTalukaName(row[rowindex++]);
		address.setDistrictName(row[rowindex++]);
		address.setCityName(row[rowindex++]);
		address.setStateName(row[rowindex++]);
		address.setPinCode(row[rowindex++]);
		tinDetailExplosion.setTinDetailExplosionAddress(address);

		tinDetailExplosion.setOldRCNo(row[rowindex++]);
		tinDetailExplosion.setLocationName(row[rowindex++]);
		tinDetailExplosion.setActName(row[rowindex++]);

		if (tinPopUpdata.length > 1) { // if frq data not present
			List<FrequncyData> fdataList = new ArrayList<FrequncyData>();
			String[] freqRow = tinPopUpdata[1].split("\\,");
			int findex = 0;
			while (findex < freqRow.length) {

				FrequncyData fdata = new FrequncyData();
				fdata.setFinYear(freqRow[findex++]);
				fdata.setFrequencyName(freqRow[findex++]);
				fdataList.add(fdata);
			}
			tinDetailExplosion.setFreqeuncyData(fdataList);
		}
		return tinDetailExplosion;
	}

}
