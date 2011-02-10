package org.jamescarr.stocks;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.AbstractRabbitConfiguration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
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
	public TopicExchange exchange(){
		final TopicExchange topicExchange = new TopicExchange(STOCK_NOTIFICATIONS_EXCHANGE);
		return topicExchange;
	}
	@Bean
	public StockNotifier notifier(){
		amqpAdmin().declareExchange(exchange());
		return new AmqpBasedNotifier(rabbitTemplate());
	}
	@Bean
	public Binding bindCommandChannel(){
		final Queue queue = new Queue("stock.watch.specific");
		amqpAdmin().declareQueue(queue);
		return BindingBuilder.from(queue).to(exchange()).with("stock.watch.specific");
	}
	@Bean
	public JsonMessageConverter converter(){
		JsonMessageConverter converter = new JsonMessageConverter();
		converter.setClassMapper(new StockClassMapper());
		return converter;
	}
	@Bean
	public SimpleMessageListenerContainer container(){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setAutoStartup(true);
		container.setConnectionFactory(connectionFactory());
		container.setQueueName("stock.watch.specific");
		container.setMessageListener(adapter());
		return container;
	}
	
	@Bean
	public MessageListenerAdapter adapter() {
		MessageListenerAdapter messageListener = new MessageListenerAdapter(notifier(), converter());
		messageListener.setDefaultListenerMethod("addCriteria");
		return messageListener;
	}
	@Override
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setExchange(STOCK_NOTIFICATIONS_EXCHANGE);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	

}
