package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.mycompany.pojo.SearchCriteria;
import com.mycompany.pojo.TinDetail;
import com.mycompany.pojo.TinDetailExplosion;

public class Datagetters {

	static SiteConnections siteConnections = new SiteConnections();
	static ObjectMappers objectMappers = new ObjectMappers();
	static Selectors selectors = new Selectors();
	final static Logger logger = Logger.getLogger(Datagetters.class);
	public List<TinDetail> getTinDetails(SearchCriteria searchcriteria)
			throws IOException {
		Document doc = siteConnections
				.getInitalCookies(ScrapingConstants.BaseURL);
		// logger.info("base doc is" + doc);
		doc = siteConnections.getTinDetaildoc(ScrapingConstants.SearchURL,
				searchcriteria);
		String requiredTinDetailsData = selectors.parseBaseDocument(doc);
		List<TinDetail> tinDetails = new ArrayList<TinDetail>();
		if (requiredTinDetailsData.length() != 0) {
			tinDetails = objectMappers
					.populateTinDetails(requiredTinDetailsData);
		}
		return tinDetails;
	}

	public List<TinDetail> getTinExplosionData(List<TinDetail> tinDetails,
			SearchCriteria searchcriteria) throws IOException {

		for (TinDetail tindetail : tinDetails) {
			// if (!tindetail.getStatus().contains(ScrapingConstants.Cancelled))
			// {
			Document doc = siteConnections
					.getInitalCookies(ScrapingConstants.BaseURL);
			// // logger.info("base doc is" + doc);
			doc = siteConnections.getTinDetaildoc(ScrapingConstants.SearchURL,
					searchcriteria); // sending
			// dummy
			// search
			// criteria

			doc = siteConnections
					.getTinDetailPopUpdoc(ScrapingConstants.explosionURL
							+ tindetail.getTinNumber());
			// Document doc=Jsoup.connect(Href).get();
			String requiredTinExplosionDetailsData = selectors
					.parsePopUPDocument(doc);
			// logger.info("explosion data " + doc);
			TinDetailExplosion tinExplosionDetails = objectMappers
					.populateExplsionTinDetails(requiredTinExplosionDetailsData);
			tindetail.setTinDetailExplosion(tinExplosionDetails);
			// } else {
			// continue;
			// }
		}

		return tinDetails;
	}

}
