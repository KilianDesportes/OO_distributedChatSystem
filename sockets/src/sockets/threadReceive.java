package sockets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class threadReceive extends Thread {
	
	private DatagramSocket socket;
	private byte[] buffer = new byte[1024];
	PrintWriter writer;

	public threadReceive () {

		try {
			socket = new DatagramSocket(8888);
			writer = new PrintWriter("temp_messages.txt", "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		
		System.out.println("ReceiveThread Running");

		while (true) {
			
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace(); 
			}

			InetAddress address = packet.getAddress();
			int port = packet.getPort();

			String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
			System.out.println("Packet received : " + received + " from " + address.toString());
			System.out.flush();
			writer.write(address.toString().substring(1)+";"+received+"\n");
			writer.flush();

		}

	}

}
