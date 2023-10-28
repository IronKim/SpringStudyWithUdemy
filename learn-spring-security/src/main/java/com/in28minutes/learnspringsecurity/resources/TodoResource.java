package com.in28minutes.learnspringsecurity.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
public class TodoResource {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final List<Todo> TODOS_LIST = 
			List.of(new Todo("in28minutes", "Learn AWS"),
					new Todo("in28minutes", "Learn AWS"));
	
	@GetMapping("/todos")
	public List<Todo> retrieveAllTodos() {
		return TODOS_LIST;
	}
	
	@GetMapping("/users/{username}/todos")
	// 현재 사용자가 USER 역할을 가지고 있고, 요청의 username과 현재 사용자의 이름이 일치하는지 확인
	@PreAuthorize("hasRole('USER') and #username == authentication.name")
	// 메소드 실행 후 결과 객체의 username이 'in28minutes'와 일치하는지 확인
	@PostAuthorize("returnObject.username == 'in28minutes'")
	// ADMIN 또는 USER 역할을 가진 사용자만 접근 가능
	@RolesAllowed({"ADMIN", "USER"})
	// ROLE_ADMIN 또는 ROLE_USER 역할을 가진 사용자만 접근 가능
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public Todo retrieveTodosForSpecificUser(@PathVariable String username) {
		return TODOS_LIST.get(0);
	}
	
	@PostMapping("/users/{username}/todos")
	public void createTodosForSpecificUser(@PathVariable String username, @RequestBody Todo todo) {
		logger.info("Create {} for {}", todo, username);
	}
}

record Todo (String username, String description) {}