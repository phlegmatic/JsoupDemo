package com.mycompany.app;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
/*  
 * Purpose :Selects DOM element whih have required data*/
public class Selectors {

	static Utilities utility = new Utilities();
	final static Logger logger = Logger.getLogger(Selectors.class);
	public String parseBaseDocument(Document doc) throws IOException {

		String tableData = utility.getTableData(doc, "table[width=788]",
				ScrapingConstants.columnDelimeter,
				ScrapingConstants.tempRowDelimeter);

		// logger.info("Received data " + tableData);

		return tableData;

	}

	public String parsePopUPDocument(Document doc) {

		String popUpData = utility.getPopUpData(doc, "[colspan]",
				ScrapingConstants.explsioncolumnDelimeter);

		// logger.info("Received data " + popUpData);

		String popUpFreqData = utility.getTextBoxdata(doc, "input.subhead-1",
				ScrapingConstants.columnDelimeter);

		// logger.info("Received data " + popUpFreqData);

		return popUpData + "%%%" + popUpFreqData;

	}

}
