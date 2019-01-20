package com.gd.tdd.tddrestDemo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gajanan
 * This class models the Account details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetails {
	
	//ID of the record
	private String id;
	
	//First Name of the account holder
	@NotNull(message = "firstName must not be null")
	@NotEmpty(message = "firstName must not be blank")
	@Pattern(regexp="[a-zA-Z]+", message="Invalid first name")	
	private String firstName;
	
	//Second Name of the account holder
	@NotNull(message = "secondName must not be null")
	@NotEmpty(message = "secondName must not be blank")
	@Pattern(regexp="[a-zA-Z]+", message="Invalid second name")	
	private String secondName;
	
	//Account number of the account holder
	@NotNull(message = "accountNumber must not be null")
	private Integer accountNumber;

}
