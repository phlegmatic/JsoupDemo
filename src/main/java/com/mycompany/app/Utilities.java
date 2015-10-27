package com.mycompany.app;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/* 
* Purpose : - general functions to parse table  , textboxes*/
public class Utilities {

	final static Logger logger = Logger.getLogger(Utilities.class);

	public String getTableData(Document doc, String searchcondition,
			String columnDelim, String rowDelim) {

		StringBuilder tableString = new StringBuilder();
		boolean firstSkipped = false; // Used to skip first 'tr' tag
		Element table = doc.select(searchcondition).first();
		if (table != null) {
			for (Element row : table.select("tr")) // Select all 'tr' tags
			{
				// Skip the first 'tr' tag since it's the header
				if (!firstSkipped) {
					firstSkipped = true;
					continue;
				}
				for (Element cells : row.select("td")) {// Select all 'td' tags
														// of
														// the'tr'
					tableString.append((cells.text()));
					tableString.append(columnDelim);
				}
				tableString.append(rowDelim);
			}

			return tableString.toString();
		} else {
			return tableString.toString();
		}
	}

	public String getTextBoxdata(Document doc, String searchcondition,
			String columnDelim) {

		StringBuilder tableString = new StringBuilder();
		Elements elems = doc.select(searchcondition);
		for (Element element : elems) {
			tableString.append(element.attr("value"));
			tableString.append(columnDelim);
		}

		return tableString.toString();
	}

	public String getPopUpData(Document doc, String searchcondition,
			String columndelimeter) {

		StringBuilder tableString = new StringBuilder();

		boolean firstSkipped = false; // Used to skip first 'tr' tag
		Elements elems = doc.select(searchcondition);
		for (Element element : elems) {
			if (!firstSkipped) {
				firstSkipped = true;
				continue;
			}
			tableString.append(element.text());
			tableString.append(columndelimeter);
		}

		return tableString.toString();
	}
}
