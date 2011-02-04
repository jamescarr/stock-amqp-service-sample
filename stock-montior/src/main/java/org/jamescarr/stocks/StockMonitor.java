package org.jamescarr.stocks;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.scheduling.annotation.Scheduled;

public class StockMonitor {
	private static final String[] TICKERS = {"IBM", "ORCL", "GOOG"};
	private final StockService stockService;
	private final StockNotifier notifier;
	private final ConcurrentMap<String, Float> previousValue = new ConcurrentHashMap<String, Float>();
	
	public StockMonitor(StockService stockService, StockNotifier notifier) {
		this.stockService = stockService;
		this.notifier = notifier;
	}
	

	@Scheduled(fixedRate=2000)
	public void poll(){
		for(String ticker : TICKERS){
			Float price = stockService.getQuote(ticker);
			notifier.stockPrice(new StockPrice(ticker, price, getPreviousValue(ticker)));
			previousValue.put(ticker, price);
		}
	}


	private Float getPreviousValue(String ticker) {
		final Float previous = previousValue.get(ticker);
		return previous != null? previous : 0.00f;
	}

}
