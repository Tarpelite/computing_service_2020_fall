package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class HelloController {

	// private PersonRepository Repo;
	private Person pr = new Person ();
	//use the first person for test

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/name")
	public String getName(){
		return pr.getName();
	}

	@GetMapping("/age")
	public int getAge(){
		// Person p = this.Repo.findAll()[0];
		return this.pr.getAge();
	}

	@GetMapping("/gender")
	public boolean getGender(){
		// Person p = this.Repo.findAll()[0];
		return this.pr.getGender();
	}

	@PostMapping("/name")
	public String setName(@RequestBody Person p){
		this.pr.setName(p.getName());
		return "Update Name!";
	}

	@PostMapping("/age")
	public String setAge(@RequestBody Person p){
		this.pr.setAge(p.getAge());
		return "Update Age!";
	}

	@PostMapping("/gender")
	public String setGender(@RequestBody Person p){
		
		this.pr.setGender(p.getGender());
		return "Update Gender!";
	}

	@RequestMapping("/sayHello")
	public String sayHello() {
		return this.pr.sayHello();
	}
}
	



