package com.awmd.challenge.transaction;

import com.awmd.challenge.account.Account;

public class OverDraftException extends Exception {

	private Account account;

	public OverDraftException(Account source) {
		this.account = source;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OverDraftException [account=" + account + "]";
	}

}
