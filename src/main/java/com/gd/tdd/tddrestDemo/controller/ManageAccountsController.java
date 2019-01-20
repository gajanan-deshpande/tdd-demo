package com.gd.tdd.tddrestDemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gd.tdd.tddrestDemo.exception.RecordAlreadyExistsException;
import com.gd.tdd.tddrestDemo.exception.RecordDoesNotExistException;
import com.gd.tdd.tddrestDemo.model.AccountDetails;
import com.gd.tdd.tddrestDemo.service.ManageAccountsService;

/**
 * @author Gajanan
 * This Controller will perform basic CRUD operations for Account
 */
@RestController
@RequestMapping("/account/")
public class ManageAccountsController {
	
	@Autowired
	private ManageAccountsService accountService;
	
	@PostMapping(path = "create")
    public ResponseEntity<String> addAccount(@RequestBody @Valid AccountDetails account) {
		String message = null;
		HttpStatus httpStatus = null;
		
		try {
			message = accountService.addAccount(account);
			httpStatus = HttpStatus.CREATED;
		}
		catch(RecordAlreadyExistsException ex) {
			message = ex.getLocalizedMessage();
			httpStatus = HttpStatus.BAD_REQUEST;
		}
        return new ResponseEntity<>(message,httpStatus);
    }
	
	@GetMapping(path = "get")
    public ResponseEntity<List<AccountDetails>> getAllAccounts(){
        return new ResponseEntity<>(accountService.getAccounts(),HttpStatus.OK);
    }
	
	 @DeleteMapping("delete/{id}")
	 public ResponseEntity<String> deleteAccount(@PathVariable(name = "id") String id) {
	     String message = null;
	     HttpStatus httpStatus = null;
		 try {
	    	 message = accountService.deleteAccount(id);
	    	 httpStatus = HttpStatus.OK;
	     }
	     catch(RecordDoesNotExistException ex) {
	    	 httpStatus = HttpStatus.BAD_REQUEST;
	     }
		 return new ResponseEntity<>(message,httpStatus);
	 }
}
