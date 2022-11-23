package com.example;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class SenderConfig {

	@Value("${activemq.broker-url}")
	private String brokerUrl;

	@Bean
	public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = null;
		try {
			activeMQConnectionFactory = new ActiveMQConnectionFactory(new URI(brokerUrl));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return activeMQConnectionFactory;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(
				senderActiveMQConnectionFactory());
		return cachingConnectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
		JmsTemplate template = new JmsTemplate(cachingConnectionFactory);
		template.setReceiveTimeout(5000);
		template.setMessageConverter(new MappingJackson2MessageConverter());
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public JmsMessagingTemplate jmsMessagingTemplate(CachingConnectionFactory cachingConnectionFactory,
			JmsTemplate jmsTemplate) {
		JmsMessagingTemplate template = new JmsMessagingTemplate(jmsTemplate);
		template.setJmsTemplate(jmsTemplate);
		template.setConnectionFactory(cachingConnectionFactory);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	@ApplicationScope
	public ActiveMQConnection cachingConnection(CachingConnectionFactory cachingConnectionFactory) {
		ActiveMQConnection activeMQConnection = null;
		try {
			
			Connection connection = cachingConnectionFactory.createConnection();
			connection.createSession();
			if( connection  instanceof ActiveMQConnection) {
				activeMQConnection = (ActiveMQConnection) connection;
				
			}else {
				cachingConnectionFactory.createTopicConnection();
			}
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return activeMQConnection;
	}

}
