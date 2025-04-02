package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dynamsoft.dbr.BarcodeReader;
import com.dynamsoft.dbr.TextResult;
import com.example.demo.utils.Tools;

@Component
public class BarcodeService {

	@Autowired
	private HttpClient httpClient;

	@Autowired
	private Tools tools;

	@Autowired
	private BarcodeReaderBuilder barcodeReaderBuilder;

	public List<String> decodeImage(String handshakeCode, String license, String imageUrl, String requestId) {

		List<String> decodedResults = new ArrayList<String>();

		try {
			long startTime = System.currentTimeMillis();
			BarcodeReader br = barcodeReaderBuilder.getInstance();

			long endTime = System.currentTimeMillis();
			tools.log("[RequestId=" + requestId + "] instance created, time taken=" + (endTime - startTime) + "ms");
			tools.log("[RequestId=" + requestId + "] imageUrl=" + imageUrl);

			HttpUriRequest imageRequest = new HttpGet(imageUrl);
			HttpResponse imageResponse = httpClient.execute(imageRequest);

			startTime = System.currentTimeMillis();
			TextResult[] results = br.decodeFileInMemory(imageResponse.getEntity().getContent(), "");
			endTime = System.currentTimeMillis();
			tools.log("[RequestId=" + requestId + "] recognize finished, time taken=" + (endTime - startTime) + "ms");

			for (TextResult result : results) {
				decodedResults.add(result.barcodeText);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return decodedResults;
	}
}
