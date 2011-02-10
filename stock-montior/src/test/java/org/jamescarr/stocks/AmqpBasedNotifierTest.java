package org.jamescarr.stocks;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.jamescarr.stocks.complex.StockWatchCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.core.AmqpTemplate;

@RunWith(MockitoJUnitRunner.class)
public class AmqpBasedNotifierTest {
	@Mock AmqpTemplate template;
	@Mock StockWatchCriteria criteria;
	@InjectMocks AmqpBasedNotifier notifer = new AmqpBasedNotifier(template);
	
	@Before
	public void beforeEach(){
		notifer.addCriteria(criteria);
	}
	@Test
	public void shouldSubmitWithRoutingKeyRepresentingStockPriceIncrese(){
		final StockPrice stockPrice = new StockPrice("IBM", 23.33f, 21.22f);
		
		notifer.stockPrice(stockPrice);
		
		verify(template).convertAndSend("stock.IBM.up", stockPrice);
	}
	
	@Test
	public void shouldSubmitWithRoutingKeyRepresentingStockPriceDecrese(){
		final StockPrice stockPrice = new StockPrice("IBM", 23.33f, 24.22f);
		
		notifer.stockPrice(stockPrice);
		
		verify(template).convertAndSend("stock.IBM.down", stockPrice);
	}
	
	@Test
	public void shouldAlsoSendMessagesForSpecialCriteria(){
		final StockPrice stockPrice = new StockPrice("IBM", 23.33f, 24.22f);
		given(criteria.matches(stockPrice)).willReturn(true);
		given(criteria.getRoutingKey()).willReturn("key");
		
		notifer.stockPrice(stockPrice);
		
		InOrder inOrder = Mockito.inOrder(template);
		inOrder.verify(template).convertAndSend("stock.IBM.down", stockPrice);
		inOrder.verify(template).convertAndSend("key", stockPrice);
	}
	
}
