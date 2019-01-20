package com.gd.tdd.tddrestDemo.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gd.tdd.tddrestDemo.model.AccountDetails;

/**
 * @author Gajanan
 * This Service class will Perform CRUD operations for Account.
 */
@Service
public class ManageAccountsService {
	
	//This will act as Java cache to store the Account details as no DB is needed for this assignment
	private Map<String, AccountDetails> map = new ConcurrentHashMap<>();

	public String addAccount(AccountDetails account) throws Exception {
		account.setId(UUID.randomUUID().toString());
		//The below code will check if the firstName and secondName already exists
		if(!(map.values().stream().anyMatch(value -> account.getFirstName().equals(value.getFirstName()) && 
				account.getSecondName().equals(value.getSecondName())))) {
			map.put(account.getId(), account);
		}
		else
		{
			throw new Exception("ALREADY_EXISTS");
		}
		return "The account has been successfully added";
	}

	public List<AccountDetails> getAccounts() {
		return map.values().stream().collect(Collectors.toList());
	}

	public String deleteAccount(String id) throws Exception {
		//The below code will check if the id exists in the map to delete 
		if(map.containsKey(id)) {
			map.remove(id);
		}
		else {
			throw new Exception("NOT_FOUND");
		}
		return "The account has been successfully deleted";
	}

}
