package com.sbu.master;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import sbu.async.udpsocket.UDPClient;

import com.sbu.async.tcpsocket.TCPChatClient;
import com.sbu.async.tcpsocket.TCPChatServer;
import com.sbu.vo.Bank;
import com.sbu.vo.ClientInfo;
import com.sbu.vo.Role;
import com.sbu.vo.ServerInfo;

public class Master 
{
	private static final Logger log = Logger.getLogger(Master.class.getName());
	private static final String TAG= "Master ";
	
	private String ip;
	private int port;
	private long timeout;
	private TCPChatClient tcpClient;
	private TCPChatClient tcpClient1=null;
	private TCPChatServer tcpServer;
	UDPClient fUDPClient;
	private HashMap<String, Bank> bankList;
	private ArrayList<ServerInfo> pendingList= new ArrayList<ServerInfo>();
	private HashMap<String,ServerInfo> chainExtpendingList= new HashMap<String, ServerInfo>();
	
	private boolean check;
	
	private final String MSTag = "MS"; // Master msg tag
	private final String NEWHEAD="NH";
	private final String NEWTAIL="NT";
	private final String NEWSUCCESSOR="NS";
	private final String NEWPREDECESSOR="NP";
	private final String SENTTRANSACTION= "ST";
	private final String CHAINEXTENSION = "CE";
	private String UPDATETAIL = "UT"; // update old tail with new tail
	private final String DELMTER = "-";

	
	public Master(String ip, int port,long timeout, HashMap<String, Bank> bankList)
	{
		this.ip=ip;
		this.port=port;
		this.timeout=timeout;
		this.setBankList(bankList);
		try {

			tcpServer = new TCPChatServer(InetAddress.getByName(this.ip), this.port);
			tcpServer.setMaster(this);
			tcpServer.Start();
			log.debug(TAG+" master tcp server started ip: "+ this.ip+" port: "+this.port);
			checkServerFailure(check);
	
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void checkServerFailure(boolean check)
	{
		log.debug(TAG + "checkServerFailure");
		this.check=check;
		Executors.newSingleThreadExecutor().execute(new Runnable(){
		    public void run()
		    {
		        while(true)
		        {
		        	for (String bname : bankList.keySet()) 
		        	{
		        		CopyOnWriteArrayList<ServerInfo> serverChain=bankList.get(bname).getServerChain();
		        		for (ServerInfo s : serverChain)
		        		{
		        			//log.debug(TAG+"bname: "+bname+" ip="+s.getIpAddr()+":"+s.getPort()+" timestamp:"+ s.getTimestamp()+" currenttime:"+new Date().getTime());
		        			if((s.getTimestamp() > 0) && (new Date().getTime()-s.getTimestamp() > timeout) && s.isServerUp())
		        			{
		        				s.setServerUp(false);
		        				serverFailed(bname, s);
		        			}
		        		}
		        	}
		        	try { Thread.sleep(1000);	} 
		        	catch (InterruptedException e) {e.printStackTrace();}
		        	
		        }
		    }
		});
	}

	private synchronized void serverFailed(String bname, ServerInfo s)
	{
		log.debug(TAG + bname + " serverFailed: ");
		bankList.get(bname).setChainLength(bankList.get(bname).getChainLength()-1);
		
		
		if(s.getRole()==Role.HEAD)
		{
			log.debug(TAG +bname+ " head server failed ip "+s.getIpAddr()+":"+s.getPort());
			ServerInfo nwhead = s.getSuccessor();
			 //save new head in bank bn chain
			//s.getSuccessor().setRole(Role.HEAD);
			//s.getSuccessor().setPredecessor(null);
			
			try {
				//inform server his new role as head
				tcpClient = new TCPChatClient(InetAddress.getByName(nwhead.getIpAddr()), nwhead.getPort(), "server");
				tcpClient.setMasterServer(this);
				tcpClient.Start();
				try { Thread.sleep(100);	} 
	        	catch (InterruptedException e) {e.printStackTrace();}
				
				tcpClient.SendDataFromMaster(NEWHEAD);
				log.debug(TAG +"data sent to ip: "+nwhead.getIpAddr()+" port: "+nwhead.getPort());

				//tcpClient.Stop();
				
				//send client do later - inform client about new head of the bank bn
				for(ClientInfo clnt: bankList.get(bname).getClientList())
				{
					fUDPClient = new UDPClient("My UDP client", "Example", 0, 2, 2, InetAddress.getByName(clnt.getClientIp()), clnt.getClientPort());
					fUDPClient.Start();
					fUDPClient.sendMessageToServer(MSTag+NEWHEAD+nwhead.getIpAddr()+":"+nwhead.getUdpPort());
					log.debug(TAG +"client data sent to : "+clnt.getClientIp()+" port: "+clnt.getClientPort());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//remove server from chain
			for (ServerInfo serv : bankList.get(bname).getServerChain())
			{
				if(serv.equals(nwhead))
				{
					serv.setPredecessor(null);
					serv.setRole(Role.HEAD);
				}
			}
			
			bankList.get(bname).getServerChain().remove(s);
			System.out.println(bankList.get(bname).getServerChain());
		}	
		else if (s.getRole()==Role.TAIL)
		{
			log.debug(TAG +bname+ " tail server failed ip "+s.getIpAddr()+":"+s.getPort());
			ServerInfo nwtail = s.getPredecessor();
			//s.getPredecessor().setRole(Role.TAIL);
			//s.getPredecessor().setSuccessor(null);

			try {
				//inform server his new role as tail
				tcpClient = new TCPChatClient(InetAddress.getByName(nwtail.getIpAddr()), nwtail.getPort(), "server");
				tcpClient.setMasterServer(this);
				tcpClient.Start();
				try { Thread.sleep(500);	} 
	        	catch (InterruptedException e) {e.printStackTrace();}
				tcpClient.SendDataFromMaster(NEWTAIL);
				//tcpClient.Stop();
				for(ClientInfo clnt: bankList.get(bname).getClientList())
				{
					fUDPClient = new UDPClient("My UDP client", "Example", 0, 2, 2, InetAddress.getByName(clnt.getClientIp()), clnt.getClientPort());
					fUDPClient.Start();
					fUDPClient.sendMessageToServer(MSTag+NEWTAIL+nwtail.getIpAddr()+":"+nwtail.getUdpPort());
					log.debug(TAG +"client data sent to : "+clnt.getClientIp()+" port: "+clnt.getClientPort());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//remove server from chain

			for (ServerInfo serv : bankList.get(bname).getServerChain())
			{
				if(serv.equals(nwtail))
				{
					serv.setSuccessor(null);
					serv.setRole(Role.TAIL);
				}
			}
			
			bankList.get(bname).getServerChain().remove(s);
			
			
			//chain extension curr tail fails
			if(chainExtpendingList.containsKey(s.getIpAddr()+s.getPort()))
			{
				ServerInfo nwTail= chainExtpendingList.get(s.getIpAddr()+s.getPort());
				log.debug(TAG +"checkServerFailure- curr tails fails while extension is going on "+s.getIpAddr()+s.getPort());
				this.receiveChainExt(nwTail.getIpAddr()+DELMTER+nwTail.getPort()+DELMTER+nwTail.getUdpPort()+DELMTER+bname);
				chainExtpendingList.remove(s.getIpAddr()+s.getPort());
				
			}
			
		}
		else
		{
			log.debug(TAG +bname+ " normal server failed ip "+s.getIpAddr()+":"+s.getPort());
			ServerInfo succ = s.getSuccessor();
			ServerInfo pred = s.getPredecessor();
			//s.getPredecessor().setSuccessor(succ);
			//s.getSuccessor().setPredecessor(pred);
			
			try {
				//inform server his new role 
				tcpClient = new TCPChatClient(InetAddress.getByName(succ.getIpAddr()), succ.getPort(), "server1");
				tcpClient.setMasterServer(this);
				tcpClient.Start();
				try { Thread.sleep(1000);	} 
	        	catch (InterruptedException e) {e.printStackTrace();}
				
				tcpClient.SendDataFromMaster(NEWPREDECESSOR+pred.getIpAddr()+":"+pred.getPort()+":"+s.getIpAddr()+":"+s.getPort());
				log.debug(TAG +"data sent to ip: "+succ.getIpAddr()+" port: "+succ.getPort());
				//tcpClient.Stop();
			
				//put it on pending list of master to resolve
				pendingList.add(s);
			
				log.debug(TAG +bname+ " remove server from chain"+s.getIpAddr()+":"+s.getPort()+" start");
				for (ServerInfo ser : bankList.get(bname).getServerChain())
				{
					if(ser.equals(s))
					{
						s = ser;
					}
					else if(ser.equals(pred))
					{
						ser.setSuccessor(succ);
					}
					else if(ser.equals(succ))
					{
						ser.setPredecessor(pred);
					}
					
				}
				
				//remove server from chain
				bankList.get(bname).getServerChain().remove(s);
				log.debug(TAG +bname+ " normal server failed ip "+s.getIpAddr()+":"+s.getPort()+" list updated");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//return s;
	}
	
	public synchronized void receiveChainExt(String msg)
	{
		log.debug(TAG + "receiveChainExt- " + msg );
		String info[]= msg.split(DELMTER);
		ServerInfo nwTail = new ServerInfo();
		nwTail.setIpAddr(info[0]);
		nwTail.setPort(Integer.parseInt(info[1]));
		nwTail.setUdpPort(Integer.parseInt(info[2]));
		//String nwTailIp= info[0];
		//int nwTailPort = Integer.parseInt(info[1]);
		//int nwTailUdpPort = Integer.parseInt(info[2]);
		String bname = info[3];
		
		ServerInfo currTail = null;
		for (ServerInfo serv : bankList.get(bname).getServerChain())
		{
			if(serv.getRole().equals(Role.TAIL))
			{
				currTail=serv;
			}
		}
		
		try {
			log.debug(TAG + "predServerIp:"+currTail.getIpAddr()+" predServerPort:"+currTail.getPort());
			tcpClient1 = new TCPChatClient(InetAddress.getByName(currTail.getIpAddr()), currTail.getPort(), "currTail");
			tcpClient1.setMasterServer(this);
			tcpClient1.Start();
		
			tcpClient1.SendDataFromMaster(CHAINEXTENSION + nwTail.getIpAddr() +DELMTER + nwTail.getPort() + DELMTER +nwTail.getUdpPort());
			
			log.debug(TAG + "receiveChainExt- newtail info sent to currtail");
			
			if(!chainExtpendingList.containsKey(currTail.getIpAddr()+currTail.getPort()))
			{
				chainExtpendingList.put(currTail.getIpAddr()+currTail.getPort(),nwTail);
				log.debug(TAG + "receiveChainExt- chainExtpendingList add "+currTail.getIpAddr()+currTail.getPort());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void updateChainExtToClients(String msg)
	{
		log.debug(TAG + "updateChainExtToClients- " + msg );
		String info[]= msg.split(DELMTER);
		String nwTailIp= info[0];
		int nwTailPort = Integer.parseInt(info[1]);
		int nwTailUdpPort = Integer.parseInt(info[2]);
		String bname = info[3];
		
		ServerInfo nwTail = new ServerInfo();
		nwTail.setIpAddr(nwTailIp);
		nwTail.setPort(nwTailPort);
		nwTail.setUdpPort(nwTailUdpPort);
		nwTail.setRole(Role.TAIL);
		
		ServerInfo currTail = null;
		for (ServerInfo serv : bankList.get(bname).getServerChain())
		{
			if(serv.getRole().equals(Role.TAIL))
			{
				currTail=serv;
				serv.setRole(Role.NORMAL);
				serv.setSuccessor(nwTail);
				nwTail.setPredecessor(serv);
			}
		}
		
		bankList.get(bname).setChainLength(bankList.get(bname).getChainLength()+1);
		bankList.get(bname).getServerChain().add(nwTail);
		log.debug(TAG + "updateChainExtToClients- bank details updated in master" );
		
		try{
			tcpClient1 = new TCPChatClient(InetAddress.getByName(currTail.getIpAddr()), currTail.getPort(), "currTail");
			tcpClient1.setMasterServer(this);
			tcpClient1.Start();
		
			tcpClient1.SendDataFromMaster(UPDATETAIL + nwTailIp +DELMTER + nwTailPort + DELMTER +nwTailUdpPort);
			log.debug(TAG + "updateChainExtToClients- to update the chain newtail info sent to oldtail again");
			
			for(ClientInfo clnt: bankList.get(bname).getClientList())
			{
				fUDPClient = new UDPClient("My UDP client", "Example", 0, 2, 2, InetAddress.getByName(clnt.getClientIp()), clnt.getClientPort());
				fUDPClient.Start();
				fUDPClient.sendMessageToServer(MSTag+NEWTAIL+nwTail.getIpAddr()+":"+nwTail.getUdpPort());
				log.debug(TAG +"updateChainExtToClients - nwtail data sent to client: "+clnt.getClientIp()+" port: "+clnt.getClientPort());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//remove curr tail from pending ext list
		if(chainExtpendingList.containsKey(currTail.getIpAddr()+currTail.getPort()))
		{
			chainExtpendingList.remove(currTail.getIpAddr()+currTail.getPort());
			log.debug(TAG + "updateChainExtToClients- remove curr tail from pending ext list");
		}
	}
	
	
	public synchronized void receiveSentList(String msg)
	{
		log.debug(TAG + "receiveSentList- " + msg );
		String info[]= msg.split(DELMTER);
		String failedServerIp= info[0];
		int failedServerPort =Integer.parseInt(info[1]);
		String predServerIp = info[2];
		int predServerPort = Integer.parseInt(info[3]);
		String bkname= info[4];
		String succServerIp=info[5];
		int succServerPort=Integer.parseInt(info[6]);
		
		String sentListMsg = info[7];
		
		try {
			log.debug(TAG + "predServerIp:"+predServerIp+" predServerPort:"+predServerPort+" msg:"+sentListMsg);
			tcpClient1 = new TCPChatClient(InetAddress.getByName(predServerIp), predServerPort, "server");
			tcpClient1.setMasterServer(this);
			tcpClient1.Start();
		
		tcpClient1.SendDataFromMaster(NEWSUCCESSOR +succServerIp+DELMTER+succServerPort+DELMTER+ sentListMsg);
		log.debug(TAG + "successor data sent to predecessor");
		//tcpClient.Stop();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//remove server from chain
		ServerInfo failserv= new ServerInfo();
		failserv.setIpAddr(failedServerIp);
		failserv.setPort(failedServerPort);
		pendingList.remove(failserv);
		
		/*ServerInfo predServer= new ServerInfo();
		predServer.setIpAddr(predServerIp);
		predServer.setPort(predServerPort);
		ServerInfo succServer= new ServerInfo();
		succServer.setIpAddr(succServerIp);
		succServer.setPort(succServerPort);
		
		CopyOnWriteArrayList<ServerInfo> serverChain=bankList.get(bkname).getServerChain();
		for (ServerInfo s : serverChain)
		{
			if(s.equals(failserv))
			{
				failserv = s;
				//pendingList.remove(failserv);
			}
			else if(s.equals(predServer))
			{
				s.setSuccessor(s.getSuccessor().getSuccessor());
			}
			else if(s.equals(succServer))
			{
				s.setPredecessor(s.getPredecessor().getPredecessor());
			}
			
		}
		
		//remove server from chain
		bankList.get(bkname).getServerChain().remove(failserv);*/
	}
	
	public synchronized void receiveHB(String msg)
	{
		log.debug(TAG + "receiveHB: "+msg+"-from: "+ip );
		String[] msgdata= msg.split(":");
		String bankname= msgdata[0].trim();
		int serverno= Integer.parseInt(msgdata[1].trim());
		String serverip= msgdata[2].trim();
		int serverport= Integer.parseInt(msgdata[3].trim());
		String hbmsg= msgdata[4].trim();
		ServerInfo serv= new ServerInfo();
		serv.setIpAddr(serverip);
		serv.setPort(serverport);
		serv.setPosition(serverno);
		
		CopyOnWriteArrayList<ServerInfo> serverChain=bankList.get(bankname).getServerChain();
		for (ServerInfo s : serverChain)
		{
			if(s.equals(serv))
			{
				log.debug(TAG + "Setting time for ip: "+msg+"-from: "+ip );
				s.setTimestamp( new Date().getTime());
			}
		}
	}

	public HashMap<String, Bank> getBankList() {
		return bankList;
	}

	public void setBankList(HashMap<String, Bank> bankList) {
		this.bankList = bankList;
	}
	
}
