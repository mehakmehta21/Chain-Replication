package com.sbu.utils;

import java.sql.Timestamp;

public class RequestHistory
{
	OperationType operationType;
	String reqID;
	int accountNum;
	float amount;
	long time;
	int retryCount=0;
	boolean replyReceived=false;
	
	public RequestHistory(int accNo, float amt, String reqId, OperationType operationType, long time)
	{
		this.accountNum=accNo;
		this.amount=amt;
		this.reqID=reqId;
		this.operationType=operationType;
		this.time=time;
		this.retryCount=0;
	}
	public RequestHistory(int accNo, String reqId,OperationType operationType, long time)
	{
		this.accountNum=accNo;
		this.reqID=reqId;
		this.operationType=operationType;
		this.time=time;
		this.retryCount=0;
	}
	
	public RequestHistory(String reqId)
	{
		this.reqID=reqId;
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
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public boolean isReplyReceived() {
		return replyReceived;
	}

	public void setReplyReceived(boolean replyCame) {
		this.replyReceived = replyCame;
	}

	@Override
	public String toString() {
		return "RequestHistory "+operationType +" [reqID=" +reqID+ " accountNum: "+accountNum+" amount"+amount+" time "+time+
				" replyReceived:"+replyReceived+" retryCount "+retryCount +" "+"]";
	}
	
	 public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        }

	        RequestHistory reqh = (RequestHistory) obj;
	        return reqID.equals(reqh.reqID);
	               
	    }
	
	
}
