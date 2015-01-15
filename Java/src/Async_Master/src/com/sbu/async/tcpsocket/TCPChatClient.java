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

import Extasys.Network.TCP.Client.Connectors.TCPConnector;
import Extasys.Network.TCP.Client.Exceptions.ConnectorCannotSendPacketException;
import Extasys.Network.TCP.Client.Exceptions.ConnectorDisconnectedException;

import com.sbu.master.Master;

/**
 *
 * @author Nikos Siatras
 */
public class TCPChatClient extends Extasys.Network.TCP.Client.ExtasysTCPClient implements Extasys.Network.TCP.Client.ITCPClient
{
	private static String TAG = "TCPChatClient: ";
    private InetAddress fServerIP;
    private int fPort;
    private String fUsername;
//    private frmTCPChatClient fMainForm;
    private String fSPT = String.valueOf(((char) 2)); // Message splitter character.
    private String fMCChar = String.valueOf(((char) 3)); // Message collector character.
    
    private String HBTag = "HB"; // Heartbeat msg tag
    private Master masterServer; 
    private String MSTag = "MS"; // Master msg tag

    public TCPChatClient(InetAddress serverIP, int port, String username/*, frmTCPChatClient frmMain*/)
    {
        super("TCP Chat Client", "", 4, 8);
        fServerIP = serverIP;
        fPort = port;
        fUsername = username;
//        fMainForm = frmMain;

        super.AddConnector("Main Connector", serverIP, port, 20480, ((char) 3));
    }
    
    public void setMasterServer(Master masterServer) {
		this.masterServer = masterServer;
	}

    public void Connect()
    {
        try
        {
            super.Start();
        }
        catch (Exception ex)
        {
//            fMainForm.MarkAsDisconnected();
        }
    }

    /*@Override
    public void OnDataReceive(TCPConnector connector, DataFrame data)
    {
//    	System.out.println(TAG + "data received from successor : " + new String(data.getBytes()));
    	
    	bankServer.eventAck();
    }*/

    @Override
    public void OnConnect(TCPConnector connector)
    {
    	System.out.println(TAG + "onConnect");
//        SendDataToServer("Login" + fSPT + fUsername);
    }

    @Override
    public void OnDisconnect(TCPConnector connector)
    {
    	System.out.println(TAG + "disconnected: ");
//        fMainForm.MarkAsDisconnected();
//        fMainForm.DisplayMessage("You are disconnected...");
    }

    public void SendDataFromMaster(String data)
    {
//    	System.out.println(TAG + "sending data to server : ");
        try
        {
            super.SendData(MSTag + data + fMCChar);
        }
        catch (ConnectorDisconnectedException ex)
        {
        	System.out.println(TAG + "sending data to server failed.. : ");
            System.err.println(ex.getMessage());
        }
        catch (ConnectorCannotSendPacketException ex)
        {
        	System.out.println(TAG + "sending data to server failed.. : ");
            System.err.println(ex.getMessage());
        }
    }
    
    public void SendHeartBeat(String data)
    {
//    	System.out.println(TAG + "sending data to server : ");
        try
        {
            super.SendData(HBTag + data + fMCChar);
        }
        catch (ConnectorDisconnectedException ex)
        {
        	System.out.println(TAG + "sending heartbeat to server failed.. : ");
            System.err.println(ex.getMessage());
        }
        catch (ConnectorCannotSendPacketException ex)
        {
        	System.out.println(TAG + "sending heartbeat to server failed.. : ");
            System.err.println(ex.getMessage());
        }
    }
}
