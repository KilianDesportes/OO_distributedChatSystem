package sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class threadReceiverMulti extends Thread {

	MulticastSocket sock = null;
	protected byte[] buffer = new byte[256];
	protected UserList userList;


	public threadReceiverMulti () {
		
		userList = new UserList();
		
		try {
			sock = new MulticastSocket(8889);
			InetAddress group = InetAddress.getByName("230.0.0.0");
			sock.joinGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {

		System.out.println("ReceiveThreadMulti Running");

		while (true) {

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {
				sock.receive(packet);
			} catch (IOException e) {
				e.printStackTrace(); 
			}

			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
			userList.addUser(received, address);
			System.out.println("Packet received multicast : " + received + " from " + address.getHostAddress());
			System.out.flush();
		}

	}

}
