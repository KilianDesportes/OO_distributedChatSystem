package sockets;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner; 

public class MainClient {

	public static void main(String[] args) throws UnknownHostException {

		InetAddress local_adr = InetAddress.getLocalHost();
		threadReceiverMulti trm = new threadReceiverMulti();
		trm.start();
		new threadReceive().start();

		Scanner scan = new Scanner(System.in);

		System.out.println("What is your pseudo?");
		String s = scan.nextLine();

		threadSend th = new threadSend(s);
		th.start();
		th.login();
		trm.setSendingThread(th);

		while(true){

			System.out.println("Your message");
			s = scan.nextLine();
			InetAddress adr = InetAddress.getByName("10.32.45.187");
			th.sendMessage(s, local_adr);

		}

	}

}
