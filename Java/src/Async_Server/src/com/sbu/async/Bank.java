package com.sbu.async;

import java.util.ArrayList;

import com.sbu.async.server.Server;


public class Bank {

	private String bankName;
	private String head;
	private String tail;
	private int chainLength;
	private ArrayList<Server> serverChain;
	
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
	
	public ArrayList<Server> getServerChain() {
		return serverChain;
	}
	
	public void setServerChain(ArrayList<Server> serverChain) {
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
	
	
}
