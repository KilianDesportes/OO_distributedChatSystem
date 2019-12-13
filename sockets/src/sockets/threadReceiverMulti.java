package sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class threadReceiverMulti extends Thread {

	MulticastSocket sock = null;
	protected byte[] buffer = new byte[256];
	protected UserList userList;
	protected messageSender mSender;
	private boolean isValid = true;
	private InetAddress public_address;


	public threadReceiverMulti () {
		System.out.println("threadReceiverMulti - threadReceiverMulti");

		userList = new UserList();

		try { 
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); 
			while (interfaces.hasMoreElements()) { 
				NetworkInterface interfaceN = (NetworkInterface) interfaces.nextElement(); 
				Enumeration<InetAddress> iEnum = interfaceN.getInetAddresses(); 

				while (iEnum.hasMoreElements()) { 

					InetAddress ia= (InetAddress) iEnum.nextElement();
					if (!ia.isLinkLocalAddress() 
							&& !ia.isLoopbackAddress()
							&& ia instanceof Inet4Address) {
						public_address = ia;
						System.out.println("Public address = " + this.public_address);
					}

				} 
			} 
		} catch (Exception ex) { 
			System.out.println("pas de carte réseau."); 
			ex.printStackTrace(); 
		}

		System.out.println("Public final address = " + this.public_address);

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
		System.out.println("threadReceiverMulti - setSender");

		this.mSender = ms_mSender;
	}

	public boolean isPseudoValid() {

		System.out.println("threadReceiverMulti - isPseudoValid");


		mSender.sendIsPseudoValid();
		long time = System.currentTimeMillis();
		while(System.currentTimeMillis() - time < 2000) {

		}
		System.out.println("isValid " + isValid);
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

				try {
					System.out.println(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}

				if((received.compareTo(this.mSender.getPseudo())) == 0 && (!public_address.equals(address))) {

					System.out.println("Inet mienne : " + public_address + " sienne " + address);
					System.out.println("Même pseudo que le mien");

					mSender.sendMessage("isPseudoValidNOK", address);

				}else {

					System.out.println("Inet mienne : " + public_address + " sienne " + address);
					System.out.println("Pseudo différents, je suis " + this.mSender.getPseudo() + " il est " + received);
				}

			}else if(received.contains("isPseudoValidNOK")) {
				isValid = false;

			}else {
				userList.addUser(received, address);
			}
		}
	}
}
