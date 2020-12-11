package com.example.demo.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    public static final String QUEUE_ORDERS = "orders-queue";
    public static final String QUEUE_DEAD_ORDERS = "dead-orders-queue";
    public static final String EXCHANGE_ORDERS = "orders-exchange";

    public static final String QUEUE_LOG = "log-message-queue";
    public static final String QUEUE_DEAD_LOG = "dead-logmessage-queue";
    public static final String EXCHANGE_LOG = "audit-exchange";
    
    //
    public static final String ORDER_QUEUE = "order_queue";
    public static final String EXCHANGE = "shopingcart_exchange";
    public static final String ORDER_ROUTING_KEY = "order_routingKey";
    
    public static final String AUDIT_QUEUE = "audit_queue";
//    public static final String EXCHANGE_AUDIT = "shopingcartAudit_exchange";
    public static final String AUDIT_ROUTING_KEY = "audit_routingKey";
    
//    @Bean
//    Queue ordersQueue() {
//
//        return QueueBuilder.durable(QUEUE_ORDERS)
//                .withArgument("x-dead-letter-exchange", "")
//                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_ORDERS)
//                .withArgument("x-message-ttl", 1800000)
//                .build();
//    }
//
//    @Bean
//    Queue deadLetterQueue() {
//        return QueueBuilder.durable(QUEUE_DEAD_ORDERS).build();
//    }
//
//    @Bean
//    Exchange ordersExchange() {
//        return ExchangeBuilder.topicExchange(EXCHANGE_ORDERS).build();
//    }
//
//    @Bean
//    Binding binding1(Queue ordersQueue, TopicExchange ordersExchange) {
//        return BindingBuilder.bind(ordersQueue).to(ordersExchange).with(QUEUE_ORDERS);
//    }
//
//    @Bean
//    Exchange logExchange() {
//        return ExchangeBuilder.topicExchange(EXCHANGE_LOG).build();
//    }

//    @Bean
//    Binding binding2(Queue ordersQueue, TopicExchange logExchange) {
//        return BindingBuilder.bind(ordersQueue).to(logExchange).with(QUEUE_LOG);
//    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
    

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
    
    //My changes are..
//    
    /*
    @Bean
	public Queue queue() {
		return new Queue(QUEUE);
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}
	
	 @Bean
	    public Binding binding(Queue queue, TopicExchange exchange) {
	        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	 }
	 
	 
	 @Bean
		public Queue queueAudit() {
			return new Queue(QUEUE_AUDIT);
		}
		
		@Bean
		public TopicExchange exchangeAudit() {
			return new TopicExchange(EXCHANGE_AUDIT);
		}
		
		 @Bean
		    public Binding bindingAudit(Queue queue, TopicExchange exchange) {
		        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_AUDIT);
		 }
		 
	 */
	 
	 
	 @Bean
	    public MessageConverter converter() {
	        return new Jackson2JsonMessageConverter();
	   }
	 
	 
	 @Bean
	    public AmqpTemplate template(ConnectionFactory connectionFactory) {
	        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setMessageConverter(converter());
	        return rabbitTemplate;
	    }
	 
	 
	 //////
	   @Bean
		Queue orderQueue() {
			return new Queue(ORDER_QUEUE);
		}

		@Bean
		Queue auditQueue() {
			return new Queue(AUDIT_QUEUE);
		}


		@Bean
		TopicExchange topicExchange() {
			return new TopicExchange(EXCHANGE);
		}
		
		@Bean
		Binding marketingBinding(Queue orderQueue, TopicExchange topicExchange) {
			return BindingBuilder.bind(orderQueue).to(topicExchange).with(ORDER_ROUTING_KEY);
		}
		
		@Bean
		Binding financeBinding(Queue auditQueue, TopicExchange topicExchange) {
			return BindingBuilder.bind(auditQueue).to(topicExchange).with(AUDIT_ROUTING_KEY);
		}
		

}
