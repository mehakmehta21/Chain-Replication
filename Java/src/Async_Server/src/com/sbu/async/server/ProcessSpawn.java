package com.sbu.async.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sbu.async.Bank;
import com.sbu.async.Role;
import com.sbu.async.ServerInfo;


public class ProcessSpawn {
	
	private static final Logger log = Logger.getLogger(ProcessSpawn.class.getName());
	private static final String TAG= "ProcessSpawn";
	
	public static void main(String[] args) throws IOException {
		
		
			Properties prop = new Properties();
			InputStream input = null;
			
			String filename = args[0];
			input = ProcessSpawn.class.getClassLoader().getResourceAsStream(filename);
			if(input==null){
		            System.out.println("Sorry, unable to load config file: " + filename);
		            log.debug(TAG+"Sorry, unable to load config file: " + filename);
			    return;
			}
			prop.load(input);

		String masterip=prop.getProperty("masterip");
		int masterport=Integer.parseInt(prop.getProperty("masterport"));
		
		int numBanks=Integer.parseInt(prop.getProperty("numBanks"));
		log.debug(TAG+"Num of Banks for initiation: "+ prop.getProperty("numBanks"));
		
		
		for(int t=1;t<=numBanks; t++)
		{
			log.debug(TAG+"bankname: "+ prop.getProperty("bank"+t));
			String bankname=prop.getProperty("bank"+t);
			Bank bank=new Bank();
			bank.setBankName(bankname);
			
			int chainLength=Integer.parseInt(prop.getProperty("bank"+t+"chainlength"));
			int initialchainlength = Integer.parseInt(prop.getProperty("bank"+t+"initialchainlength"));
			
			log.debug(TAG+"chainLength: "+ chainLength);
			bank.setChainLength(chainLength);
			
			for(int i=initialchainlength; i>=1; i--)
			{
				
				int syncport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"syncport"));
				String ip=prop.getProperty("bank"+t+"server"+i+"ip");
				int life=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"life"));
				int serverstartdelay=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"delay"));
				int udpport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"udpport"));
				
				Server server = new Server(bankname,i,initialchainlength,masterip,masterport,serverstartdelay,life,ip,syncport,udpport);
				
				if(prop.getProperty("bank"+t+"server"+i+"tranfail")!=null)
				{
					int tranfailMsgs=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"tranfail"));
					server.setTranfailMsgs(tranfailMsgs);
				}
				
				/*if(prop.getProperty("bank"+t+"server"+i+"udpport")!=null)
				{
					int udpport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"udpport"));
					server.setIPPort(ip,syncport,udpport);
				}
				server.setIPPort(ip, syncport);*/
				
				
				if(i==initialchainlength || serverstartdelay > 0)
				{
					ServerInfo predecessor = new ServerInfo();
					predecessor.ipAddr = prop.getProperty("bank"+t+"server"+(i-1)+"ip");
					predecessor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"syncport"));
					server.setPredecessor(predecessor);
					server.setRole(Role.TAIL);
					server.init(Role.TAIL);
				}
				else if(i==1)
				{
					ServerInfo successor = new ServerInfo();
					successor.ipAddr = prop.getProperty("bank"+t+"server"+(i+1)+"ip");
					successor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"syncport"));
					server.setPredecessor(null);
					server.setSuccessor(successor);
					server.setRole(Role.HEAD);
					server.init(Role.HEAD);
						
				}
				else
				{
					ServerInfo successor = new ServerInfo();
					successor.ipAddr = prop.getProperty("bank"+t+"server"+(i+1)+"ip");
					successor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"syncport"));
					ServerInfo predecessor = new ServerInfo();
					predecessor.ipAddr = prop.getProperty("bank"+t+"server"+(i-1)+"ip");
					if(prop.getProperty("bank"+t+"server"+(i-1)+"syncport")!=null)
					predecessor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"syncport"));
					server.setPredecessor(predecessor);
					server.setSuccessor(successor);
					server.setRole(Role.NORMAL);
					server.init(Role.NORMAL);
				}
				
				//master code- add bankserver to chain
				//bankServers.add(server);	
			}
			//master code 
			//bank.setServerChain(bankServers);
			//bankList.add(bank);
			
			//chain extension
			if(chainLength-initialchainlength>0)
			{
	
				for(int i=chainLength; i>initialchainlength; i--)
				{
					
					int syncport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"syncport"));
					String ip=prop.getProperty("bank"+t+"server"+i+"ip");
					int life=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"life"));
					int serverstartdelay=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"delay"));
					int udpport=Integer.parseInt(prop.getProperty("bank"+t+"server"+i+"udpport"));
					
					Server server = new Server(bankname,i,initialchainlength,masterip,masterport,serverstartdelay,life,ip,syncport,udpport);
					
					if(i==initialchainlength || serverstartdelay > 0)
					{
						ServerInfo predecessor = new ServerInfo();
						predecessor.ipAddr = prop.getProperty("bank"+t+"server"+(i-1)+"ip");
						predecessor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"syncport"));
						server.setPredecessor(predecessor);
						server.setRole(Role.TAIL);
						server.init(Role.TAIL);
					}
					else if(i==1)
					{
						ServerInfo successor = new ServerInfo();
						successor.ipAddr = prop.getProperty("bank"+t+"server"+(i+1)+"ip");
						successor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"syncport"));
						server.setPredecessor(null);
						server.setSuccessor(successor);
						server.setRole(Role.HEAD);
						server.init(Role.HEAD);
							
					}
					else
					{
						ServerInfo successor = new ServerInfo();
						successor.ipAddr = prop.getProperty("bank"+t+"server"+(i+1)+"ip");
						successor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i+1)+"syncport"));
						ServerInfo predecessor = new ServerInfo();
						predecessor.ipAddr = prop.getProperty("bank"+t+"server"+(i-1)+"ip");
						if(prop.getProperty("bank"+t+"server"+(i-1)+"syncport")!=null)
						predecessor.port = Integer.parseInt(prop.getProperty("bank"+t+"server"+(i-1)+"syncport"));
						server.setPredecessor(predecessor);
						server.setSuccessor(successor);
						server.setRole(Role.NORMAL);
						server.init(Role.NORMAL);
					}
					
					//master code- add bankserver to chain
					//bankServers.add(server);	
				}
			
			}
			
		}
		//master code
		//master.setBankList(bankList);
		
		

		
		
	}
}
