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
			
			System.out.println("List of user online :");
			System.out.flush();
			UserList ul = trm.getUserList();
			ul.printUserList();
			System.out.println("For who is your message ? :");
			System.out.flush();

			
			s = scan.nextLine();
			String s_adr = ul.getAdr(s).getHostAddress();
			InetAddress adr = InetAddress.getByName(s_adr);
			System.out.println("Your message to " + s + " at " + s_adr);
			System.out.flush();
			s = scan.nextLine();
			th.sendMessage(s, adr);

		}

	}

}
