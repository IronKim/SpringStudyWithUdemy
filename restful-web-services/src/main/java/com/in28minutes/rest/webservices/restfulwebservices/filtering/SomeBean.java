package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//정적 필터링 구현
//@JsonIgnoreProperties("field1") //field1을 json처리 하지 않음

//동적 필터링 구현
@JsonFilter("SomeBeanFilter") //호출하는 필터의 이름과 같아야 함
public class SomeBean {

	private String field1;
	
	//@JsonIgnore  //Json으로 변환시 처리하지 않음
	private String field2;
	private String field3;
	
	public SomeBean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}
	
	public String getField1() {
		return field1;
	}
	public String getField2() {
		return field2;
	}
	public String getField3() {
		return field3;
	}
	
	@Override
	public String toString() {
		return "SomeBean [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
	
	
}
