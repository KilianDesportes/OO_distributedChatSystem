package sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
