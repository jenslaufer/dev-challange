package com.awmd.challenge;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.awmd.challenge.account.Account;
import com.awmd.challenge.account.AccountRepository;

@Component
public class DatabaseFillerOnStartup  {

	@Autowired
	private AccountRepository accountRepository;

    @EventListener
    public void handleContextStart(ApplicationReadyEvent event) {
		Account bank = new Account();
		bank.setName("Bank");
		bank.setBalance(new BigDecimal(10000000));
		accountRepository.save(bank);
    }

}