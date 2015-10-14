package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mycompany.pojo.Address;
import com.mycompany.pojo.FrequncyData;
import com.mycompany.pojo.SearchCriteria;
import com.mycompany.pojo.TinDetail;
import com.mycompany.pojo.TinDetailExplosion;

//Task: Verify the status and validity 
//of Sales Tax Number (TIN) and obtain allied 
//information if valid
public class App {
	static App core = new App();
	static Utilities utility = new Utilities();
	static Validator validator = new Validator();

	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws IOException {
		App core = new App();
		/* build searchParameters */
		SearchCriteria searchcriteria = core.createInput("", "", "TEST");
		// if (validator.validate(searchcriteria)) {
		/* fetching session coockies */
		Response res = utility.getSessionCookie(ScrapingConstants.BaseURL);
		logger.info("cookies are" + res.cookies());

		/* static file */
		File in = new File(
				"G:\\data science\\Core Java practice\\my-app\\files\\base.html");
		Document doc = Jsoup.parse(in, null);

		// Document doc = core.makeConnection(searchcriteria, res,
		// ScrapingConstants.BaseURL);
		// logger.info("base doc is" + doc);

		String requiredTinDetailsData = core.parseBaseDocument(doc, res);

		List<TinDetail> tinDetails = populateTinDetails(requiredTinDetailsData);

		core.printOutPut(tinDetails);
		// }

	}

