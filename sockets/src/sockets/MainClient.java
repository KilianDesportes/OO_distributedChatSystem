package sockets;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner; 

public class MainClient {

	public static void main(String[] args) {


		 threadSend th = new threadSend();
		 th.start();
		 Scanner scan = new Scanner(System.in);
		 
		 while(true){

			 System.out.println("Enter your message");
			 String s = scan.next();
			 InetAddress adr =null;
			try {
				adr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			System.out.println("Message main " + s + " " + adr.toString());
			 th.sendMessage(s, adr);
			 
		 }

	}

}
