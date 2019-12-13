package controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import sockets.messageSender;
import sockets.threadReceiver;
import sockets.threadReceiverMulti;

public class networkController{
	
	private threadReceiverMulti threadRecvMulti;
	private threadReceiver threadRecv;
	private Scanner scan = new Scanner(System.in);
	private messageSender mSender;
	
	public networkController() {
	}

	public void networkInit() {
		InetAddress local_adr = null;
		try {
			local_adr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		threadRecvMulti = new threadReceiverMulti();
		threadRecvMulti.start();
		threadRecv = new threadReceiver();
		threadRecv.start();	
		System.out.println("Network Init OK");
	}
			
	
	public void setSender(String pseudo) {
		messageSender mSender = new messageSender(pseudo);
		threadRecvMulti.setSender(mSender);
		System.out.println("Set Sender OK");

	}
	
	public boolean isNetworkOk() {
		boolean bool = false;
		if(threadRecvMulti.isAlive() && threadRecv.isAlive()) {
			bool = true;
		}
		return bool;
	}
	
	public boolean isPseudoOk() {
		return threadRecvMulti.isPseudoValid();
	}
	
	/*
	private void networkControl(){
		System.out.println("networkControl ON");
		
		while(threadRecvMulti.isAlive() && threadRecv.isAlive()){
			System.out.println("List of user online :");
			System.out.flush();
			UserList ul = threadRecvMulti.getUserList();
			ul.printUserList();
			System.out.println("For who is your message ? :");
			System.out.flush();
			String s = scan.nextLine();
			String s_adr = ul.getAdr(s).getHostAddress();
			InetAddress adr = null;
			try {
				adr = InetAddress.getByName(s_adr);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			System.out.println("Your message to " + s + " at " + s_adr);
			System.out.flush();
			s = scan.nextLine();
			mSender.sendMessage(s, adr);
		}
		
	}
	*/


}