	private void printOutPut(List<TinDetail> tinDetails) {
		for (TinDetail tinDetail : tinDetails) {
			System.out.print(tinDetail.getTinNumber());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getDealerName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getStatus());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getEffectiveOrCancelledDate());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getAddressLineOne());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getStreetName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getAddressLineTwo());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getAddressLineThree());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getTalukaName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getDistrictName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getCityName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getStateName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getTinDetailExplosionAddress().getPinCode());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion().getOldRCNo());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(tinDetail.getTinDetailExplosion()
					.getLocationName());
			System.out.print(tinDetail.getTinDetailExplosion().getActName());
			System.out.print(ScrapingConstants.columnDelimeter);
			System.out.print(ScrapingConstants.RowDelimeter);

		}

	}
	private static List<TinDetail> populateTinDetails(
			String requiredTinDetailsData) throws IOException {
		// TODO Auto-generated method stub
		List<TinDetail> tinDetaillist = new ArrayList<TinDetail>();
		String[] row = requiredTinDetailsData.split("\\,\\$");
		int rowindex = 0;
		for (String rowText : row) {
			int index = 0;
			String[] column = rowText.split(ScrapingConstants.columnDelimeter);
			TinDetail tinDetail = new TinDetail();
			tinDetail.setSearialNO(column[index++]);
			tinDetail.setOldRcNo(column[index++]);
			tinDetail.setTinNumber(column[index++]);
			tinDetail.setDealerName(column[index++]);
			tinDetail.setStatus(column[index++]);

			/* static file */
			File in = new File(
					"G:\\data science\\Core Java practice\\my-app\\files\\popup"
							+ rowindex++ + ".html");
			Document doc = Jsoup.parse(in, null);

			Response res = utility.getSessionCookie(ScrapingConstants.BaseURL);
			//

			// Document doc = core.makeConnection(new SearchCriteria(), res,
			// ScrapingConstants.explosionURL + "?tinnumber="
			// + "27205264222P");
			// Document doc=Jsoup.connect(Href).get();
			String requiredTinExplosionDetailsData = core.parsePopUPDocument(
					doc, res);
			logger.info("base doc is are" + doc);
			TinDetailExplosion tinDetails = populateExplsionTinDetails(requiredTinExplosionDetailsData);
			tinDetail.setTinDetailExplosion(tinDetails);
			tinDetaillist.add(tinDetail);
		}

		return tinDetaillist;
	}
	private static TinDetailExplosion populateExplsionTinDetails(
			String requiredTinExplosionDetailsData) {

		TinDetailExplosion tinDetailExplosion = new TinDetailExplosion();
		String[] tinPopUpdata = requiredTinExplosionDetailsData.split("%%%");
		String[] row = tinPopUpdata[0].split("@");
		int rowindex = 1;

		tinDetailExplosion.setDealerName(row[1]);
		tinDetailExplosion.setTinNumber(row[3]);
		tinDetailExplosion.setEffectiveOrCancelledDate(row[5]);

		Address address = new Address();
		address.setAddressLineOne(row[7]);
		address.setStreetName(row[9]);
		address.setAddressLineTwo(row[11]);
		address.setAddressLineThree(row[13]);
		address.setTalukaName(row[15]);
		address.setDistrictName(row[17]);
		address.setCityName(row[19]);
		address.setStateName(row[21]);
		address.setPinCode(row[23]);
		tinDetailExplosion.setTinDetailExplosionAddress(address);

		tinDetailExplosion.setOldRCNo(row[25]);
		tinDetailExplosion.setLocationName(row[27]);
		tinDetailExplosion.setActName(row[29]);

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
		return tinDetailExplosion;
	}
	private String parsePopUPDocument(Document doc, Response res) {
		String popUpData = utility.getTableData(doc, "table[width=65%]",
				ScrapingConstants.explosioncolumnDelimeter,
				ScrapingConstants.tempRowDelimeter);

		logger.info("Received data " + popUpData);

		String popUpFreqData = utility.getTextBoxdata(doc, "input.subhead-1",
				ScrapingConstants.columnDelimeter);

		logger.info("Received data " + popUpData);

		return popUpData + "%%%" + popUpFreqData;

	}
	private String parseBaseDocument(Document doc, Response res)
			throws IOException {

		String tableData = utility.getTableData(doc, "table[width=788]",
				ScrapingConstants.columnDelimeter,
				ScrapingConstants.tempRowDelimeter);

		logger.info("Received data " + tableData);

		return tableData;

		// Document explosionDoc = core.makeConnection(
		// core.createInput(tindetail.getTinNumber(), "", ""), res,
		// ScrapingConstants.explosionURL);
		// TinDetailExplosion TinDetailsPopup = core
		// .parsePopUpDocument(explosionDoc);
		// tindetail.setTinDetailExplosion(TinDetailsPopup);

	}

	// private Document makeConnection(SearchCriteria searchcriteria,
	// Response res, String URL) throws IOException {
	// /* get session id* */
	//
	// Document doc = null;
	//
	// /* connect base page */
	// if (URL.equalsIgnoreCase(ScrapingConstants.BaseURL)) {
	// doc = Jsoup
	// .connect(URL)
	// .
	// // .header("Accept-Encoding", "gzip, deflate")
	// userAgent("Mozilla")
	// .cookie("JSESSIONID",
	// "6A602FC9463AD80D34BEF2D545E54CA5.webapps17")
	// .header("Transfer-Encoding", "chunked")
	// .data("tin", searchcriteria.getTinNumber())
	// .data("pan", searchcriteria.getPanNumber())
	// .data("rc_no", "").data("fptecno", "").data("bptecno", "")
	// .data("DEALERNAME", searchcriteria.getDealerName())
	// .cookie("Path", "/Tin_Search").data("Submit", "SEARCH")
	// .post();
	//
	// } else {
	// doc = Jsoup.connect(URL)
	// .header("Accept-Encoding", "gzip, deflate, sdch")
	// .header("host", "mahavat.gov.in").userAgent("Mozilla")
	//
	// .referrer(ScrapingConstants.referrer).get();
	// }
	// return doc;
	// }

	private SearchCriteria createInput(String inputTin, String inputPan,
			String inputDealerName) {
		// TODO Auto-generated method stub
		SearchCriteria searchcriteria = new SearchCriteria();
		searchcriteria.setTinNumber(inputTin);
		searchcriteria.setPanNumber(inputPan);
		searchcriteria.setDealerName(inputDealerName);
		return searchcriteria;

	}
}
