package com.awmd.challenge.transaction;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.awmd.challenge.account.Account;
import com.awmd.challenge.account.AccountRepository;
import com.awmd.challenge.account.OverDraftException;

@Component
@RepositoryEventHandler(Transaction.class)
public class TransactionHandler {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;

	@HandleBeforeCreate
	@Transactional
	public void handleBeforeCreate(Transaction transaction)
			throws NegativeAmountTransactionException, OverDraftException {
		if (transaction.getAmount().doubleValue() < 0) {
			throw new NegativeAmountTransactionException();
		}

		Account source = accountRepository.findOne(new Long(transaction.getSource()));
		Account target = accountRepository.findOne(new Long(transaction.getTarget()));

		double sourceBalance = source.getBalance().subtract(transaction.getAmount()).doubleValue();
		if (sourceBalance < 0) {
			throw new OverDraftException(source);
		}
		double targetBalance = target.getBalance().add(transaction.getAmount()).doubleValue();

		source.setBalance(new BigDecimal(sourceBalance));
		target.setBalance(new BigDecimal(targetBalance));
		accountRepository.save(source);
		accountRepository.save(target);
	}

	@HandleBeforeSave
	public void handleBeforeSave(Transaction transaction) {
		transaction = transactionRepository.findOne(transaction.getId());
	}

}