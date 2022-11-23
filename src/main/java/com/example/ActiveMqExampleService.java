package com.example;

import java.util.Set;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqExampleService {

	@Value("${activemq.requestQueueName}")
	private String requestQueueName;

	@Autowired
	private Connection connection;

	// called for request/reply from activeMQ
	public void requestReplyLogic() {
		AmqRequestClient requestor = null;
		try {
			// all queues fetch
			requestor = AmqRequestClient.getInstance(connection, true);
			

			// TODO business logic on message received
		} catch (Exception e) {
			// Log exception
		} finally {
			if (requestor != null) {
				requestor.close();
			}
		}
	}

	public void getQueues() {
		try {
			DestinationSource ds = new DestinationSource(connection);
			ds.start();

			Set<ActiveMQQueue> queues = ds.getQueues();

			for (ActiveMQQueue activeMQQueue : queues) {
				try {
					System.out.println(activeMQQueue.getQueueName());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
