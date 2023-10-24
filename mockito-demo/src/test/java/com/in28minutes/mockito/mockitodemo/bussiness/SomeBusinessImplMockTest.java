package com.in28minutes.mockito.mockitodemo.bussiness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) //Mock 과 InjectMocks를 사용하기위해 지정
class SomeBusinessImplMockTest {
	
	@Mock
	private DataService dataServiceMock;
	
	@InjectMocks
	private SomeBusinessImpl businessImpl;

	@Test
	void findTheGreatestFromAllData_basicScenario() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {25, 15, 5});
		assertEquals(25, businessImpl.findTheGreatestFromAllData());
	}
	
	@Test //위에 것이 더 쉬운 방법
	void findTheGreatestFromAllData_OneValue() {
		
		DataService dataServiceMock = mock(DataService.class);
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {35});
		SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceMock);
		assertEquals(35, businessImpl.findTheGreatestFromAllData());
	}
	
	@Test
	void findTheGreatestFromAllData_EmptyArray() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int[] {});
		assertEquals(Integer.MIN_VALUE, businessImpl.findTheGreatestFromAllData());
	}
}
