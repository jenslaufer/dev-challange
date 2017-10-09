package com.awmd.challenge.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.awmd.challenge.account.Account;
import com.awmd.challenge.account.AccountRepository;


@Component
@RepositoryEventHandler(Transaction.class)
public class TransactionHandler {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;

	@HandleBeforeCreate
	@Transactional
	public void handleBeforeCreate(Transaction transaction) {
		Account source = accountRepository.findOne(new Long(transaction.getSource()));
		Account target = accountRepository.findOne(new Long(transaction.getTarget()));
		source.setBalance(source.getBalance().subtract(transaction.getAmount()));
		target.setBalance(target.getBalance().add(transaction.getAmount()));
		accountRepository.save(source);
		accountRepository.save(target);
	}

	@HandleBeforeSave
	public void handleBeforeSave(Transaction transaction) {
		transaction = transactionRepository.findOne(transaction.getId());
	}
	
}