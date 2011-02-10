package org.jamescarr.stocks;

import static org.junit.Assert.*;

import org.jamescarr.stocks.complex.StockWatchCriteria;
import org.junit.Test;


public class StockWatchCriteriaTest {
	@Test
	public void shouldDetermineifTheChangeMatchesOrNot(){
		StockWatchCriteria criteria = new StockWatchCriteria();
		criteria.setBanding(">=");
		criteria.setDirection("up");
		criteria.setPercentage(2);
		criteria.setTicker("IBM");
		
		assertTrue(criteria.matches(new StockPrice("IBM", 100, 70)));
		assertTrue(criteria.matches(new StockPrice("IBM", 100, 97)));
		assertTrue(criteria.matches(new StockPrice("IBM", 100, 98)));
		assertFalse(criteria.matches(new StockPrice("IBM", 100, 99)));
		assertFalse(criteria.matches(new StockPrice("IBM", 100,100)));
		assertFalse(criteria.matches(new StockPrice("IBM", 100, 101)));
	}
	
	@Test
	public void shouldDetermineifTheChangeMatchesOrNotWhenGoingDown(){
		StockWatchCriteria criteria = new StockWatchCriteria();
		criteria.setBanding(">=");
		criteria.setDirection("down");
		criteria.setPercentage(2);
		criteria.setTicker("IBM");
		
		assertTrue(criteria.matches(new StockPrice("IBM", 70, 100)));
		assertTrue(criteria.matches(new StockPrice("IBM", 97, 100)));
		assertTrue(criteria.matches(new StockPrice("IBM", 98, 100)));
		assertFalse(criteria.matches(new StockPrice("IBM", 99, 100)));
		assertFalse(criteria.matches(new StockPrice("IBM", 100,100)));
		assertFalse(criteria.matches(new StockPrice("IBM", 101, 100)));
	}
	
	@Test
	public void shouldDetermineifTheChangeMatchesLessThan(){
		StockWatchCriteria criteria = new StockWatchCriteria();
		criteria.setBanding("<=");
		criteria.setDirection("up");
		criteria.setPercentage(2);
		criteria.setTicker("IBM");
		
		assertFalse(criteria.matches(new StockPrice("IBM", 100, 70)));
		assertFalse(criteria.matches(new StockPrice("IBM", 100, 97)));
		assertFalse(criteria.matches(new StockPrice("IBM", 100.01f, 98)));
		assertTrue(criteria.matches(new StockPrice("IBM", 100, 99)));
		assertTrue(criteria.matches(new StockPrice("IBM", 100,100)));
		assertTrue(criteria.matches(new StockPrice("IBM", 100, 101)));
	}
	
	@Test
	public void shouldDetermineifTheChangeMatchesLessThanWhenGoingDown(){
		StockWatchCriteria criteria = new StockWatchCriteria();
		criteria.setBanding("<=");
		criteria.setDirection("down");
		criteria.setPercentage(2);
		criteria.setTicker("IBM");
		
		assertFalse(criteria.matches(new StockPrice("IBM", 70, 100)));
		assertFalse(criteria.matches(new StockPrice("IBM", 97, 100)));
		assertFalse(criteria.matches(new StockPrice("IBM", 98, 100)));
		assertTrue(criteria.matches(new StockPrice("IBM", 99, 100)));
		assertTrue(criteria.matches(new StockPrice("IBM", 100,100)));
		assertTrue(criteria.matches(new StockPrice("IBM", 101, 100)));
	}
	
	@Test
	public void shouldNotMatchifTickerDoesNotMatch(){
		StockWatchCriteria criteria = new StockWatchCriteria();
		criteria.setBanding("<=");
		criteria.setDirection("down");
		criteria.setPercentage(2);
		criteria.setTicker("IBM");
		

		assertFalse(criteria.matches(new StockPrice("GOOG", 100,100)));
	}
}
