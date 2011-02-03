package org.jamescarr.stocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@Configuration
@Import(AmqpConfiguration.class)
public class StockMonitorModule {
	@Autowired 
	private StockNotifier notifier;
	
	@Bean
	public StockService stockService(){
		StockServiceClientFactory factory = new StockServiceClientFactory();
		return factory.create();
	}
	
	@Bean
	public StockMonitor monitor(){
		return new StockMonitor(stockService(), notifier);
	}
	
	@Bean
	public ScheduledAnnotationBeanPostProcessor postProcessor(){
		return new ScheduledAnnotationBeanPostProcessor();
	}
	public static void main(String... args){
		new AnnotationConfigApplicationContext(StockMonitorModule.class);
	}
}
