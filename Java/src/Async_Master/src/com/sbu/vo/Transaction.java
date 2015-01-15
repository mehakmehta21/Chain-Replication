package com.sbu.vo;



public class Transaction {
	String reqID;
	int accountNum;
	OperationType operation;
	float amount;
	Outcome outcome;
	float balance;
	int replyPort;
	
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public int getReplyPort() {
		return replyPort;
	}
	public void setReplyPort(int replyPort) {
		this.replyPort = replyPort;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Outcome getOutcome() {
		return outcome;
	}
	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}
	public String getReqID() {
		return reqID;
	}
	public void setReqID(String reqID) {
		this.reqID = reqID;
	}
	public int getAccount() {
		return accountNum;
	}
	public void setAccount(int accountNum) {
		this.accountNum = accountNum;
	}
	public OperationType getOperation() {
		return operation;
	}
	public void setOperation(OperationType operation) {
		this.operation = operation;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}	
	
	 public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        }

	        Transaction trans = (Transaction) obj;
	        return reqID.equals(trans.reqID) && (accountNum == trans.accountNum) && operation.equals(trans.operation) && (amount==trans.amount);
	               
	    }
	
}
