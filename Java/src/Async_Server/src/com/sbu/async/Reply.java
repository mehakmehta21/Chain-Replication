package com.sbu.async;

import java.io.Serializable;


public class Reply{
	  /**
	 * 
	 */
	String reqID;
	  Outcome outcome;
	  float balance;
	  int replyPort;
	  String ip;
	  
	
	 public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getReplyPort() {
		return replyPort;
	}
	public void setReplyPort(int replyPort) {
		this.replyPort = replyPort;
	}
	/* public Reply(String reqID, Outcome outcome, float balance)
	  {
	    this.reqID = reqID;
	    this.outcome = outcome;
	    this.balance = balance;
	  }*/
	  public String getReqID() {
			return reqID;
		}
		public void setReqID(String reqID) {
			this.reqID = reqID;
		}
		public Outcome getOutcome() {
			return outcome;
		}
		public void setOutcome(Outcome outcome) {
			this.outcome = outcome;
		}
		public float getBalance() {
			return balance;
		}
		public void setBalance(float balance) {
			this.balance = balance;
		}
	  
	  public String toString()
	  {
	   String str = "reqID " + this.reqID + " outcome " + this.outcome + " balance " + balance;
	   return str;
	  }
	  
}