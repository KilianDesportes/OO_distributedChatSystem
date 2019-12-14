package sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;

import model.UserList;

public class ThreadReceiverMulticast extends Thread {

	private MulticastSocket sock;

	private byte[] buffer = new byte[256];

	private LinkedBlockingQueue<DatagramPacket> messages_Queue;

	public ThreadReceiverMulticast(LinkedBlockingQueue<DatagramPacket> networkController_messages_Queue) {

		this.messages_Queue = networkController_messages_Queue;

		try {

			sock = new MulticastSocket(8889);
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

			} catch (Exception e) {

				e.printStackTrace();

			}

		}
	}
}
