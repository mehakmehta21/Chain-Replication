package com.sbu.async;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbu.utils.OperationType;
import com.sbu.utils.Reply;
import com.sbu.utils.Request;
import com.sbu.utils.RequestHistory;
import com.sbu.utils.UDPClient;
import com.sbu.utils.UDPServer;


public class Client {	

	private static final Logger log = Logger.getLogger(Client.class.getName());
	private String TAG="";
	private String TAG_ERROR ="client_error : ";
	
    private final String MSTag = "MS"; // Master msg tag
	private final String NEWHEAD="NH";
	private final String NEWTAIL="NT";
	
	private  UDPServer clientUpdateReplyServer;
	String headServerIp;
	int headPort;
	String tailServerIp;
	int tailPort;
	int clientReplyPort;
	String clientIp;
	int retransmitTime;
	int retryCount;
	CopyOnWriteArrayList<RequestHistory> sentReqList= new CopyOnWriteArrayList<RequestHistory>();
	
	public Client(String bankname, int clientno)
	{
		this.TAG= bankname +"::client"+clientno+ ":: ";
		this.TAG_ERROR=TAG + "client_error : ";
	}
	
	public void init(String headServerIp, int headPort, String tailServerIp, int tailPort, int clientReplyPort, String clientIp, int retransmitTime, int retryCount)
	{
		this.headServerIp=headServerIp;
		this.headPort=headPort;
		this.tailServerIp=tailServerIp;
		this.tailPort=tailPort;
		this.clientReplyPort=clientReplyPort;
		this.clientIp=clientIp;
		
		this.retransmitTime=retransmitTime;
		this.retryCount=retryCount;
		log.debug(TAG + "Client Initiated with clientIp: "+clientIp+" clientPort: "+ clientReplyPort + " headServerIp: "+headServerIp+
				" headPort: "+headPort+ " tailServerIp: "+tailServerIp+" tailPort: "+tailPort);
		
		//initializeClientUDPSockets();
		initializeUDPUpdateReplyServer();
		checkRequest();
	}
	
