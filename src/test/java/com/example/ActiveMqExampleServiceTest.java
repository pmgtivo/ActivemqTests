package com.example;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActiveMqExampleServiceTest {

	@Mock
	private Connection connection;

	@Spy
	@InjectMocks
	private ActiveMqExampleService activeMqExampleService;

	@Test
	public void requestReplyLogicTest() throws NamingException, JMSException {
		activeMqExampleService.requestReplyLogic();
		verify(connection, times(1)).createSession(false, Session.AUTO_ACKNOWLEDGE);

	}

}
