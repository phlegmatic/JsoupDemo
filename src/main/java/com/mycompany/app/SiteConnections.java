package com.mycompany.app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mycompany.pojo.SearchCriteria;

public class SiteConnections {
	private final Map<String, String> cookies = new HashMap<String, String>();

	public Document getInitalCookies(String url) throws IOException {
		Connection connection = Jsoup.connect(url).timeout(10 * 1000);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		try {
			Response response = connection.execute();
			cookies.putAll(response.cookies());
			return response.parse();
		} catch (IOException e) {
			System.out.println("Connection Problem!");

		}
		return null;
	}

	public Document getTinDetaildoc(String url, SearchCriteria searchcriteria)
			throws SocketTimeoutException, IOException {
		Connection connection = Jsoup.connect(url).timeout(10 * 1000);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response;
		try {
			response = connection.data("tin", searchcriteria.getTinNumber())
					.data("pan", searchcriteria.getPanNumber())
					.data("rc_no", "").data("fptecno", "").data("bptecno", "")
					.data("DEALERNAME", searchcriteria.getDealerName())
					.data("Submit", "SEARCH").userAgent("Mozilla").execute();
			cookies.putAll(response.cookies());
			return response.parse();
		}

		catch (IOException e) {
			System.out.println("Connection Problem");

		}
		return null;

	}

	public Document getTinDetailPopUpdoc(String url) throws IOException {
		Connection connection = Jsoup.connect(url).timeout(10 * 1000);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response = connection
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.referrer(ScrapingConstants.SearchURL).userAgent("Mozilla")
				.execute();
		// cookies.putAll(response.cookies());
		return response.parse();
	}
}
