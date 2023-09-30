package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details") //JPA가 관리하기 위해서 필요
public class User {
	
	protected User() {  //JPA를 사용하기 위해 기본생성자 필요
		
	}
	
	@Id //id필드는 식별자이므로 @Id 추가
	@GeneratedValue //시퀀스값 생성?
	private Integer id;
	@Size(min = 2, message = "이름은 최소 2자 이상이어야 합니다.")
	@JsonProperty("user_name")
	private String name;
	@Past(message = "생일은 과거의 날짜여야 합니다.")
	@JsonProperty("birth_date")
	private LocalDate birthDate; 
	
	@OneToMany(mappedBy = "user") //유저와 게시물은 일대다 관계이므로
	@JsonIgnore //User Bean에 대해 게시물을 JSON 응담에 포함시키려는 것은 아니므로
	private List<Post> posts;
	
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
	
	
}
