package com.example;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

public class AmqRequestClient {

	private Session session;
	private Connection connection;

	private AmqRequestClient() {
		super();
	}

	public static AmqRequestClient getInstance(Connection connection, boolean isAutoAck)
			throws NamingException, JMSException {
		AmqRequestClient client = new AmqRequestClient();
		client.connection = connection;
		client.initialize(isAutoAck);
		return client;
	}

	protected void initialize(boolean isAutoAck) throws NamingException, JMSException {
		if (isAutoAck) {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} else {
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		}

	}

	public void close() {
		try {

			if (session != null) {
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}