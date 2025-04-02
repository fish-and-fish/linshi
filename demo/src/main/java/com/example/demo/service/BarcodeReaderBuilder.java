package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dynamsoft.dbr.BarcodeReader;
import com.dynamsoft.dbr.BarcodeReaderException;
import com.dynamsoft.dbr.DMLTSConnectionParameters;
import com.dynamsoft.dbr.EnumBarcodeFormat;
import com.dynamsoft.dbr.EnumConflictMode;
import com.dynamsoft.dbr.PublicRuntimeSettings;

@Component
public class BarcodeReaderBuilder {

	@Value("${dynamsoft.handShakeCode}")
	private String handShakeCode;

	public String getHandShakeCode() {
		return handShakeCode;
	}

	public BarcodeReader getInstance() {

		BarcodeReader barcodeReader = null;

		try {
			barcodeReader = new BarcodeReader("");
			DMLTSConnectionParameters ltspar = barcodeReader.initLTSConnectionParameters();
			ltspar.handshakeCode = handShakeCode;
			barcodeReader.initLicenseFromLTS(ltspar);
			barcodeReader.initRuntimeSettingsWithString(
					"{\"ImageParameter\":{\"Name\":\"BestCoverage\",\"DeblurLevel\":9,\"ExpectedBarcodesCount\":512,\"ScaleDownThreshold\":100000,\"LocalizationModes\":[{\"Mode\":\"LM_CONNECTED_BLOCKS\"},{\"Mode\":\"LM_SCAN_DIRECTLY\"},{\"Mode\":\"LM_STATISTICS\"},{\"Mode\":\"LM_LINES\"},{\"Mode\":\"LM_STATISTICS_MARKS\"}],\"GrayscaleTransformationModes\":[{\"Mode\":\"GTM_ORIGINAL\"},{\"Mode\":\"GTM_INVERTED\"}]}}",
					EnumConflictMode.CM_OVERWRITE);

			PublicRuntimeSettings runtimeSettings = barcodeReader.getRuntimeSettings();
			runtimeSettings.expectedBarcodesCount = 512;
			runtimeSettings.barcodeFormatIds = EnumBarcodeFormat.BF_CODE_128 | EnumBarcodeFormat.BF_DATAMATRIX;
			runtimeSettings.barcodeFormatIds_2 = 0;

			barcodeReader.updateRuntimeSettings(runtimeSettings);
		} catch (BarcodeReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return barcodeReader;
	}


}
