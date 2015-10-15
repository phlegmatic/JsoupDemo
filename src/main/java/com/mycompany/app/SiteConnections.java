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

public class SiteConnections {
	private static Map<String, String> cookies = new HashMap<String, String>();

	public Document getInitalCookies(String url) throws IOException {

		cookies = new HashMap<String, String>();
		// System.out.println("1" + cookies.toString());
		Connection connection = Jsoup.connect(url).timeout(10 * 1000)
				.cookies(cookies);
		// for (Entry<String, String> cookie : cookies.entrySet()) {
		// connection.cookie(cookie.getKey(), cookie.getValue());
		// }
		// System.out.println("2" + cookies.toString());
		try {
			Response response = connection.execute();
			cookies.putAll(response.cookies());
			// System.out.println("2" + cookies.toString());
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
		// for (Entry<String, String> cookie : cookies.entrySet()) {
		// connection.cookie(cookie.getKey(), cookie.getValue());
		// }
		// System.out.println("3" + cookies.toString());
		Response response;
		try {
			response = connection.data("tin", searchcriteria.getTinNumber())
					.data("pan", searchcriteria.getPanNumber())
					.data("rc_no", "").data("fptecno", "").data("bptecno", "")
					.data("DEALERNAME", searchcriteria.getDealerName())
					.data("Submit", "SEARCH").userAgent("Mozilla").execute();
			cookies.putAll(response.cookies());
			// System.out.println("4" + cookies.toString());
			return response.parse();
		}

		catch (IOException e) {
			System.out.println("Connection Problem");

		}
		return null;

	}

	public Document getTinDetailPopUpdoc(String url) throws IOException {
		// System.out.println(url);
		Connection connection = Jsoup.connect(url).timeout(10 * 1000)
				.cookies(cookies);
		// for (Entry<String, String> cookie : cookies.entrySet()) {
		// connection.cookie(cookie.getKey(), cookie.getValue());
		// }
		// System.out.println("5" + cookies.toString());
		Response response = connection
				.header("Accept-Encoding", "gzip, deflate, sdch")
				.referrer(ScrapingConstants.SearchURL).userAgent("Mozilla")
				.execute();
		cookies.putAll(response.cookies());
		// System.out.println("6" + cookies.toString());
		return response.parse();
	}
}
