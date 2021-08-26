package com.buddy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.buddy.dto.BankDTO;
import com.buddy.model.BankAccount;
import com.buddy.model.Users;
import com.buddy.service.BankService;
import com.buddy.service.TransactionService;
import com.buddy.service.UsersService;

@WebMvcTest(controllers = BankController.class)
@RunWith(SpringRunner.class)
public class BankControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BankService bankService;
	
	@MockBean
	UsersService usersService;
	
	@Test
	public void getBankAccountByUserIdTest() throws Exception {
		
		//GIVEN
		Users user2 = new Users(null, "Smith", "Wesson", "smithwesson@gmail.com", "1234");
		usersService.saveUser(user2);
		
		BankAccount bankAccount1 = new BankAccount(1L, 500.0, user2);
		
		BankDTO bankDTO = new BankDTO(bankAccount1.getId()
				, bankAccount1.getBalance()
				, bankAccount1.getUserId().getId()
				, bankAccount1.getUserId().getUsername());
		
	    //WHEN
	    when(bankService.getBankAccountDTOByUserId(1L)).thenReturn(bankDTO);

	    mockMvc.perform(get("/api/bankaccount/{userId}", 1)
			       .contentType(MediaType.APPLICATION_JSON))
			       .andExpect(status().isOk())
			       .andExpect(jsonPath("$.id", is(1)))
			       .andExpect(jsonPath("$.balance", is(bankAccount1.getBalance())))
			       .andExpect(jsonPath("$.userId", is(bankAccount1.getUserId().getId())))
			       .andExpect(jsonPath("$.username", is(bankAccount1.getUserId().getUsername())));
			    
	    verify(bankService, times(1)).getBankAccountDTOByUserId(1L);
		
	}
	
//	@Test
//	public void createBankAccountTest() throws Exception {
//		
//		//GIVEN
//		Users user1 = new Users(null, "Jojo", "affreux", "jojoaffreux@gmail.com", "1234");
//		usersService.saveUser(user1);
//		
//		BankAccount bankAccount1 = new BankAccount(2L, 500.0, user1);
//		bankService.createBankAccount(bankAccount1);
//		
//	    //WHEN
////	    when(bankService.createBankAccount(bankAccount1)).thenReturn();
//
//	    this.mockMvc.perform(post("http://localhost:8080/api/bankaccount/create")
//			       .contentType(MediaType.APPLICATION_JSON_VALUE)
//			       .content("{\"id\":2,\"balance\":500.0,\"userId\":1}"))
//			       .andExpect(status().isOk());
//			    
////	    verify(bankService, times(1)).createBankAccount(bankAccount1);
//		
//	}

}
