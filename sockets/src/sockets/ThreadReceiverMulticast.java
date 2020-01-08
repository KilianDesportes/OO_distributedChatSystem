package sockets;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.BlockingQueue;

public class ThreadReceiverMulticast extends Thread {

	private MulticastSocket sock;

	private byte[] buffer = new byte[256];

	private BlockingQueue<DatagramPacket> messages_Queue;

	public ThreadReceiverMulticast(BlockingQueue<DatagramPacket> networkController_messages_Queue) {

		this.messages_Queue = networkController_messages_Queue;

		try {

			sock = new MulticastSocket(8889);

			// A FAIRE !!! detecter l'adresse de groupe via la bib java pour rien avoir en
			// dur

			InetAddress group = InetAddress.getByName("230.0.0.0");
			sock.joinGroup(group);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void run() {

		while (true) {

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {

				sock.receive(packet);

				this.messages_Queue.put(packet);

				String received = new String(packet.getData(), packet.getOffset(), packet.getLength());

				System.out.println("QUEUE PUT : " + received);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}
	}
}
