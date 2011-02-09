import javax.annotation.PostConstruct;
import javax.xml.ws.BindingType;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AmqpConfiguration.class)
public class Consumer {
	@Autowired
	private SimpleMessageListenerContainer container;
	@Autowired
	private MessageConverter converter;
	
	
	@PostConstruct
	public void listen(){
		container.setMessageListener(new MessageListenerAdapter(new Echo(), converter));
		container.setQueueName("hello.world");
		container.start();
	}
	class Echo{
		public void handleMessage(String message){
			System.out.
			println(message);
		}
	}
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Consumer.class);
		System.out.println("Listening for messages.");
	}
}
