package sockets;


import java.time.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class threadReceive extends Thread {
	
	private DatagramSocket socket;
	private byte[] buffer = new byte[1024];
	private String local_adress;
	File fileHistory;
	FileWriter fwriter;
	PrintWriter writer;

	public threadReceive () {

		try {
			socket = new DatagramSocket(8888);
			local_adress = InetAddress.getLocalHost ().getHostAddress ();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {
		
		System.out.println("ReceiveThread Running");
		System.out.flush();

		while (true) {
			
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace(); 
			}

			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			String file_ipAdr = address.getHostAddress().toString().replace('.', '_') + ".txt";
			try {
				fileHistory = new File(file_ipAdr);
				if(!fileHistory.exists()){
					fileHistory.createNewFile();
				}
				fwriter = new FileWriter(fileHistory, true);

				writer = new PrintWriter(fwriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int year = LocalDateTime.now().getYear();
			int month = LocalDateTime.now().getMonthValue();
			int day = LocalDateTime.now().getDayOfMonth();
			int hour = LocalDateTime.now().getHour();
			int min = LocalDateTime.now().getMinute();
			
			String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
			System.out.println("Received " + received + " from " + packet.getAddress().getHostAddress()+" at ["+hour+":"+min+" - "+day+"/"+month+"/"+year+"]");
			System.out.flush();
			writer.write(local_adress+";"+address.getHostAddress().toString()+";"+received+"\n");
			writer.flush();

		}

	}

}
