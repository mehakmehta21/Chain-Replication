package com.sbu.master;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.sbu.vo.Bank;
import com.sbu.vo.ClientInfo;
import com.sbu.vo.Role;
import com.sbu.vo.ServerInfo;


public class MasterSpawn {
	
	private static final Logger log = Logger.getLogger(MasterSpawn.class.getName());
	private static final String TAG= "MasterSpawn ";
	
	public static void main(String[] args) throws IOException {
		
		
			Properties prop = new Properties();
			InputStream input = null;
			
			String filename = args[0];
			input = MasterSpawn.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
		            System.out.println("Sorry, unable to load config file: " + filename);
		            log.debug(TAG+"Sorry, unable to load config file: " + filename);
			    return;
			}
			prop.load(input);

		String masterip=prop.getProperty("masterip");
		int masterport=Integer.parseInt(prop.getProperty("masterport"));
		long masterTimeout=Integer.parseInt(prop.getProperty("masterTimeout"));
		//Master master= new Master(masterip,masterport,masterTimeout);
		//ArrayList<Bank> bankList= new ArrayList<Bank>();
		HashMap<String, Bank> bankList= new HashMap<String, Bank>();
		log.debug(TAG+"Master Started: "+ masterip+":" +masterport);
		
		//server details
		int numBanks=Integer.parseInt(prop.getProperty("numBanks"));
		log.debug(TAG+"Num of Banks for initiation: "+ prop.getProperty("numBanks"));
		for(int t=1;t<=numBanks; t++)
		{
			//System.out.println(prop.getProperty("bank"+t));
			log.debug(TAG+"bankname: "+ prop.getProperty("bank"+t));
			String bankname=prop.getProperty("bank"+t);
			Bank bank=new Bank();
			bank.setBankName(bankname);
			CopyOnWriteArrayList<ServerInfo> bankServers=new CopyOnWriteArrayList<ServerInfo>();
			
			int initialchainlength=Integer.parseInt(prop.getProperty("bank"+t+"initialchainlength"));
			log.debug(TAG+"initialchainlength: "+ initialchainlength);
			bank.setChainLength(initialchainlength);
			
			for(int i=initialchainlength; i>=1; i--)
			{	
				//Server server = new Server(bankname,i,chainLength,masterip,masterport);
				ServerInfo serverinfo = new ServerInfo();
				int syncport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"syncport"));
				String ip=prop.getProperty("bank"+t+"server"+i+"ip");
				if(prop.getProperty("bank"+t+"server"+i+"udpport")!=null)
				{
					int udpport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"udpport"));
					serverinfo.setUdpPort(udpport);
					//server.setIPPort(ip,syncport,udpport);
				}
				//server.setIPPort(ip, syncport);
				serverinfo.setIpAddr(ip);
				serverinfo.setPort(syncport);
				
				if(i==initialchainlength)
				{
					ServerInfo predecessor = new ServerInfo();
					predecessor.setIpAddr(prop.getProperty("bank"+t+"server"+(i-1)+"ip"));
					predecessor.setPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"syncport")));
					predecessor.setUdpPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"udpport")));
					serverinfo.setPredecessor(predecessor);
					serverinfo.setRole(Role.TAIL);
				}
				else if(i==1)
				{
					ServerInfo successor = new ServerInfo();
					successor.setIpAddr(prop.getProperty("bank"+t+"server"+(i+1)+"ip"));
					successor.setPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"syncport")));
					successor.setUdpPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"udpport")));
					serverinfo.setSuccessor(successor);
					serverinfo.setRole(Role.HEAD);						
				}
				else
				{
					ServerInfo successor = new ServerInfo();
					successor.setIpAddr(prop.getProperty("bank"+t+"server"+(i+1)+"ip"));
					successor.setPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"syncport")));
					if(prop.getProperty("bank"+t+"server"+(i+1)+"udpport")!=null)
						successor.setUdpPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"udpport")));
					
					ServerInfo predecessor = new ServerInfo();
					predecessor.setIpAddr(prop.getProperty("bank"+t+"server"+(i-1)+"ip"));
					if(prop.getProperty("bank"+t+"server"+(i-1)+"syncport")!=null)
						predecessor.setPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"syncport")));
					if(prop.getProperty("bank"+t+"server"+(i-1)+"udpport")!=null)
						predecessor.setUdpPort(Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"udpport")));
					serverinfo.setPredecessor(predecessor);
					serverinfo.setSuccessor(successor);
					serverinfo.setRole(Role.NORMAL);
				}
				
				//master code- add bankserver to chain
				bankServers.add(serverinfo);	
			}
			bank.setServerChain(bankServers);
			//server code
			
			//client code
			CopyOnWriteArrayList<ClientInfo> bankclntList=new CopyOnWriteArrayList<ClientInfo>();
			String clientDetail= prop.getProperty("bank"+t+"clientDetail"); 
			String[] clientList = clientDetail.split(",");

			int banknumclient=Integer.parseInt(prop.getProperty("bank"+t+"numclient"));
			if(banknumclient!=clientList.length)
				System.out.println("banknumclient!=clientList.length");

			for(int i=0; i<clientList.length; i++)
			{	
				String ipport[]=clientList[i].split(":");
				String clientIp=ipport[0];
				int clientReplyPort=Integer.parseInt(ipport[1]);
				ClientInfo clientinfo= new ClientInfo();
				clientinfo.setClientIp(clientIp);
				clientinfo.setClientPort(clientReplyPort);
				bankclntList.add(clientinfo);
			}
			bank.setClientList(bankclntList);
			//client code
			
			bankList.put(bankname, bank);
		}
		//master code ends
		
		//master code
		Master master= new Master(masterip,masterport,masterTimeout,bankList);
		
		
		/*ArrayList<ClientInfo> bankclntList=new ArrayList<ClientInfo>();
		//client details
		int bankNo=Integer.parseInt(prop.getProperty("bankNo"));
		for(int t=1; t <= bankNo; t++)
		{
			String bankname= prop.getProperty("bank"+t);
			log.debug(TAG+"Client data for bank: " + bankname);
			
			String clientDetail= prop.getProperty("bank"+t+"clientDetail"); 
			String[] clientList = clientDetail.split(",");

			int banknumclient=Integer.parseInt(prop.getProperty("bank"+t+"numclient"));
			if(banknumclient!=clientList.length)
				System.out.println("banknumclient!=clientList.length");

			for(int i=0; i<clientList.length; i++)
			{	
				String ipport[]=clientList[i].split(":");
				String clientIp=ipport[0];
				int clientReplyPort=Integer.parseInt(ipport[1]);
				ClientInfo clientinfo= new ClientInfo();
				clientinfo.setClientIp(clientIp);
				clientinfo.setClientPort(clientReplyPort);
				bankclntList.add(clientinfo);
			}
		}*/
		//client code ends
		
	
		
	}
}
