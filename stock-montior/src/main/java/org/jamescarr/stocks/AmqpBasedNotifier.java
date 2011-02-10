package org.jamescarr.stocks;

import java.util.Set;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.jamescarr.stocks.complex.StockWatchCriteria;
import org.springframework.amqp.core.AmqpTemplate;

public class AmqpBasedNotifier implements StockNotifier {
	private final AmqpTemplate template;
	private final Set<StockWatchCriteria> criteria = new ConcurrentHashSet<StockWatchCriteria>();
	
	public AmqpBasedNotifier(AmqpTemplate template){
		this.template = template;
	}
	public void stockPrice(StockPrice stockPrice) {
		template.convertAndSend(computeRoutingKey(stockPrice), stockPrice);
		for(StockWatchCriteria c : criteria){
			if(c.matches(stockPrice)){
				template.convertAndSend(c.getRoutingKey(), stockPrice);
			}
		}
	}
	private String computeRoutingKey(StockPrice stockPrice) {
		final String change = stockPrice.getChange() >= 0? "up":"down"; 
		return "stock."+stockPrice.getTicker()+"."+change;
	}
	public void addCriteria(StockWatchCriteria criteria) {
		System.out.println("criteria added " + criteria);
		this.criteria.add(criteria);
	}

}
