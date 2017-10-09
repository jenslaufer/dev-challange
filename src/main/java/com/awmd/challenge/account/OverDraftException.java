package com.awmd.challenge.account;

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
