package com.awmd.challenge.account;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Account.class)
public class AccountHandler {
	@Autowired
	private AccountRepository accountRepository;
	
	@HandleBeforeCreate
	public void handleAccountCreate(Account account) {
		account.setBalance(new BigDecimal("0"));
	}
	
	@HandleBeforeSave
	public void handleAccountSave(Account account) {
		Account old = accountRepository.findOne(account.getId());
		account.setBalance(old.getBalance());
	}

}
