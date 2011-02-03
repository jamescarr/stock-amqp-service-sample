package org.jamescarr.stocks;

import org.springframework.scheduling.annotation.Scheduled;

public class StockMonitor {
	private static final String[] TICKERS = {"IBM", "ORCL", "GOOG"};
	private final StockService stockService;
	private final StockNotifier notifier;

	public StockMonitor(StockService stockService, StockNotifier notifier) {
		this.stockService = stockService;
		this.notifier = notifier;
	}
	

	@Scheduled(fixedRate=1000)
	public void poll(){
		for(String ticker : TICKERS){
			System.out.println(ticker + ": " + stockService.getQuote(ticker));			
		}
		System.out.println();
	}

}
