package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class TestActiveMqTest {
	@Test
	public void testGetVal() {
		// Outside scope
		assertEquals("foo", TestActiveMq.getVal());

		try (MockedStatic<TestActiveMq> mockStatic = mockStatic(TestActiveMq.class)) {

			mockStatic.when(TestActiveMq::getVal).thenReturn("bar");

			// Inside scope
			assertEquals("bar", TestActiveMq.getVal());
			mockStatic.verify(TestActiveMq::getVal);
		}

		// Outside scope
		assertEquals("foo", TestActiveMq.getVal());
	}

	@Test
	public void testAdd() {
		assertEquals(3, TestActiveMq.add(1, 2));

		try (MockedStatic mockStatic = mockStatic(TestActiveMq.class)) {

			mockStatic.when(() -> TestActiveMq.add(anyInt(), anyInt())).thenReturn(10);

			assertEquals(10, TestActiveMq.add(1, 2));
			mockStatic.verify(() -> TestActiveMq.add(1, 2));
		}

		assertEquals(3, TestActiveMq.add(1, 2));
	}
}
