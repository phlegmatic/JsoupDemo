package com.mycompany.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Quicktest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Map<String, String> cookies = new HashMap<String, String>();

		Connection connection1 = Jsoup
				.connect("http://mahavat.gov.in/Tin_Search/Tinsearch.jsp");
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection1.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response1 = connection1.execute();
		cookies.putAll(response1.cookies());

		// Second request.
		Connection connection2 = Jsoup
				.connect("http://mahavat.gov.in/Tin_Search/TinSearch");
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection2.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response2 = connection2.data("tin", "").data("pan", "")
				.data("rc_no", "").data("fptecno", "").data("bptecno", "")
				.data("DEALERNAME", "test").data("Submit", "SEARCH")
				.userAgent("Mozilla").execute();
		cookies.putAll(response2.cookies());
		Document document2 = response2.parse();

		// Second request.
		Connection connection3 = Jsoup
				.connect("http://mahavat.gov.in/Tin_Search/Tin_display?tinnumber=27900332842C");
		for (Entry<String, String> cookie : cookies.entrySet()) {
			connection3.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response3 = connection3.userAgent("Mozilla")
				.referrer("http://mahavat.gov.in/Tin_Search/TinSearch")
				.timeout(500).execute();
		cookies.putAll(response3.cookies());
		Document document3 = response3.parse();

		System.out.println(document3);

	}
}
