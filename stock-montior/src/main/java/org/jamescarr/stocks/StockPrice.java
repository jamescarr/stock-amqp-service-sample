package org.jamescarr.stocks;


public class StockPrice {
	private final double price;
	private final double previous;
	private final String ticker;

	public StockPrice(String ticker, double price, double previous) {
		this.ticker = ticker;
		this.price = price;
		this.previous = previous;
	}

	public double getPrevious() {
		return previous;
	}

	public double getPrice() {
		return price ;
	}

	public String getTicker() {
		return ticker;
	}

	public double getChange() {
		double difference = price - previous;
		return new Double(Math.round(difference*100.0) / 100.0);
	}
	

}
