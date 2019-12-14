package sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadReceiverUDP extends Thread {

	private DatagramSocket socket;
	
	private byte[] buffer = new byte[1024];

	private LinkedBlockingQueue<DatagramPacket> messages_Queue;

	public ThreadReceiverUDP(LinkedBlockingQueue<DatagramPacket> networkController_messages_Queue) {

		this.messages_Queue = networkController_messages_Queue;
		
		try {
			
			socket = new DatagramSocket(8888);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void run() {

		while (true) {

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {
				
				socket.receive(packet);
				
				this.messages_Queue.put(packet);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}

		}

	}

}
