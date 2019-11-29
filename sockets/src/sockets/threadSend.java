package sockets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class threadSend extends Thread {

	private DatagramSocket socket;
	private InetAddress target_address;
	private byte[] buf;
	private String msg_to_send = "";
	private String local_adress;
	PrintWriter writer;
	File fileHistory;
	FileWriter fwriter;

	public threadSend () {

		try {
			socket = new DatagramSocket();
			local_adress = InetAddress.getLocalHost ().getHostAddress ();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		int i = 0;
		System.out.println("SendThread Running");
		while(true){
			if(msg_to_send.compareTo("") != 0){
				buf = msg_to_send.getBytes();
				DatagramPacket packet = new DatagramPacket(buf, buf.length, target_address, 8888);
				String file_ipAdr = target_address.getHostAddress().replace('.', '_') + ".txt";

				try {
					fileHistory = new File(file_ipAdr);
					if(!fileHistory.exists()){
						fileHistory.createNewFile();
					}
					fwriter = new FileWriter(fileHistory, true);
					writer = new PrintWriter(fwriter);
					socket.send(packet);
					String str = new String(packet.getData(),packet.getOffset(),packet.getLength());
					System.out.println("Sending " + str + " to " + packet.getAddress().getHostAddress());
					writer.write(local_adress+";"+target_address.getHostAddress()+";"+msg_to_send+"\n");
					writer.flush();
				} catch (Exception e) {
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
		msg_to_send = message;
		target_address = ipAdr;
	}

}
