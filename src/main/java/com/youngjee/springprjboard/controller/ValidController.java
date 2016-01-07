package com.youngjee.springprjboard.controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youngjee.springprjboard.domain.User;

@Controller
public class ValidController {
	
	private static final Logger log = LoggerFactory.getLogger(ValidController.class);
	
//	http://localhost:8180/springprjboard/validuser
	@RequestMapping("/validuser")
	public String init(@ModelAttribute("user") User user) {
		return "validUser";
	}

	@RequestMapping(value="/validuser/create" , method=RequestMethod.GET)
	public String validUser(@Valid User user, BindingResult bindingResult) {
		log.debug("User : {}",user);
		
		if (bindingResult.hasErrors()) {
			log.debug("binding result has error>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			 List<ObjectError> errors =  bindingResult.getAllErrors();
			 
			 for (ObjectError error : errors) {
//				log.debug("error: {},{} ", error.getCode(), error.getDefaultMessage() );
				log.debug("error: {},{} ", error.getObjectName(),error.getDefaultMessage() );
			}
			
			return "validUser";
		}
		
		return "redirect:/validUserOK";
	}
	
	@RequestMapping("/validUserOK")
	public String validUserOK() {
		return "validUserOK";
	}
}
