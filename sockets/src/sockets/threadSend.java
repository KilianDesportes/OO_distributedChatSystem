package sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class threadSend extends Thread {

	private DatagramSocket socket;
	private InetAddress target_address;
	private byte[] buf;
	private String msg_to_send = "";

	public threadSend () {

		try {
			socket = new DatagramSocket();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		int i = 0;
		System.out.println("SendThread Running");
		while(true){
			if(msg_to_send.compareTo("") != 0){
				System.out.println("Message changed");
				buf = msg_to_send.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, target_address, 8888);
				try {
					socket.send(packet);
					System.out.println("Packet sended to " + target_address + " : " + msg_to_send);
				} catch (IOException e) {
					e.printStackTrace();
				}
				msg_to_send="";
			}else{
				if(i%Integer.MAX_VALUE==0){
					System.out.println(msg_to_send);
					i=0;
				}
				i++;
			}
		}
		
	}

	public void sendMessage(String message,InetAddress ipAdr){
		System.out.println("1" + this.msg_to_send);

		msg_to_send = message;
		target_address = ipAdr;

		System.out.println("sendMessage method");
		
		System.out.println("2" + this.msg_to_send);

	}

}
