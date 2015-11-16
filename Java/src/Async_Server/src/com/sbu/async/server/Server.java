package com.sbu.async.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.async.Account;
import com.sbu.async.Bank;
import com.sbu.async.OperationType;
import com.sbu.async.Outcome;
import com.sbu.async.Reply;
import com.sbu.async.Request;
import com.sbu.async.Role;
import com.sbu.async.ServerInfo;
import com.sbu.async.Transaction;
import com.sbu.async.tcpsocket.TCPChatClient;
import com.sbu.async.tcpsocket.TCPChatServer;
import com.sbu.async.udpsocket.UDPClient;
import com.sbu.async.udpsocket.UDPServer;

public class Server {
	
	private static final Logger log = Logger.getLogger(Server.class.getName());
	private String TAG="";
	private String TAG_ERROR ="server_error : ";
	
	private static final String HBMsg = "ping";
	private String bankname;
	private int serverno;
	private Role role=Role.NORMAL;
	private int lifeMsgs;
	private int tranfailMsgs=10000000;
	
	public static int replycount = 0;
	public static int updatereqcount = 0;
	private String myIP;
	private int myPort;
	private int myudpport;
	
	private static ClientInfo clientInfo;
	private UDPServer fUDPHeadServer;
	private UDPServer fUDPTailServer;
	private TCPChatClient tcpClientForSuccessor;
	private TCPChatClient tcpClientForChainTrans;
	private TCPChatServer tcpServerForPredecessor;
	private TCPChatClient tcpClientForMaster;
	private TCPChatClient tcpClientForMaster1;
	private List<Bank> bankList = new ArrayList<Bank>();
	private volatile List<Account> accountList = new ArrayList<Account>();
	private List<Transaction> pendingList = new ArrayList<Transaction>();
	private  List<Transaction> completedTransactionList = new ArrayList<Transaction>();
	
	//forwarded transaction list to next server in chain
	private List<Transaction> sentList = new ArrayList<Transaction>();
	//update
	private ServerInfo successor;
	private ServerInfo predecessor;
	private Transaction currTransaction;
	
