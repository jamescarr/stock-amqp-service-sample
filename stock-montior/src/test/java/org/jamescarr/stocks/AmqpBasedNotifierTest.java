package org.jamescarr.stocks;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.core.AmqpTemplate;

@RunWith(MockitoJUnitRunner.class)
public class AmqpBasedNotifierTest {
	@Mock AmqpTemplate template;
	@InjectMocks AmqpBasedNotifier notifer = new AmqpBasedNotifier(template);
	
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
	
}
