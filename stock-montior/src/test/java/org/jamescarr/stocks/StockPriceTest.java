package org.jamescarr.stocks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class StockPriceTest {
	@Test
	public void shoudlCalculateTheDifferenceInCurrentAndPreviousPrice(){
		StockPrice stock = new StockPrice("IBM", 21.22f, 20.00f);
		
		assertThat(stock.getChange(), equalTo(1.22f));
	}
	
	@Test
	public void shoudlCalculateTheDifferenceInCurrentAndPreviousPriceWhenNegative(){
		StockPrice stock = new StockPrice("IBM", 21.50f, 22.00f);
		
		assertThat(stock.getChange(), equalTo(-0.50f));
	}
}