	private final String ACKTag="AK";
	private final String NEWHEAD="NH";
	private final String NEWTAIL="NT";
	private final String NEWSUCCESSOR="NS";
	private final String NEWPREDECESSOR="NP";
	private final String SENTTRANSACTION= "ST";
	private final String CHAINEXTENSION = "CE";
	private final String UPDATECHAIN = "UC"; //send master message to update the chain
	private String SLTag = "SL"; // Suffix list tag
	private String CHAINTRANSACTION = "CT"; // Suffix list tag
	private String UPDATETAIL = "UT"; // update old tail with new tail
	private final String DELMTER = "-";
	private boolean hbFlag= true;

	
	public Server(String bankname, int serverno, int chainlength, String masterip, int masterport, int serverstartdelay, int life, String ip, int syncport, int udpport)
	{
		if(serverno==1)
			this.role=Role.HEAD;
		if(serverno==chainlength)
			this.role=Role.TAIL;
		
		this.bankname=bankname;
		this.serverno=serverno;
		this.lifeMsgs=life;
		
		this.myIP = ip;
		this.myPort = syncport;
		this.myudpport=udpport;
		
		this.TAG= bankname + "::" + role + "::server"+serverno+ ":: ";
		this.TAG_ERROR=TAG + "server_error : ";
		
		try
		{
			tcpClientForMaster=new TCPChatClient(InetAddress.getByName(masterip), masterport, "master");
			tcpClientForMaster.setBankServer(this);
			tcpClientForMaster.Start();
			
			tcpClientForMaster1=new TCPChatClient(InetAddress.getByName(masterip), masterport, "master");
			tcpClientForMaster1.setBankServer(this);
			tcpClientForMaster1.Start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(serverstartdelay>0)
		{
			try {
				Thread.sleep(serverstartdelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			chainExtension();
		}
		
		startHeartBeat();
	}
	
	
	private class ClientInfo{
		public int port;
		public int replyPort;
		public InetAddress inetAddress;
	}
	
	public synchronized Role getRole()
	{
		return role;
	}
	
	public synchronized void setIPPort(String ipAddr, int syncport)
	{
		this.myIP = ipAddr;
		this.myPort = syncport;
	}
	public synchronized void setIPPort(String ipAddr, int syncport,int udpport)
	{
		this.myIP = ipAddr;
		this.myPort = syncport;
		this.myudpport=udpport;
	}
	
	public synchronized void setTranfailMsgs(int tranfailMsgs)
	{
		this.tranfailMsgs=tranfailMsgs;
	}
	
	private void startHeartBeat()
	{
		Executors.newSingleThreadExecutor().execute(new Runnable(){
		    public void run(){
		        while(hbFlag)
		        {
		        	log.debug(TAG+hbFlag+" sending heart beat "+bankname+":"+serverno+":"+myIP+":"+myPort+":"+HBMsg);
		        	tcpClientForMaster.SendHeartBeat(bankname+":"+serverno+":"+myIP+":"+myPort+":"+HBMsg);
		        	try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    } 
		});	
	}
	
	private synchronized void chainExtension()
	{
		log.debug(TAG + "chainExtension");
		tcpClientForMaster1.SendDataToServer(CHAINEXTENSION+this.myIP+DELMTER+this.myPort+DELMTER+this.myudpport+DELMTER+this.bankname);
		return;
	}
	
	public synchronized void updateOldTail(String masterMsg)
	{
		log.debug(TAG + "updateOldTail-"+masterMsg);
		String info[]= masterMsg.split(DELMTER);
		ServerInfo nwTail= new ServerInfo();
		nwTail.setIpAddr(info[0]);
		nwTail.setPort(Integer.parseInt(info[1]));
		this.setRole(Role.NORMAL);
		this.setSuccessor(nwTail);
	}
	
	public synchronized void sendTransToNwTail(String masterMsg)
	{
		log.debug(TAG + "sendTransToNwTail-"+masterMsg);
		String info[]= masterMsg.split(DELMTER);
		ServerInfo nwTail= new ServerInfo();
		nwTail.setIpAddr(info[0]);
		nwTail.setPort(Integer.parseInt(info[1]));
		/*this.setRole(Role.NORMAL);
		this.setSuccessor(nwTail);*/
		
		try {
			tcpClientForChainTrans = new TCPChatClient(InetAddress.getByName(nwTail.getIpAddr()), nwTail.getPort(), "guest");
			tcpClientForChainTrans.setBankServer(this);
			tcpClientForChainTrans.Start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObjectMapper parser = new ObjectMapper();
		synchronized (parser) {
		
			if(completedTransactionList.size()==0)
				tcpClientForChainTrans.SendDataToServer(CHAINTRANSACTION+ completedTransactionList.size()+DELMTER+"null");
			
			for(int i=0;i<completedTransactionList.size();i++)
			{
				try {
					tcpClientForChainTrans.SendDataToServer(CHAINTRANSACTION+ completedTransactionList.size()+DELMTER + parser.writeValueAsString(completedTransactionList.get(i)));
					this.tranfailMsgs--;
					if(tranfailMsgs<=0)
					{
						log.debug(TAG+"sendTransToNwTail stopping the server tranfailMsgs: "+tranfailMsgs);
						stopServer();
						return;
					}
				}
				catch (JsonProcessingException e) 
				{
					log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
					e.printStackTrace();
					} 
			}
			log.debug(TAG + "send trans to new tail ");
		}
	}
	
	public synchronized void processChainTransaction(String msg)
	{
		log.debug(TAG + "processChainTransaction-"+msg);	
		String info[] = msg.split(DELMTER);
		int comTransSize = Integer.parseInt(info[0]);
		String transString = info[1];
		
		ObjectMapper mapper = new ObjectMapper();
		//ArrayList<Transaction> oldTailTransList=null;
		try {
			//completedTransactionList = mapper.readValue(msg, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Transaction.class));
			if(!transString.equals("null"))
			{
				log.debug(TAG + "processChainTransaction is not empty");
				Transaction trans= mapper.readValue(transString, Transaction.class);
				this.completedTransactionList.add(trans);
				lifeMsgs--;
				if(lifeMsgs<=0)
				{
					log.debug(TAG+"processChainTransaction stopping the server lifeMsgs: "+lifeMsgs);
					stopServer();
					return;
				}
			}
			if(this.completedTransactionList.size()==comTransSize)
			{
				this.init(Role.TAIL);
				tcpClientForMaster.SendDataToServer(UPDATECHAIN+this.myIP+DELMTER+this.myPort+DELMTER+this.myudpport+DELMTER+this.bankname);
				log.debug(TAG + "processChainTransaction send updatechain to master");
			}

			log.debug(TAG + "completedTransactionList list received from old tail and read");
		} 
		catch(JsonProcessingException e) {
			log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
			e.printStackTrace();
		}
		catch (IOException e) {
			log.debug(TAG_ERROR + "init error-"+e.getMessage());
		e.printStackTrace();
		}
	}
	
	public synchronized void processMasterMsg(String masterMsg)
	{
		log.debug(TAG + "processMasterMsg "+masterMsg);
		if(masterMsg.startsWith(NEWHEAD))
		{
			this.role=Role.HEAD;
			try {
				this.init(getRole());
			} catch (IOException e) {
				log.debug(TAG_ERROR + "head not initiated-"+e.getMessage());
				e.printStackTrace();
			}
		}
		else if(masterMsg.startsWith(NEWTAIL))
		{
			this.role=Role.TAIL;
			try {
				this.init(getRole());
			} catch (IOException e) {
				log.debug(TAG_ERROR + "tail not initiated-"+e.getMessage());
				e.printStackTrace();
			}
		}
		else if(masterMsg.startsWith(NEWSUCCESSOR))
		{
			log.debug(TAG + " processMasterMsg-newsuccessor msg received");
			ServerInfo nwsucc= new ServerInfo();
			String msginfo= masterMsg.substring(2);
			String info[]= msginfo.split(DELMTER);
			nwsucc.setIpAddr(info[0]);
			nwsucc.setPort(Integer.parseInt(info[1]));
			this.successor=nwsucc;
			this.setSuccessor(nwsucc);
			
			String predSentList = info[2];

			ObjectMapper mapper = new ObjectMapper();
			ArrayList<Transaction> succSentList=null;
			try {
				succSentList = mapper.readValue(predSentList, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Transaction.class));
				log.debug(TAG + "receiveSentList ");
			} catch(Exception e)
			{
				log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
				e.printStackTrace();
			}
			
			ObjectMapper parser = new ObjectMapper();
			ArrayList<Request> suffixSentList = new ArrayList<Request>();
			for(Transaction trans: this.sentList)
			{
				if(succSentList!=null && !succSentList.contains(trans))
				{
					Request req = new Request();
					req.setReqID(trans.getReqID());
					req.setAccountNum(trans.getAccount());
					req.setOperationType(trans.getOperation());
					req.setReplyPort(trans.getReplyPort());
					req.setAmount(trans.getAmount());
					suffixSentList.add(req);
				}

			}
			
			try {
				tcpClientForSuccessor.SendDataToServer(SLTag + parser.writeValueAsString(suffixSentList));
			} catch (JsonProcessingException e) {
				log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
				e.printStackTrace();
			}
			log.debug(TAG + " processMasterMsg-suffixSentList sent to successor");		
			
		}
		else if(masterMsg.startsWith(NEWPREDECESSOR))
		{
			log.debug(TAG + " processMasterMsg-newpredecessor msg received");
			ServerInfo nwpred= new ServerInfo();
			String msginfo= masterMsg.substring(2);
			String info[]= msginfo.split(":");
			nwpred.setIpAddr(info[0]);
			nwpred.setPort(Integer.parseInt(info[1]));
			this.predecessor=nwpred;
			//this.setPredecessor(nwpred);
			
			String failedServerIp= info[2];
			int failedServerPort =Integer.parseInt(info[3]);
	
			ObjectMapper parser = new ObjectMapper();
			synchronized (parser) {
				try {
					tcpClientForMaster.SendDataToServer(SENTTRANSACTION+failedServerIp+DELMTER+failedServerPort+DELMTER+nwpred.getIpAddr()
							+DELMTER+nwpred.getPort()+DELMTER+this.bankname+DELMTER
							+this.myIP+DELMTER+this.myPort+DELMTER							
							+parser.writeValueAsString(sentList));
				} catch (JsonProcessingException e) {
					System.out.println(TAG_ERROR + "request format is not correct");
					log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
				}
			}
			
		}
	}
	
	public synchronized void eventSLSync(String msg)
	{
		log.debug(TAG + " eventSLSync-suffixSentList received by successor "+ msg);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Request> suffixPredList=null;
		try {
			suffixPredList = mapper.readValue(msg, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Request.class));
			log.debug(TAG + "eventSLSync suffixPredList read ");
			
			for(Request req: suffixPredList)
			{
				eventSync(mapper.writeValueAsString(req));
			}
		} catch(Exception e)
		{
			log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	
	public synchronized void sendAckToPredecessor(Transaction transaction)
	{
		if(role == Role.HEAD)
			return;
		//System.out.println(TAG + "Sending ack to predecessor");
		log.debug(TAG + "Sending ack to predecessor-"+transaction.getReqID());
		ObjectMapper parser = new ObjectMapper();
		synchronized (parser) {
			try {
				tcpServerForPredecessor.sendAckToPredecessor(ACKTag+ parser.writeValueAsString(transaction));
			} catch (JsonProcessingException e) {
				System.out.println(TAG_ERROR + "request format is not correct");
				log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
				return;
			}
		}
		
	//	tcpServerForPredecessor.sendAckToPredecessor("ACK");
	}
	
	public synchronized void eventAck(String data)
	{
		log.debug(TAG + "Received ack from predecessor");
		
		if(data.startsWith(ACKTag))
		{
			ObjectMapper mapper = new ObjectMapper();
			Transaction trans = null;
			try {
				trans = mapper.readValue(data.substring(2), Transaction.class);
				log.debug(TAG + "Received ack from predecessor- ReqId: "+trans.getReqID());
			} catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(!completedTransactionList.contains(trans))
				completedTransactionList.add(trans);
			if(!sentList.contains(trans))
			{
				log.debug(TAG + "Removing from sentList ack from predecessor- ReqId: "+trans.getReqID());
				sentList.remove(trans);
			}

		}
		
		//if(!completedTransactionList.contains(currTransaction))
			//completedTransactionList.add(currTransaction);

		if(role == Role.HEAD)
		{
			log.debug(TAG + "Ack received at HEAD");
			return;
		}
		sendAckToPredecessor(currTransaction);
	}
	
	
	public synchronized void setSuccessor(ServerInfo successor)
	{
		if(role == Role.TAIL)
			return;
		this.successor = successor;
		try {
			tcpClientForSuccessor = new TCPChatClient(InetAddress.getByName(successor.ipAddr), successor.port, "guest");
			tcpClientForSuccessor.setBankServer(this);
			tcpClientForSuccessor.Start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public synchronized void setPredecessor(ServerInfo predecessor)
	{
		
		this.predecessor = predecessor;
		try {
			tcpServerForPredecessor = new TCPChatServer(InetAddress.getByName(myIP), myPort);
			tcpServerForPredecessor.setBankServer(this);
			tcpServerForPredecessor.Start();
		} catch (UnknownHostException e) {
			log.debug(TAG_ERROR + "setPredecessor failed-"+e.getMessage());
		}
		catch (Exception e) {
			log.debug(TAG_ERROR + "setPredecessor failed-"+e.getMessage());
		}
//		System.out.println(TAG + "TCPChatServer started " );
	}
	
	public synchronized Reply update(OperationType operation, String reqID, int accNo, int replyPort, List<?> list)
	{
		Reply reply;
		currTransaction = new Transaction();
		currTransaction.setReqID(reqID);
		currTransaction.setOperation(operation);
		currTransaction.setAccount(accNo);
		currTransaction.setReplyPort(replyPort);
		currTransaction.setAmount((Float) (list.get(0)));
		if(isInconsistentWithHistory(currTransaction))
		{
			//inconsistent with history 
			log.debug(TAG + "ReqId: "+reqID+"-Transaction is inconsistent with history ");
			reply=new Reply();
			reply.setReqID(reqID);
			reply.setOutcome(Outcome.INCONSISTENTWITHHISTORY);
			reply.setBalance(getAccount(accNo).getBalance());
			reply.setReplyPort(replyPort);
			currTransaction.setOutcome(reply.getOutcome());
			currTransaction.setBalance(reply.getBalance());
			return reply;
		}else if(isDuplicateRequest(currTransaction))
		{
			log.debug(TAG +"ReqId: "+reqID+ "-Duplicate transaction already processed ");
			reply=new Reply();
			reply.setReqID(reqID);
			reply.setOutcome(getDuplicateRequest(currTransaction).getOutcome());
			reply.setBalance(getDuplicateRequest(currTransaction).getBalance());
			reply.setReplyPort(replyPort);
			return reply;
		}
		pendingList.add(currTransaction);
		switch (operation) {
		case DEPOSIT:
			deposit(reqID, accNo, (Float) (list.get(0)));
			pendingList.remove(currTransaction);
			reply=new Reply();
			reply.setReqID(reqID);
			reply.setOutcome(Outcome.PROCESSED);
			reply.setBalance(getAccount(accNo).getBalance());
			reply.setReplyPort(replyPort);
			currTransaction.setOutcome(reply.getOutcome());
			currTransaction.setBalance(reply.getBalance());
			sentList.add(currTransaction);
			log.debug(TAG + "Adding to sentList- ReqId: "+currTransaction.getReqID());
			return reply;
		case WITHDRAW:
			Account account = getAccount(accNo);
			if(account == null)
			{
				account = createNewAccount(accNo);
				//reply = new Reply(reqID, Outcome.INSUFFICIENTFUNDS, getAccount(accNo).getBalance());
				reply=new Reply();
				reply.setReqID(reqID);
				reply.setOutcome(Outcome.INSUFFICIENTFUNDS);
				reply.setBalance(getAccount(accNo).getBalance());
				reply.setReplyPort(replyPort);
				currTransaction.setOutcome(reply.getOutcome());
				currTransaction.setBalance(reply.getBalance());
				sentList.add(currTransaction);
				log.debug(TAG + "Adding to sentList- ReqId: "+currTransaction.getReqID());
				return reply;
			}
			if(getAccount(accNo).getBalance()<(Float) (list.get(0)))
			{
				//reply = new Reply(reqID, Outcome.INSUFFICIENTFUNDS, getAccount(accNo).getBalance());
				reply=new Reply();
				reply.setReqID(reqID);
				reply.setOutcome(Outcome.INSUFFICIENTFUNDS);
				reply.setBalance(getAccount(accNo).getBalance());
				reply.setReplyPort(replyPort);
				currTransaction.setOutcome(reply.getOutcome());
				currTransaction.setBalance(reply.getBalance());
				sentList.add(currTransaction);
				log.debug(TAG + "Adding to sentList- ReqId: "+currTransaction.getReqID());
				return reply;
			}
			else
			{
				withdraw(reqID, accNo, (Float) (list.get(0)));
				pendingList.remove(currTransaction);
				//reply = new Reply(reqID, Outcome.PROCESSED, getAccount(accNo).getBalance());
				reply=new Reply();
				reply.setReqID(reqID);
				reply.setOutcome(Outcome.PROCESSED);
				reply.setBalance(getAccount(accNo).getBalance());
				reply.setReplyPort(replyPort);
				currTransaction.setOutcome(reply.getOutcome());
				currTransaction.setBalance(reply.getBalance());
				sentList.add(currTransaction);
				log.debug(TAG + "Adding to sentList- ReqId: "+currTransaction.getReqID());
				return reply;
			}
		default:
			break;
		}
		return null;
	}
	
	private synchronized boolean isDuplicateRequest(Transaction trans)
	{
		for (Transaction transaction : completedTransactionList) {
			if((trans.getAccount() == transaction.getAccount()) &&
					(trans.getOperation().equals(transaction.getOperation())) &&
					(trans.getReqID().equals(transaction.getReqID())) &&
					(trans.getAmount() == transaction.getAmount())
					)
			{
				return true;
			}
		}
		return false;
	}
	
	private synchronized Transaction getDuplicateRequest(Transaction trans)
	{
		for (Transaction transaction : completedTransactionList) {
			if((trans.getAccount() == transaction.getAccount()) &&
					(trans.getOperation().equals(transaction.getOperation())) &&
					(trans.getReqID().equals(transaction.getReqID())) &&
					(trans.getAmount() == transaction.getAmount())
					)
			{
				return transaction;
			}
		}
		return null;
	}
	
	private synchronized void deposit(String reqID,int accountNum, float amount)
	{
//		System.out.println(TAG + "deposit method called..");
		Account account = getAccount(accountNum);
		if(account == null)
		{
			account = createNewAccount(accountNum);
		}
		account.setBalance(account.getBalance() + amount);
		//System.out.println("Server: deposit complete new balance is : " + account.getBalance());
		log.debug(TAG +"ReqId: "+reqID+ "-Deposit operation complete new balance is : " + account.getBalance());
		
	}
	
	private synchronized boolean withdraw(String reqID,int accountNum, float amount)
	{
		Account account = getAccount(accountNum);
		account.setBalance(account.getBalance() - amount);
		//System.out.println("Server: withdraw complete new balance is : " + account.getBalance());
		log.debug(TAG +"ReqId: "+reqID+ "-Withdraw operation complete new balance is : " + account.getBalance());
		return true;
	}
	
	private synchronized void sendSyncToSuccessor(Request req) {
		
		//decrement counter of messages
		
		if(role == Role.TAIL)
		{
			//System.out.println("I am tail sending reply to client and ack to predecessor");
			log.debug(TAG +"ReqId: "+req.getReqID() + "Reply to client and ack to predecessor-ReqId: "+req.getReqID());
			//sendAckToPredecessor();
			Reply reply = new Reply();
			reply.setReqID(req.getReqID());
			reply.setOutcome(Outcome.PROCESSED);
			reply.setBalance(getAccount(req.getAccountNum()).getBalance());
			reply.setReplyPort(req.getReplyPort());
			replyToClient(reply);
			return;
		}

		log.debug(TAG+"ReqId: "+req.getReqID() + "-Sendin sync to successor");
		
		//send sync to sucessor
		ObjectMapper parser = new ObjectMapper();
		synchronized (parser) {
			try {
				tcpClientForSuccessor.SendDataToServer(parser.writeValueAsString(req));
			} catch (JsonProcessingException e) {
				System.out.println(TAG_ERROR + "request format is not correct");
				log.debug(TAG_ERROR + "request format is not correct-"+e.getMessage());
				return;
			}
		}
		
	}

	private synchronized void replyToClient(Reply reply) {
		log.debug(TAG +" ReqId: "+reply.getReqID()+"Reply to client : "+ clientInfo.inetAddress + " port:" + reply.getReplyPort());
		UDPClient fUDPClient;
		try {
			fUDPClient = new UDPClient("My UDP client", "Example", 0, 2,
			        2, clientInfo.inetAddress, reply.getReplyPort());
			fUDPClient.Start();
			 ObjectMapper mapper = new ObjectMapper();
		      try {
				String sentence = mapper.writeValueAsString(reply);
				//mapper.writeValueAsBytes(arg0)
				fUDPClient.sendMessageToServer(sentence);
			} catch (JsonProcessingException e) {
				log.debug(TAG_ERROR + "Sending query request to bank failed..." + e.getMessage());
			}
			
//			fUDPClient.Stop();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(TAG + " replyToClient success");
		log.debug(TAG + "ReplyToClient success");
	}

	private synchronized boolean isInconsistentWithHistory(Transaction trans)
	{
		for (Transaction transaction : completedTransactionList) {
			if((trans.getReqID().equals(transaction.getReqID())
					&& (!(trans.getOperation().equals(transaction.getOperation())) | 
						(trans.getAccount() != transaction.getAccount()) |
						trans.getAmount() != transaction.getAmount())))
				return true;
		}
		//similarly check for pending list also
		return false;
	}
	
	
	private synchronized Account createNewAccount(int accountNum)
	{
		Account account = new Account();
		account.setAccountNum(accountNum);
		account.setBalance(0);
		accountList.add(account);
		log.debug(TAG + "New Account created with account no : " + accountNum);
		return account;
	}
	private synchronized Account getAccount(int accountNum)
	{
		for (Account account : accountList) {
			if(account.getAccountNum() == accountNum)
				return account;
		}
		return null;
	}
	
	public void setRole(Role role)
	{
		this.role = role;
		this.TAG= bankname + "::" + role + "::server"+serverno+ ":: ";
	}
	
	private synchronized Reply processRequest(Request req)
	{
		log.debug(TAG + "ProcessRequest method called.."+req.getReqID());
		Vector<Float> v = new Vector<Float>();
		float amount = req.getAmount();
        v.add(amount);
        return update(req.getOperationType(), req.getReqID(), req.getAccountNum(), req.getReplyPort() ,v);
	}
	
	public synchronized void eventQuery(InetAddress clientInetAddress, int clientPort, String args)
	{
		//decrement counter of messages
		lifeMsgs--;
		log.debug(TAG +"sendSyncToSuccessor - lifeMsgs: "+lifeMsgs);
		//crash the server by stopping connections
		if(lifeMsgs<=0)
		{
			log.debug(TAG+"eventQuery stopping the server lifeMsgs: "+lifeMsgs);
			stopServer();
			return;
		}
		
		log.debug(TAG + "Received client Query request.."+ clientInetAddress.getHostAddress() + " port " + clientPort);
		if(role != Role.TAIL)
			return;
		ObjectMapper mapper = new ObjectMapper();
		Request req = null;
		try {
			req = mapper.readValue(args, Request.class);
			log.debug(TAG + "Received client Query reqId"+ req.getReqID());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			//System.out.println(TAG_ERROR + "request format is not correct");
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
			return;
//			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			//System.out.println(TAG_ERROR + "request format is not correct");
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
//			e.printStackTrace();
			return;
		} catch (IOException e) {

			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
//			e.printStackTrace();
			return;
		}
		if(req.getOperationType() != OperationType.QUERY)
			return;
		Reply reply;
		Account account = getAccount(req.getAccountNum());
		if(account == null)
		{
			log.info(TAG + "tail account is null");
			account = createNewAccount(req.getAccountNum());
		}
		reply = new Reply();
		reply.setReqID(req.getReqID());
		reply.setOutcome(Outcome.PROCESSED);
		reply.setBalance(account.getBalance());
		reply.setReplyPort(req.getReplyPort());

		UDPClient fUDPClient;
		try {
			fUDPClient = new UDPClient("My UDP client", "Example", 0, 2,
			        2, clientInetAddress, req.getReplyPort());
			fUDPClient.Start();
			fUDPClient.sendMessageToServer(mapper.writeValueAsString(reply));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(TAG + "Client qurey replied..");
		log.debug(TAG+  "Client qurey replied..");
		

	}
	
	public void stopServer()
	{
		log.debug(TAG+"stop called "+this.myIP+":"+this.myPort);
		this.hbFlag=false;
		if(fUDPHeadServer!=null)
			fUDPHeadServer.Stop();
		if(fUDPTailServer!=null)
			fUDPTailServer.Stop();
		if(tcpClientForSuccessor!=null)
			tcpClientForSuccessor.Stop();
		if(tcpServerForPredecessor!=null)
			tcpServerForPredecessor.Stop();
		if(tcpClientForMaster!=null)
			tcpClientForMaster.Stop();
	}
	
	public synchronized void eventUpdate(InetAddress clientInetAddress, int clientPort, String args)
	{
		lifeMsgs--;
		log.debug(TAG +"sendSyncToSuccessor - lifeMsgs: "+lifeMsgs);
		//crash the server by stopping connections
		if(lifeMsgs<=0)
		{
			log.debug(TAG+"eventQuery stopping the server lifeMsgs: "+lifeMsgs);
			stopServer();
			return;
		}
		
		log.debug(TAG + "Received client Update request.." + clientInetAddress.getHostAddress() + " port " + clientPort);
		ObjectMapper mapper = new ObjectMapper();
		clientInfo = new ClientInfo();
		clientInfo.inetAddress = clientInetAddress;
		clientInfo.port = clientPort;
		clientInfo.replyPort=clientPort;
		try {
			Request req = mapper.readValue(args, Request.class);
			Reply reply = processRequest(req);
			log.debug(TAG + "Received Request No -"+ req.getReqID());
			sendSyncToSuccessor(req);
		} catch (JsonParseException e) {
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
		} catch (JsonMappingException e) {
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
//			e.printStackTrace();
		} catch (IOException e) {
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
//			e.printStackTrace();
		}
		
	}
	
	
	public synchronized void eventSync(String args)
	{
		lifeMsgs--;
		log.debug(TAG +"sendSyncToSuccessor - lifeMsgs: "+lifeMsgs);
		//crash the server by stopping connections
		if(lifeMsgs<=0)
		{
			log.debug(TAG+"eventSync stopping the server lifeMsgs: "+lifeMsgs);
			stopServer();
			return;
		}
		
		//System.out.println(TAG + "sync from predecessor " + args);
		log.debug(TAG + "Sync from predecessor " + args);
		ObjectMapper parser = new ObjectMapper();
		Request req ;
		Reply reply = null;
		try {
			req = parser.readValue(args, Request.class);
			reply = processRequest(req);
				
		} catch (JsonParseException e) {

			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
			return;
//			e.printStackTrace();
		} catch (JsonMappingException e) {
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
			return;
//			e.printStackTrace();
		} catch (IOException e) {
			log.debug(TAG_ERROR + "Request format is not correct-"+e.getMessage());
			return;
//			e.printStackTrace();
		}
		
		if(role == Role.TAIL)
		{
			log.debug(TAG +" ReqId: "+reply.getReqID()+" Sending reply to client and ack to predecessor");
			if(!completedTransactionList.contains(currTransaction))
				completedTransactionList.add(currTransaction);
			replyToClient(reply);
			sendAckToPredecessor(currTransaction);
		}
		else
		{
			//send sync request to sucessor
			sendSyncToSuccessor(req);
		}
	}
	

	private void openUDPServerForClientUpdateRequest()
	{
		System.out.println(TAG + "HEAD::udp for client started at ip " +  myIP + " port: " + (this.myudpport));
		log.debug(TAG + "HEAD::udp for client started at ip " +  myIP + " port: " + (this.myudpport));
		try {
			fUDPHeadServer = new UDPServer("My UDP server", "Example", InetAddress.getByName(myIP), this.myudpport,
			            0, 2, 2);
			fUDPHeadServer.setBankServer(this);
			fUDPHeadServer.Start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(TAG_ERROR + " udp server at ip " +  myIP + " port: " + this.myudpport + " failed-" + e.getMessage());
		}
		catch (SocketException e) {
			// TODO Auto-generated catch block
			log.debug(TAG_ERROR + " udp server at ip " +  myIP + " port: " + this.myudpport + " failed-" + e.getMessage());
			e.printStackTrace();
		}
//		System.out.println(TAG + "openUDPServerForClient end" );
	}
	
	private void openUDPServerForClientQueryRequest()
	{
		try {
			fUDPTailServer = new UDPServer("My TAIL UDP server", "For Query Request", InetAddress.getByName(myIP), this.myudpport,
			            0, 2, 2);
			fUDPTailServer.setBankServer(this);
			fUDPTailServer.Start();
			//System.out.println(TAG + "TAIL: query :: udp server at ip " +  myIP + " port: " + (myPort + 200) + " started..");
			log.debug(TAG + "Udp server at ip " +  myIP + " port: " + this.myudpport + " started..");
		} catch (UnknownHostException e) {
			//System.err.println(TAG_ERROR + "TAIL: query :: udp server at ip " +  myIP + " port: " + (myPort + 200) + " failed");
			log.debug(TAG_ERROR + " udp server at ip " +  myIP + " port: " + this.myudpport + " failed-" + e.getMessage());
		}
		catch (SocketException e) {
			log.debug(TAG_ERROR + " udp server at ip " +  myIP + " port: " + this.myudpport + " failed-" + e.getMessage());
		}
	}
	
	public void init(Role role) throws IOException {
		//System.out.println(TAG + " role " + role);
		log.debug(TAG + " Server started as " + role);
		setRole(role);
		if(role == Role.HEAD)
		{
			openUDPServerForClientUpdateRequest();
		}
		else if(role == Role.TAIL)
		{
			//open UDP connection for query request
			openUDPServerForClientQueryRequest();
		}
	}
}
