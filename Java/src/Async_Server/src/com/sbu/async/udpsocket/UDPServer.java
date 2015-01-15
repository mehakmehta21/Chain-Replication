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
package com.sbu.async.udpsocket;

import Extasys.Network.UDP.Server.Listener.UDPListener;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.sbu.async.Role;
import com.sbu.async.server.Server;

/**
 *
 * @author Nikos Siatras
 */
public class UDPServer extends Extasys.Network.UDP.Server.ExtasysUDPServer
{

	private static String TAG = "UDPServer: ";
	private Server bankServer;
    public UDPServer(String name, String description, InetAddress listenerIP, int port, int connectionsTimeOut, int corePoolSize, int maximumPoolSize)
    {
        super(name, description, corePoolSize, maximumPoolSize);
        this.AddListener("My UDP Listener", listenerIP, port, 10240, connectionsTimeOut);
    }
    
    public void setBankServer(Server server)
    {
    	this.bankServer = server;
    }

    @Override
    public void OnDataReceive(UDPListener listener, DatagramPacket packet)
    {
        //System.out.println("Data received from " + packet.getAddress().toString());
    	if(this.bankServer.getRole() == Role.TAIL)
    	{
    		System.out.println(TAG + "received...");
    		this.bankServer.eventQuery(packet.getAddress(), packet.getPort(), new String(packet.getData()));
    	}else if(this.bankServer.getRole() == Role.HEAD)
    	{
    		System.out.println(TAG + "data received from client " + new String(packet.getData()));
    		this.bankServer.eventUpdate(packet.getAddress(), packet.getPort(), new String(packet.getData()));
    	}
    	/*try
        {
            // Send data back to the sender.
            listener.SendData(new DatagramPacket(packet.getData(), 0, packet.getLength(), packet.getAddress(), packet.getPort()));
        }
        catch (Exception ex)
        {
        }*/
    }
}
