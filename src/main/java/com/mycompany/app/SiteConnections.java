package com.mycompany.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SiteConnections {
	private final Map<String, String> cookies = new HashMap<String, String>();

	public Document getInitalCookies(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response = connection.execute();
		cookies.putAll(response.cookies());
		return response.parse();
	}

	public Document getTinDetaildoc(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response = connection.data("tin", "").data("pan", "")
				.data("rc_no", "").data("fptecno", "").data("bptecno", "")
				.data("DEALERNAME", "test").data("Submit", "SEARCH")
				.userAgent("Mozilla").execute();
		cookies.putAll(response.cookies());
		return response.parse();
	}

	public Document getTinDetailPopUpdoc(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response = connection.data("tin", "")
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.referrer(ScrapingConstants.referrer).userAgent("Mozilla")
				.execute();
		cookies.putAll(response.cookies());
		return response.parse();
	}

}
