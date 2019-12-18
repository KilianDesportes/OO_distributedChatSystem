package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class Conversation {

	private String distant_user_name;
	private InetAddress distant_user_ipAdr;
	private Socket distant_user_tpc_socket;

	private HashMap<String, String> hm_conversation_messages;

	public Conversation(String s_usr_name, InetAddress ina_user_adr, Socket s_usr_socket) {

		this.distant_user_name = s_usr_name;
		this.distant_user_ipAdr = ina_user_adr;
		this.distant_user_tpc_socket = s_usr_socket;

	}

	private void loadHistory() {
		
		String fileName = this.distant_user_ipAdr.getHostAddress().replace('.', '_') + ".txt";

		try {

			InputStream ips = new FileInputStream(fileName);
			
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			
			String line;

			while ((line = br.readLine()) != null) {
				
				//FORMAT netAdr_sources.getHostAddress() + ";" + this.local_inetAdr.getHostAddress() + ";" + message_received + ";" + this.getTime("/", "/", "/"));
				
				String[] split_line = line.split(";");
				
				String ipSender = split_line[0];
				String ipReceiver = split_line[1];
				String message = split_line[2];
				String date = split_line[3];
				
				String msg_with_date = message+";"+date;

				hm_conversation_messages.put(ipSender,msg_with_date);
				

			}

			br.close();

		}

		catch (Exception e) {

			System.out.println(e.toString());

		}

	}

}
