package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service;
	}
	
	//GET /users
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
 
//	//GET /users
//	@GetMapping("/users/{id}")
//	public User retrieveUser(@PathVariable int id) {
//		User user = service.findOne(id);
//		
//		if(user == null) {
//			throw new UserNotFoundException("id:" + id);
//		}
//		
//		return user;
//	}
	
	
	//http://localhost:8080/users
	
	//EntityModel
	//WebMvcLinkBuilder
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		
		EntityModel<User> entitiyModel = EntityModel.of(user);
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entitiyModel.add(link.withRel("all-users"));
		
		return entitiyModel;
	}
	
	@DeleteMapping("/users/{id}")
	public void DeleteUser(@PathVariable int id) {
		service.deleteById(id);
		
	}
	
	//POST /users
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		// /users/4 => /users/{id},        user.getID      ↓ 현재 요청에 해당하는 URL 반환
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						 .path("/{id}") ///id 를 만들어
						 .buildAndExpand(savedUser.getId()) //그곳에 저장된 유저 아이디를 넣고
						 .toUri(); //uri로 반환한다.
		
		return ResponseEntity.created(location).build(); //해당 uri를 만들어서 응답한다.
	}
}

/*
 	404 - 리소스를 찾지 못할 때
 	500 - 서버에서 예외 발생
 	400 - 검증 에러 발생 (정보를 전달했는데 해당 정보가 검증을 통과 하지 못할 때)
 	200 - 성공
 	201 - POST 요청으로 새 리소스를 생성 했을때
 	204 - 콘텐츠를 업데이트 하기 위해 PUT 요청을 보냈으나 콘텐츠가 없을 때
 	401 - 사용자가 요청에 올바른 정보를 제공하지 않을 때
 
 */


