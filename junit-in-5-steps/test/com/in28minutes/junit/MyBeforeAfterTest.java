package com.in28minutes.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyBeforeAfterTest {

	@BeforeAll
	static void beforeAll() { //테스트 함수가 실행되기전 한번만 실행
		System.out.println("beforeAll");
	}
	
	@BeforeEach //테스트 함수가 실행되기전에 반복 실행
	void beforeEach() {
		System.out.println("BeforeEach");
	}
	
	@Test
	void test1() {
		System.out.println("test1");
	}
	
	@Test
	void test2() {
		System.out.println("test2");
	}
	
	@Test
	void test3() {
		System.out.println("test3");
	}
	
	@AfterEach //테스트 함수가 실행된후에 반복 실행
	void afterEach() {
		System.out.println("afterEach");
	}
	
	@AfterAll
	static void afterAll() { //테스트 함수가 실행된후 한번만 실행
		System.out.println("afterAll");
	}

}
