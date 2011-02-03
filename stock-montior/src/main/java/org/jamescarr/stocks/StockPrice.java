package org.jamescarr.stocks;

public class StockPrice {
	private final float price;
	private final float previous;
	private final String ticker;

	public StockPrice(String ticker, float price, float previous) {
		this.ticker = ticker;
		this.price = price;
		this.previous = previous;
	}

	public float getPrevious() {
		return previous;
	}

	public float getPrice() {
		return price;
	}

	public String getTicker() {
		return ticker;
	}

	public Float getChange() {
		Float difference = price - previous;
		return new Float(Math.round(difference*100.0) / 100.0);
	}
	

}
