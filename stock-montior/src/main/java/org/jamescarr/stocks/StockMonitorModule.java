package org.jamescarr.stocks;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@Configuration
public class StockMonitorModule {
	@Bean
	public StockService stockService(){
		StockServiceClientFactory factory = new StockServiceClientFactory();
		return factory.create();
	}
	
	@Bean
	public StockMonitor monitor(){
		return new StockMonitor(stockService(), null);
	}
	
	@Bean
	public ScheduledAnnotationBeanPostProcessor postProcessor(){
		return new ScheduledAnnotationBeanPostProcessor();
	}
	public static void main(String... args){
		new AnnotationConfigApplicationContext(StockMonitorModule.class);
	}
}
