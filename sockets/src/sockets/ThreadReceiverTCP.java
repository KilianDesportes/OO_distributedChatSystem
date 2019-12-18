package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ThreadReceiverTCP extends Thread {

	Socket socket;

	private BlockingQueue<DatagramPacket> messages_Queue;

	public ThreadReceiverTCP(BlockingQueue<DatagramPacket> networkController_messages_Queue, Socket socket) {

		this.messages_Queue = networkController_messages_Queue;

		try {

			this.socket = socket;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {

		BufferedReader in_stream = null;

		while (in_stream == null) {

			try {

				in_stream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			} catch (IOException e1) {

				e1.printStackTrace();

			}

		}

		while (socket.isConnected()) {

			try {

				String received = in_stream.readLine();
				
				System.out.println("TCP PACKET RECEIVED FROM " + this.socket.getInetAddress() + " : " + received);

				DatagramPacket packet = new DatagramPacket(received.getBytes(), received.length(),
						this.socket.getInetAddress(), 3000);

				this.messages_Queue.put(packet);

				System.out.println("QUEUE PUT : " + received);

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

}
