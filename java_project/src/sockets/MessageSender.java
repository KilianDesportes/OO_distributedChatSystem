/**
 * MessageSender used to send message on the network.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */
package sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class MessageSender {

	private DatagramSocket socket;
	private byte[] buf;

	public MessageSender() {

		try {

			socket = new DatagramSocket();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Send a message with TCP protocol.
	 * 
	 * @param socket          Socket used to send the message.
	 * @param message_to_send Message to send.
	 * @param target_address  Destination address for this message.
	 */
	public void sendMessageTCP(Socket socket, String message_to_send, InetAddress target_address) {

		try {

			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			System.out.println("Sending " + message_to_send + " to " + target_address + " with " + socket);
			out.writeBytes(message_to_send);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Send a message with UDP protocol.
	 * 
	 * @param message_to_send Message to send.
	 * @param target_address  Destination address for this message.
	 */
	public void sendMessageUDP(String message_to_send, InetAddress target_address) {

		buf = message_to_send.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, target_address, 8888);

		try {

			socket.send(packet);
			String str = new String(packet.getData(), packet.getOffset(), packet.getLength());
			System.out.println("Sending " + str + " to " + packet.getAddress().getHostAddress());
			System.out.flush();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Send a multicast message.
	 * 
	 * @param message Message to send.
	 */
	public void sendMessageMulticast(String message) {

		try {

			InetAddress group = InetAddress.getByName("230.0.0.0");
			byte[] buffer = message.getBytes();
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length, group, 8889);
			socket.setBroadcast(true);
			socket.send(pack);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
}
