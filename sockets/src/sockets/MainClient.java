package sockets;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner; 

public class MainClient {

	public static void main(String[] args) {


		 threadSend th = new threadSend();
		 th.start();
		 Scanner scan = new Scanner(System.in);
		 
		 th.login("Kilian");
		 
		 while(true){

			 System.out.println("Enter your message");
			 String s = scan.nextLine();
			 InetAddress adr =null;
			try {
				adr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			 th.sendMessage(s, adr);
			 s="";
			 
		 }

	}

}
