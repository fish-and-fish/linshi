package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BarcodeReaderBuilder;
import com.example.demo.service.BarcodeService;
import com.example.demo.utils.Tools;

@RestController
public class BarcodeController {

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private BarcodeReaderBuilder barcodeReaderBuilder;

	@Autowired
	private Tools tools;

	@RequestMapping("/decode-barcode")
	public List<String> decode(@RequestBody Map<String, String> payload) {
		String imageUrl = payload.get("imageUrl");
		String handshakeCode = payload.get("handshakeCode");
		String license = payload.get("license");

		if (StringUtils.isEmpty((imageUrl))) {
			return new ArrayList<String>();
		}

		String requestId = System.currentTimeMillis() + "-" + Math.round(Math.ceil(Math.random() * 9000 + 1000));
		tools.log("[RequestId=" + requestId + "] [Version.HandshakeCode.Added] request coming...");
		List<String> results = barcodeService.decodeImage(handshakeCode, license, imageUrl, requestId);
		tools.log("[RequestId=" + requestId + "] results=" + String.join(", ", results));
		return results;
	}

	@RequestMapping("/alive")
	public Map<String, Object> alive() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", "hi, i'm still alive");
		result.put("time", System.currentTimeMillis());
		return result;
	}

	@RequestMapping("/handshake-code")
	public String test() {
		tools.log(barcodeReaderBuilder.getHandShakeCode());
		return "this is a test";
	}

}