	private void checkRequest()
	{
		Executors.newSingleThreadExecutor().execute(new Runnable(){
		    public void run(){
		        while(true)
		        {
		        	for (RequestHistory reqh : sentReqList) {
		        		  // System.out.println("reqid: "+reqh.getReqID());
		        			//log.debug(TAG+"reqid: "+reqh.getReqID());
		        		  // if(date.getTime()-reqh.getTime() > retransmitTime && reqh.getRetryCount()<retryCount && !reqh.isReplyReceived()) 
		        		   if(new Date().getTime()-reqh.getTime() > retransmitTime && reqh.getRetryCount()<retryCount && !reqh.isReplyReceived()) 
		        		   {
		        			   System.out.println("Retry the update for "+reqh.toString());
		        			   log.debug(TAG+"retry success");
		        			   log.debug(TAG+ "checkRequest "+ "Retry the update for "+reqh.toString());
		        			   if(reqh.getOperationType().equals(OperationType.QUERY))
		        				   queryBalance(reqh.getAccountNum(),reqh.getReqID(),false);
		        			   else
		        				   updateBalance(reqh.getAccountNum(),reqh.getAmount(),reqh.getReqID(),reqh.getOperationType(),false);
		        			   reqh.setRetryCount(reqh.getRetryCount()+1);
		        		   }
		        		}
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
		});
		
	}
	
	public  void printReply(String msg){
		ObjectMapper mapper = new ObjectMapper();
		Reply reply;
		if(msg.startsWith(MSTag))
		{
			log.debug(TAG + "Msg from Master : " + msg.trim());
			msg = msg.substring(2).trim();
			log.debug(TAG + msg);
			if(msg.startsWith(NEWHEAD))
			{
				msg=msg.substring(2);
				String data[]= msg.split(":");
				this.headServerIp=data[0];
				this.headPort=Integer.parseInt(data[1]);
				log.debug(TAG + "NewHead : " + headServerIp+":"+headPort);
			}
			else if(msg.startsWith(NEWTAIL))
			{
				msg=msg.substring(2);
				String data[]= msg.split(":");
				this.tailServerIp=data[0];
				this.tailPort=Integer.parseInt(data[1]);
				log.debug(TAG + "NewTail : " + tailServerIp+":"+tailPort);
			}
		}
		else
		{
			try {
				reply = mapper.readValue(msg, Reply.class);
				//System.out.println(TAG + "reply from bank : " + reply.toString());
				log.debug(TAG + "Reply from bank : " + reply.toString());
				//RequestHistory reqh=new RequestHistory(reply.getReqID());
				for (RequestHistory reqh : sentReqList) {
					if(reqh.getReqID().equals(reply.getReqID()))
					{
						reqh.setReplyReceived(true);
						//System.out.println("RequestHistory: Reply received for "+reqh.toString());
						//log.debug(TAG + "RequestHistory: Reply received for "+reqh.toString());
					}

				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	private  void initializeUDPUpdateReplyServer()
	{
		try {
			clientUpdateReplyServer = new UDPServer("My UDP server", "Example", InetAddress.getByName(clientIp), clientReplyPort,
			            0, 2, 2, this);
			clientUpdateReplyServer.Start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			log.debug(TAG_ERROR+"UDP server socket for receiving update reply failed..."+e.getMessage());
			//System.err.println(TAG_ERROR + " udp server socket for receiving update reply failed...");
		}
		catch (SocketException e) {
			// TODO Auto-generated catch block
			//System.err.println(TAG_ERROR + " udp server socket for receiving update reply failed...");
			log.debug(TAG_ERROR+"UDP server socket for receiving update reply failed..."+e.getMessage());
		}
		//System.out.println(TAG + " udp server socket for receiving update reply opened successfully...");
		log.debug(TAG + "UDP server socket for receiving update reply opened successfully...");
	}
	
	public void updateBalance(int accNo, float amt, String reqId, OperationType operationType,boolean isMessageLoss)
	{
		UDPClient headUDPClient;
		try {
			headUDPClient = new UDPClient("My UDP client", "Example", 0, 2,
			        2, InetAddress.getByName(headServerIp), headPort, this);
			headUDPClient.Start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.err.println(TAG_ERROR + " udp client socket for head failed...");
			log.debug(TAG_ERROR+ "UDP client socket for head failed..."+e.getMessage());
			return;
		}
		//System.out.println(TAG + " udp client socket for head opened successfully...");
		log.debug(TAG + "UDP client socket for head opened successfully..."+headServerIp+":"+headPort);
		 Request req = new Request();
		 req.setReplyPort(this.clientReplyPort);
		 req.setOperationType(operationType);
		 req.setReqID(reqId);
		 req.setAccountNum(accNo);
		 req.setAmount(amt);
	     ObjectMapper mapper = new ObjectMapper();
	      try {
			String sentence = mapper.writeValueAsString(req);
			
			if(!isMessageLoss)
			{
				headUDPClient.sendMessageToServer(sentence);
				log.debug(TAG + " sending update request : " + sentence);
			}
			else
			{
				log.debug(TAG + "Message Lost in sending request : " + sentence);
			}

			
			RequestHistory reqh=new RequestHistory(accNo,amt,reqId,operationType,new Date().getTime());
			if(!sentReqList.contains(reqh))
				sentReqList.add(reqh);
			
			//System.out.println(TAG + " sending update request : " + sentence);
		} 
	      catch (JsonProcessingException e) {
	    	  log.debug(TAG_ERROR + " sending update requst to bank failed..."+e.getMessage());
		}
	      
	}
	public void queryBalance(int accNo,String reqId, boolean isMessageLoss)
	{
		
		UDPClient tailUDPClient;
		try {
			tailUDPClient = new UDPClient("My UDP client", "Example", 0, 2,
			        2, InetAddress.getByName(tailServerIp), tailPort, this);
			tailUDPClient.Start();
		} catch (Exception e) {
			log.debug(TAG_ERROR + "UDP client socket tail failed..."+e.getMessage());
			return;
		}
		//System.out.println(TAG + " udp client socket for tail opened successfully...");
		log.debug(TAG + "UDP client socket for tail opened successfully..."+tailServerIp+":"+tailPort);
		
		Request req = new Request();
		 req.setReplyPort(this.clientReplyPort);
		 req.setOperationType(OperationType.QUERY);
		 req.setReqID(reqId);
		 req.setAccountNum(accNo);
		 //req.setAmount(100);
	     ObjectMapper mapper = new ObjectMapper();
	      try {
			String sentence = mapper.writeValueAsString(req);
			
			
			if(!isMessageLoss)
			{
				tailUDPClient.sendMessageToServer(sentence);
				log.debug(TAG + " sending query request : " + sentence);
			}
			else
			{
				log.debug(TAG + "Message Lost in sending request : " + sentence);
			}

			RequestHistory reqh=new RequestHistory(accNo,reqId,req.getOperationType(),new Date().getTime());
			if(!sentReqList.contains(reqh))
				sentReqList.add(reqh);
			//System.out.println(TAG + " Queried the bank " + sentence);
			log.debug(TAG + "Queried the bank " + sentence);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			//System.err.println(TAG_ERROR + " sending query request to bank failed...");
			log.debug(TAG_ERROR + " sending query request to bank failed..."+e.getMessage());
		}
	}

}
