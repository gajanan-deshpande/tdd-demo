package com.gd.tdd.tddrestDemo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.tdd.tddrestDemo.TddRestDemoApplication;
import com.gd.tdd.tddrestDemo.model.AccountDetails;
import com.gd.tdd.tddrestDemo.service.ManageAccountsService;

/**
 * @author Gajanan
 * This Test class will test all the API's in the ManageAccountsController
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TddRestDemoApplication.class)
@WebMvcTest
public class ManageAccountsControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ManageAccountsService accountsService;
	
	@Autowired 
	private ObjectMapper mapper;
	
	private List<AccountDetails> accDetailsList;
	
	 @Before
	    public void setup() throws Exception {
		AccountDetails accDetails = AccountDetails.builder()
				.firstName("Jack")
				.secondName("Sparrow")
				.accountNumber(12345678).build();
		
		AccountDetails accDetails1 = AccountDetails.builder()
				.firstName("Jack1")
				.secondName("Sparrow")
				.accountNumber(12345678).build();
		
		accDetailsList = new ArrayList<>();
		accDetailsList.add(accDetails);
		accDetailsList.add(accDetails1);
	 }
	
	@Test
	public void willCreateAccount() throws Exception {
		
		when(accountsService.addAccount(accDetailsList.get(0)))
			.thenReturn("The account has been successfully added");
		
		String json = mapper.writeValueAsString(accDetailsList.get(0));
	    mvc.perform(post("/account/create")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isCreated())
	       .andExpect(content().string("The account has been successfully added"));
	}
	
	@Test(expected=Exception.class)
	public void willSendAlreadyExistsCreateAccount() throws Exception {
		
		when(accountsService.addAccount(accDetailsList.get(0)))
			.thenThrow(new Exception("ALREADY_EXISTS"));
		
		String json = mapper.writeValueAsString(accDetailsList.get(0));
	    mvc.perform(post("/account/create")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().is5xxServerError());
	}
	
	@Test
	public void willSendBadResponseCreateAccountInvalidFirstname() throws Exception {
		String json = mapper.writeValueAsString(accDetailsList.get(1));
	    mvc.perform(post("/account/create")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void willSendBadResponseCreateAccountBlankFirstname() throws Exception {
		AccountDetails accDetails = accDetailsList.get(1);
		accDetails.setFirstName("");
		String json = mapper.writeValueAsString(accDetails);
	    mvc.perform(post("/account/create")
	       .contentType(MediaType.APPLICATION_JSON)
	       .content(json)
	       .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void willGetAllAccounts() throws Exception {
		
		when(accountsService.getAccounts()).thenReturn(accDetailsList);
		
		mvc.perform(get("/account/get")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(2)))
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].firstName", is("Jack")))
			      .andExpect(jsonPath("$[0].secondName", is("Sparrow")));
	}
	
	@Test
	public void willDeleteAccount() throws Exception {
		
		when(accountsService.deleteAccount("123456"))
			.thenReturn("The account has been successfully deleted");
		
		mvc.perform(delete("/account/delete/{id}", "123456")
			      .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(content().string("The account has been successfully deleted"));
	}
	
	@Test(expected=Exception.class)
	public void willSendNotFoundDeleteAccountForInvalidId() throws Exception {
		
		when(accountsService.deleteAccount("123456"))
			.thenThrow(new Exception("NOT_FOUND"));
		
		mvc.perform(delete("/account/delete/{id}", "123456")
			      .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isExpectationFailed())
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

}
