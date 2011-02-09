import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AmqpConfiguration.class)
public class Producer {
	@Autowired
	private AmqpTemplate template;
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Producer.class);
		AmqpTemplate template = ctx.getBean(AmqpTemplate.class);
		
		template.convertAndSend("hello.world", "Hello World");
		
	}
}
