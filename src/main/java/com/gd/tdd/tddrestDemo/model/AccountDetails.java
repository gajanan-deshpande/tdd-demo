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
	private String firstName;
	
	//Second Name of the account holder
	private String secondName;
	
	//Account number of the account holder
	private Integer accountNumber;

}
