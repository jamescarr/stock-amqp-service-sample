import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.AbstractRabbitConfiguration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SingleConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class AmqpConfiguration  extends AbstractRabbitConfiguration{
	@Bean
	public ConnectionFactory connectionFactory(){
		SingleConnectionFactory conn = new SingleConnectionFactory("localhost");
		conn.setUsername("guest");
		conn.setPassword("guest");
		return conn;
	}
	
	@Override
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setMessageConverter(converter());
		return template;
	}
	
	@Bean
	public MessageConverter converter(){
		return new JsonMessageConverter();
	}
	@Bean
	public Queue queue(){
		Queue queue = new Queue("hello.world");
		amqpAdmin().declareQueue(queue);
		return queue;
	}
	@Bean
	@Scope("prototype")
	public SimpleMessageListenerContainer listenerContainer(){
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		return container;
	}
	
}
