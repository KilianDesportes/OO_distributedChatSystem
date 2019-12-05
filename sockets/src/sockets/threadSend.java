package sockets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class threadSend extends Thread {

	private DatagramSocket socket;
	private InetAddress target_address;
	private byte[] buf;
	private String msg_to_send = "";
	private String local_adress;
	PrintWriter writer;
	File fileHistory;
	FileWriter fwriter;
	private String pseudo;

	public threadSend (String s) {

		try {
			this.pseudo = s;
			socket = new DatagramSocket();
			local_adress = InetAddress.getLocalHost ().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		int i = 0;
		System.out.println("SendThread Running");
		Timer tim = new Timer();
		TimerTask timTask = new TimerTask() {
			public void run() {
				//System.out.println("-- User " + pseudo + " is asking who is connected. --");
				testUserConnected();
			}
		};
		tim.schedule(timTask,0,10000);
		while(true){
			if(msg_to_send.compareTo("") != 0){
				buf = msg_to_send.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, target_address, 8888);
				String file_ipAdr = target_address.getHostAddress().replace('.', '_') + ".txt";

				try {
					fileHistory = new File(file_ipAdr);
					if(!fileHistory.exists()){
						fileHistory.createNewFile();
					}
					fwriter = new FileWriter(fileHistory, true);
					writer = new PrintWriter(fwriter);
					socket.send(packet);
					String str = new String(packet.getData(),packet.getOffset(),packet.getLength());
					System.out.println("Sending " + str + " to " + packet.getAddress().getHostAddress());
					System.out.flush();
					writer.write(local_adress+";"+target_address.getHostAddress()+";"+msg_to_send+"\n");
					writer.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				msg_to_send="";
			}else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public String getPseudo() {
		return this.pseudo;
	}

	public void sendMessage(String message,InetAddress ipAdr){
		msg_to_send = message;
		target_address = ipAdr;
	}
	
	public void login()
	{

		try
		{
			//System.out.println("I am " + this.pseudo + ". I login.");
			InetAddress group = InetAddress.getByName("230.0.0.0");
			byte[] buffer = this.pseudo.getBytes();
			DatagramPacket pack = new DatagramPacket(buffer,buffer.length,group,8889);
			socket.setBroadcast(true);
			socket.send(pack);
		}
		catch ( Exception e )
		{ }
	}
	
	public void testUserConnected() {
		try
		{
			InetAddress group = InetAddress.getByName("230.0.0.0");
			String message = "whoIsConnected";
			byte[] buffer = message.getBytes();
			DatagramPacket pack = new DatagramPacket(buffer,buffer.length,group,8889);
			socket.setBroadcast(true);
			socket.send(pack);
		}
		catch ( Exception e )
		{ }
	}

}
