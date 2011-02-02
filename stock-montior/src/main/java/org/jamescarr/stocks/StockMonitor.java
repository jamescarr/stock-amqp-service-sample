package org.jamescarr.stocks;

import org.springframework.scheduling.annotation.Scheduled;

public class StockMonitor {
	private static final String[] TICKERS = {"IBM", "ORCL", "GOOG", "MSFT"};
	private final StockService stockService;

	public StockMonitor(StockService stockService) {
		this.stockService = stockService;
	}
	
	@Scheduled(fixedRate=1000)
	public void poll(){
		for(String ticker : TICKERS){
			System.out.println(ticker + ": " + stockService.getQuote(ticker));			
		}
		System.out.println();
	}

}
