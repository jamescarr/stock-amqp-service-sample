package org.jamescarr.stocks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.*;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

@RunWith(MockitoJUnitRunner.class)
public class StockMonitorTest {
	@Mock StockService stocks;
	@Mock StockNotifier notifier;
	@Captor ArgumentCaptor<StockPrice> arg;
	@InjectMocks StockMonitor monitor = new StockMonitor(stocks, notifier);
	
	@Test
	public void pollShouldCheckEachStockTickerAndPassToTemplate(){
		givenStockServiceRerturnsPrices(
				"IBM", 21.23f,0f,
				"ORCL", 31.02f,0f,
				"GOOG", 612.23f,0f
		);
		
		monitor.poll();
		
		verify(notifier, times(3)).stockPrice(arg.capture());
		verifyNotifications(arg.getAllValues(), 
				new StockPrice("IBM", 21.23f, 0f),
				new StockPrice("ORCL", 31.02f, 0f),
				new StockPrice("GOOG", 612.23f, 0f));
	}
	
	@Test
	public void shouldIncludePreviousValuesSecondTimeCalled(){
		givenStockServiceRerturnsPrices(
				"IBM", 21.23f,21.01f,
				"ORCL", 31.02f,31.52f,
				"GOOG", 612.23f,610.23f
				
		);
		
		monitor.poll();
		monitor.poll();
		
		verify(notifier, times(6)).stockPrice(arg.capture());
		verifyNotifications(discardFirstThree(arg.getAllValues()), 
				new StockPrice("IBM", 21.01f,  21.23f),
				new StockPrice("ORCL", 31.52f, 31.02f),
				new StockPrice("GOOG", 610.23f, 612.23f));
	}



	private List<StockPrice> discardFirstThree(List<StockPrice> allValues) {
		for(int i = 0; i < 3; i++)
			allValues.remove(0);
		return allValues;
	}

	private void verifyNotifications(List<StockPrice> allValues, StockPrice... expected) {
		for(int i = 0; i < expected.length; i++){
			StockPrice actual = allValues.get(i);
			assertThat(actual.getPrice(), equalTo(expected[i].getPrice()));
			assertThat(actual.getTicker(), equalTo(expected[i].getTicker()));
			assertThat(actual.getPrevious(), equalTo(expected[i].getPrevious()));
		}
		
	}



	private void givenStockServiceRerturnsPrices(String t1, float p1, float p12, String t2, float p2, float p22, String t3,
			float p3, float p32) {
		given(stocks.getQuote(t1)).willReturn(p1).willReturn(p12);
		given(stocks.getQuote(t2)).willReturn(p2).willReturn(p22);
		given(stocks.getQuote(t3)).willReturn(p3).willReturn(p32);
	}
	
	class CapturingNotifier implements StockNotifier{
		
		public void stockPrice(StockPrice price) {
			
		}
		
	}
}
