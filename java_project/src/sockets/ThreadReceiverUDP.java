/**
 * ThreadReceiverUDP is a thread which run and which will receive every UDP packet.
 * Once the packet is received, it will put it into the BlockingQueue which is shared with the 
 * NetworkController. 
 * This message will be treated in the NetworkController and not in this class, which only
 * receive it and put it into the BlockingQueue.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */
package sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class ThreadReceiverUDP extends Thread {

	private DatagramSocket socket;

	private byte[] buffer = new byte[1024];

	private BlockingQueue<DatagramPacket> messages_Queue;

	public ThreadReceiverUDP(BlockingQueue<DatagramPacket> networkController_messages_Queue) {

		this.messages_Queue = networkController_messages_Queue;

		try {

			socket = new DatagramSocket(8888);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Overriding run method of Thread Class. This Method is used to receive UDP
	 * packet constantly and put it into the BlockingQUeue. In order to get managed
	 * by the NetworkController.
	 * 
	 * @see Thread
	 */
	public void run() {

		while (true) {

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {

				socket.receive(packet);

				this.messages_Queue.put(packet);

				String received = new String(packet.getData(), packet.getOffset(), packet.getLength());

				System.out.println("QUEUE PUT : " + received);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}
}
