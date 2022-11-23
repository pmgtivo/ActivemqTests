package com.example;

import java.util.stream.IntStream;

public class TestActiveMq {

	public static String getVal() {
		return "foo";
	}

	public static int add(int... args) {
		return IntStream.of(args).sum();
	}
}
