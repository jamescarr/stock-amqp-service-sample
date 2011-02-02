package org.jamescarr.stocks;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;



public class StockServiceClientFactory {
	private String baseAddress = "http://localhost:8080";
	
	public void setBaseAddress(String host) {
		this.baseAddress = host;
	}

	public StockService create(){
		return JAXRSClientFactory.create(baseAddress, StockService.class);
	}
}
