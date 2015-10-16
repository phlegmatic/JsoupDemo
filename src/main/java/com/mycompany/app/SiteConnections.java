package com.mycompany.app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mycompany.pojo.SearchCriteria;
/* 
 * Purpose :Formulate get or post connections and holding cookies in subsequent calls*/
public class SiteConnections {
	private static Map<String, String> cookies = new HashMap<String, String>();

	public Document getInitalCookies(String url) throws IOException {

		cookies = new HashMap<String, String>();
		Connection connection = Jsoup.connect(url).timeout(10 * 1000)
				.cookies(cookies);
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

		Connection connection = Jsoup.connect(url).timeout(10 * 1000)
				.cookies(cookies);
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

		Connection connection = Jsoup.connect(url).timeout(10 * 1000)
				.cookies(cookies);
		Response response = connection
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.referrer(ScrapingConstants.SearchURL).userAgent("Mozilla")
				.execute();
		cookies.putAll(response.cookies());
		return response.parse();
	}
}
