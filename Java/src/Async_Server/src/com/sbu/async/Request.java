package com.sbu.async;



public class Request 
{
	OperationType operationType;
	String reqID;
	int accountNum;
	float amount;
	int replyPort;
	//String destBank;
	//int destAccount;
	
	public int getReplyPort() {
		return replyPort;
	}
	public void setReplyPort(int replyPort) {
		this.replyPort = replyPort;
	}
	public String getReqID() {
		return reqID;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public void setReqID(String reqID) {
		this.reqID = reqID;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(int accountNum) {
		this.accountNum = accountNum;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Request "+operationType +" [reqID=" +reqID+ " accountNum: "+accountNum+" amount"+amount+ "]";
	}
	
	 public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        }

	        Reply reply = (Reply) obj;
	        return reqID.equals(reply.reqID);
	               
	    }
	
}
