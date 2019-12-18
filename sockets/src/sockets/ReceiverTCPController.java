package sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ReceiverTCPController extends Thread {

	ServerSocket server_socket;
	Socket socket;

	private BlockingQueue<DatagramPacket> messages_Queue;

	public ReceiverTCPController(BlockingQueue<DatagramPacket> networkController_messages_Queue) {

		this.messages_Queue = networkController_messages_Queue;

		try {

			this.server_socket = new ServerSocket(8887);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {

		Socket sock = null;

		while (true) {

			try {

				sock = this.server_socket.accept();

			} catch (IOException e) {

				e.printStackTrace();
			}

			if (sock != null) {

				new ThreadReceiverTCP(this.messages_Queue, sock);

			} else {

				// Nothing to do
			}

		}

	}

}
