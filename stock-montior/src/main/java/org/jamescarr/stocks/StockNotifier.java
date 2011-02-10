package org.jamescarr.stocks;

import org.jamescarr.stocks.complex.StockWatchCriteria;

public interface StockNotifier {
	public void stockPrice(StockPrice price);
	void addCriteria(StockWatchCriteria criteria);
}
