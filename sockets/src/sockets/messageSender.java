package sockets;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class messageSender {

	private DatagramSocket socket;
	private byte[] buf;
	private String local_adress;
	private PrintWriter writer;
	private File fileHistory;
	private FileWriter fwriter;
	private String pseudo;

	public messageSender (String s) {

		try {
			this.pseudo = s;
			socket = new DatagramSocket();
			local_adress = InetAddress.getLocalHost ().getHostAddress();
			System.out.println("MSG SENDER " + local_adress);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(String message_to_send,InetAddress target_address){
		
		System.out.println("messageSender - sendMessage");



		buf = message_to_send.getBytes();
		
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
			writer.write(local_adress+";"+target_address.getHostAddress()+";"+message_to_send+"\n");
			writer.flush();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

	public String getPseudo() {
		return this.pseudo;
	}
	
	public void sendIsPseudoValid() {
		System.out.println("messageSender - sendIsPseudoValid");

		try
		{
			InetAddress group = InetAddress.getByName("230.0.0.0");
			String msg_to_send = "isPseudoValid>"+this.pseudo;
			byte[] buffer = msg_to_send.getBytes();
			DatagramPacket pack = new DatagramPacket(buffer,buffer.length,group,8889);
			socket.setBroadcast(true);
			socket.send(pack);
		}
		catch ( Exception e )
		{ }
	}

	public void login()
	{
		System.out.println("messageSender - login");

		try
		{
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
		System.out.println("messageSender - testUserConnected");

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
