package com.scastellanos.mastermind.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/mastermind")
public class MastermindController {

	
	@RequestMapping(value="/new/{codeSize}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity createNewGame(@PathVariable("codeSize") Integer codeSize) {
		 return new ResponseEntity<>(
			      "Hello mastermind: " + codeSize, HttpStatus.OK);
	}
}
