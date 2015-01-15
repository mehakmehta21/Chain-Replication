package com.sbu.vo;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class Bank {

	private String bankName;
	private String head;
	private String tail;
	private int chainLength;
	private CopyOnWriteArrayList<ServerInfo> serverChain;
	private CopyOnWriteArrayList<ClientInfo> clientList;
	
	public CopyOnWriteArrayList<ClientInfo> getClientList() {
		return clientList;
	}
	public void setClientList(CopyOnWriteArrayList<ClientInfo> clientList) {
		this.clientList = clientList;
	}
	public int getChainLength() {
		return chainLength;
	}
	public void setChainLength(int chainLength) {
		this.chainLength = chainLength;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public CopyOnWriteArrayList<ServerInfo> getServerChain() {
		return serverChain;
	}
	
	public void setServerChain(CopyOnWriteArrayList<ServerInfo> serverChain) {
		this.serverChain = serverChain;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getTail() {
		return tail;
	}
	public void setTail(String tail) {
		this.tail = tail;
	}
	
	@Override
	public String toString() {
		return "Bank "+bankName +" [head=" +head+ " tail: "+tail+" chainLength: "+chainLength+ "]";
	}
	
	 public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        }

	        Bank bank = (Bank) obj;
	        return bankName.equals(bank.bankName);
	               
	    }
	
}
