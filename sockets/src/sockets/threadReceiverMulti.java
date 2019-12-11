package sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class threadReceiverMulti extends Thread {

	MulticastSocket sock = null;
	protected byte[] buffer = new byte[256];
	protected UserList userList;
	protected messageSender mSender;
	private boolean isValid = true;


	public threadReceiverMulti () {
		userList = new UserList();
		
		try {
			sock = new MulticastSocket(8889);
			InetAddress group = InetAddress.getByName("230.0.0.0");
			sock.joinGroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public UserList getUserList() {
		return this.userList;
	}
	
	public void setSender(messageSender ms_mSender) {
		this.mSender = ms_mSender;
	}
	
	public boolean isPseudoValid() {
		mSender.sendIsPseudoValid();
		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() - time < 1000) {
			
		}
		if(isValid==true) {
			return true;
		}else {
			return false;
		}
	}

	public void run() {

		while (true) {

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {
				sock.receive(packet);
			} catch (IOException e) {
				e.printStackTrace(); 
			}
			InetAddress address = packet.getAddress();
			String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
			
			
			if(received.compareTo("whoIsConnected")==0) {
				mSender.login();
			}else if(received.contains("isPseudoValid>")){
				received = received.substring(14);
				System.out.println("Pseudo received = " + received);
				if(received==this.mSender.getPseudo()) {
					System.out.println("Même pseudo que le mien");
					isValid = false;
				}
			}else {
				userList.addUser(received, address);
			}
		}
	}
}
