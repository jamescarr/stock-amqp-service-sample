package org.jamescarr.stocks;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.AbstractRabbitConfiguration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfiguration extends AbstractRabbitConfiguration{
	
	private static final String STOCK_NOTIFICATIONS_EXCHANGE = "stock.notifications";
	@Bean
	public ConnectionFactory connectionFactory(){
		SingleConnectionFactory factory = new SingleConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		return factory;
	}
	@Bean
	public Exchange exchange(){
		final TopicExchange topicExchange = new TopicExchange(STOCK_NOTIFICATIONS_EXCHANGE);
		return topicExchange;
	}
	@Bean
	public StockNotifier notifier(){
		amqpAdmin().declareExchange(exchange());
		return new AmqpBasedNotifier(rabbitTemplate());
	}
	@Bean
	public JsonMessageConverter converter(){
		return new JsonMessageConverter();
	}
	@Override
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setExchange(STOCK_NOTIFICATIONS_EXCHANGE);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	

}
