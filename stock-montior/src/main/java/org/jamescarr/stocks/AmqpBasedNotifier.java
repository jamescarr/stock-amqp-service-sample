package org.jamescarr.stocks;

import org.springframework.amqp.core.AmqpTemplate;

public class AmqpBasedNotifier implements StockNotifier {
	private final AmqpTemplate template;
	public AmqpBasedNotifier(AmqpTemplate template){
		this.template = template;
	}
	public void stockPrice(StockPrice stockPrice) {
		template.convertAndSend(computeRoutingKey(stockPrice), stockPrice);
	}
	private String computeRoutingKey(StockPrice stockPrice) {
		final String change = stockPrice.getChange() >= 0? "up":"down"; 
		return "stock."+stockPrice.getTicker()+"."+change;
	}

}
