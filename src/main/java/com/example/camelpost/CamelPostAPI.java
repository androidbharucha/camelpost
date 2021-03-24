package com.example.camelpost;

import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CamelPostAPI {
	@Autowired
	 FluentProducerTemplate producerTemplate; 
	
	 @PostMapping(value = "/posts")
	 public ResponseEntity<String> createPost(@RequestBody String body){
		 
		 String response = producerTemplate.withBody(body).to("direct:processEsign").request(String.class);
		 
		 return new ResponseEntity<>(response,HttpStatus.OK);
		 
	 }
	 
	 
	 @PostMapping(value = "/docusign")
	 public ResponseEntity<String> createDocusign(@RequestBody String body){
		 
		 
		 System.out.println("body received in docusign is "+body);
		
		 return new ResponseEntity<>("My Docusign API called ",HttpStatus.OK);
		 
	 }
	 
	 
	 @PostMapping(value = "/adobe")
	 public ResponseEntity<String> createAdobe(@RequestBody String body){
		 
		 System.out.println("body received in adobe is "+body);
	
		 
		 return new ResponseEntity<>("My Adobe API called ",HttpStatus.OK);
		 
	 }

}
