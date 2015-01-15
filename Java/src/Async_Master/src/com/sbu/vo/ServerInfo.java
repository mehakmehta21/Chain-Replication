package com.sbu.vo;

public class ServerInfo {
	private int port;
	private String ipAddr;
	private long timestamp=0;
	private Role role;
	private int position;
	private ServerInfo successor;
	private ServerInfo predecessor;
	private boolean serverUp=true;
	private int udpPort;
	
	
	public int getUdpPort() {
		return udpPort;
	}
	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
	public boolean isServerUp() {
		return serverUp;
	}
	public void setServerUp(boolean serverUp) {
		this.serverUp = serverUp;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public ServerInfo getSuccessor() {
		return successor;
	}
	public void setSuccessor(ServerInfo successor) {
		this.successor = successor;
	}
	public ServerInfo getPredecessor() {
		return predecessor;
	}
	public void setPredecessor(ServerInfo predesessor) {
		this.predecessor = predesessor;
	}
	
	/*public String toString()
	{
		return "[Server ip: "+ getIpAddr()+" port: "+ getPort()+ " position:"+ getPosition() + " role: "+ getRole() +"]";
	}*/
	
	 public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (obj == null || obj.getClass() != this.getClass()) {
	            return false;
	        }

	        ServerInfo serverinfo = (ServerInfo) obj;
	        if(ipAddr.equals(serverinfo.ipAddr) && port==serverinfo.port)
	        	return true;
	        
	        return false;
	               
	    }
	
}
