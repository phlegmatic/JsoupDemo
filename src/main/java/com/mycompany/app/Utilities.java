package com.mycompany.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Utilities {

	final static Logger logger = Logger.getLogger(Utilities.class);
	private final Map<String, String> cookies = new HashMap<String, String>();
	/* Making connection with searchParameter as input */

	public Response getSessionCookie(String url) throws IOException {
		return Jsoup

		.connect(url).header("Origin", "http://mahavat.gov.in")
				.method(Method.GET).timeout(10000).execute();
	}

	public Document get(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response = connection.execute();
		cookies.putAll(response.cookies());
		return response.parse();
	}

	public String getTableData(Document doc, String searchcondition,
			String columnDelim, String rowDelim) {

		StringBuilder tableString = new StringBuilder();
		boolean firstSkipped = false; // Used to skip first 'tr' tag
		Element table = doc.select(searchcondition).first();
		for (Element row : table.select("tr")) // Select all 'tr' tags
		{
			// Skip the first 'tr' tag since it's the header
			if (!firstSkipped) {
				firstSkipped = true;
				continue;
			}
			for (Element cells : row.select("td")) {// Select all 'td' tags of
													// the'tr'
				tableString.append((cells.text()));
				tableString.append(columnDelim);
			}
			tableString.append(rowDelim);
		}

		return tableString.toString();
	}

	public String getTextBoxdata(Document doc, String searchcondition,
			String columnDelim) {

		StringBuilder tableString = new StringBuilder();
		boolean firstSkipped = false; // Used to skip first 'tr' tag
		Elements elems = doc.select(searchcondition);
		for (Element element : elems) {
			tableString.append(element.attr("value"));
			tableString.append(columnDelim);
		}

		return tableString.toString();
	}
}
