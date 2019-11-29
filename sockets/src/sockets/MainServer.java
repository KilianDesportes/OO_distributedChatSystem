package sockets;

public class MainServer {
	
	public static void main(String[] args) {

		 new threadReceive().start();
		 
		 new threadReceiverMulti().start();
		 
	}

}
