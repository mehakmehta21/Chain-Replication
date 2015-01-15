/*Copyright (c) 2008 Nikos Siatras

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/
package com.sbu.async.tcpsocket;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;

import Extasys.DataFrame;
import Extasys.Network.TCP.Server.Listener.TCPClientConnection;
import Extasys.Network.TCP.Server.Listener.Exceptions.ClientIsDisconnectedException;
import Extasys.Network.TCP.Server.Listener.Exceptions.OutgoingPacketFailedException;

import com.sbu.async.server.Server;

/**
 *
 * @author Nikos Siatras
 */
public class TCPChatServer extends Extasys.Network.TCP.Server.ExtasysTCPServer implements Extasys.Network.TCP.Server.ITCPServer
{

	private static String TAG = "TCPChatServer: ";
    private Hashtable fConnectedClients;
    private String fSPT = String.valueOf(((char) 2)); // Message splitter character.
    private String fMCChar = String.valueOf(((char) 3)); // Message collector character.
    private Thread fPingThread;
    private boolean fServerIsActive;
    private TCPClientConnection client;
    private Server bankServer;
    
	private String HBTag = "HB"; // Heartbeat msg tag
	private String MSTag = "MS"; // Master msg tag
	private String SLTag = "SL"; // Suffix list tag
	private final String CHAINEXTENSION = "CE";
	private String CHAINTRANSACTION = "CT"; // Chain transaction list tag
	private String UPDATETAIL = "UT"; // update old tail with new tail

    public TCPChatServer(InetAddress listenerIP, int port)
    {
        super("TCP Chat Server", "", 10, 100);
        super.AddListener("Main Listener", listenerIP, port, 99999, 20480, 0, 100, ((char) 3));
        fConnectedClients = new Hashtable();
//        this.server = server;
    }
    
    public void setBankServer(Server bankServer)
    {
    	this.bankServer = bankServer;
    }
    


    @Override
    public void Start()
    {
        try
        {
            super.Start();

        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            Stop();
        }
    }

    @Override
    public void Stop()
    {
        fServerIsActive = false;

        if (fPingThread != null)
        {
            fServerIsActive = false;
            fPingThread.interrupt();
        }

        super.Stop();
    }

    @Override
    public void OnDataReceive(TCPClientConnection sender, DataFrame data)
    {
//    	System.out.println(TAG + " sync from predecessor");

    	this.client = sender;
    	String msg= new String(data.getBytes());
    	System.out.println(TAG + " OnDataReceive: "+ msg);
    	if(msg.startsWith(MSTag))
    	{
    		String masterMsg=msg.substring(2);
    		if(masterMsg.startsWith(CHAINEXTENSION))
    		{
    			this.bankServer.sendTransToNwTail(masterMsg.substring(2));
    		}
    		else if(masterMsg.startsWith(UPDATETAIL))
    		{
    			this.bankServer.updateOldTail(masterMsg.substring(2));
    		}
    		else
    		{
    			this.bankServer.processMasterMsg(masterMsg);
    		}

    	}
    	else if(msg.startsWith(SLTag))
    	{
    		String masterMsg=msg.substring(2);
    		this.bankServer.eventSLSync(masterMsg);
    	}
    	else if(msg.startsWith(CHAINTRANSACTION))
    	{
    		String masterMsg=msg.substring(2);
    		this.bankServer.processChainTransaction(masterMsg);
    	}
    	else
    	{
        	this.bankServer.eventSync(msg);
    	}

//    	SendToClient("ack", sender);
    }
    
    public void sendAckToPredecessor(String data)
    {
//    	System.out.println(TAG + "sendAckToPredecessor");
    	 try {
			client.SendData(data + fMCChar);
		} catch (ClientIsDisconnectedException e) {
			// TODO Auto-generated catch block
			System.out.println(TAG + "sendAckToPredecessor failed..");
			e.printStackTrace();
		} catch (OutgoingPacketFailedException e) {
			// TODO Auto-generated catch block
			System.out.println(TAG + "sendAckToPredecessor failed...");
			e.printStackTrace();
		}
    }
    
    public void SendToClient(String data, TCPClientConnection sender)
    {
//    	System.out.println(TAG + "sending ack to predecessor");
        try
        {
            sender.SendData(data + fMCChar);
        }
        catch (ClientIsDisconnectedException ex)
        {
            // Client disconnected.
        	System.out.println(TAG + "sending data to client failed...");
            System.err.println(ex.getMessage());
        }
        catch (OutgoingPacketFailedException ex)
        {
            // Failed to send packet.
        	System.out.println(TAG + "sending data to client failed...");
        	System.err.println(ex.getMessage());
        }
        catch (Exception ex)
        {
        }
    }

    private String GetConnectedClientsList()
    {
        String list = "";
        for (Enumeration e = fConnectedClients.keys(); e.hasMoreElements();)
        {
            list = list + ((TCPChatUser) fConnectedClients.get(e.nextElement())).getUsername() + String.valueOf(((char) 1));
        }
        return "User_List" + fSPT + list;
    }

    @Override
    public void OnClientConnect(TCPClientConnection client)
    {
        System.out.println( TAG + "New client starting connection " + client.getIPAddress() + " numbers: " + fConnectedClients.size());
//       fMainForm.UpdateClientsCount();
    }

    @Override
    public void OnClientDisconnect(TCPClientConnection client)
    {
    	System.out.println( TAG + "client disconnected " + client.getIPAddress());
        if (fConnectedClients.containsKey(client.getIPAddress()))
        {
//            SendToAllClients("Remove_User" + fSPT + ((TCPChatUser) fConnectedClients.get(client.getIPAddress())).getUsername());
            fConnectedClients.remove(client.getIPAddress());
        }
       // fMainForm.UpdateClientsCount();
    }
}




