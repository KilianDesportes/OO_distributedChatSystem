package model;

import java.net.InetAddress;
import java.util.*;

import controller.MainController;

public class UserList {

	private HashMap<String, InetAddress> hm_user_inet;
	private HashMap<String, Timer> hm_user_timer;

	private MainController mainController;

	public UserList(MainController maincontroller) {

		this.mainController = maincontroller;
		this.hm_user_inet = new HashMap<String, InetAddress>();
		this.hm_user_timer = new HashMap<String, Timer>();
	}

	public void addUser(String str_pseudo, InetAddress adr_user) {

		if (hm_user_inet.put(str_pseudo, adr_user) == null) {

			Timer tim = new Timer();

			tim.schedule(new MajList(str_pseudo), 10000);

			hm_user_timer.put(str_pseudo, tim);

		} else {
			
			//Reset of timer if user is already on the list

			hm_user_timer.get(str_pseudo).cancel();

			Timer tim = new Timer();

			tim.schedule(new MajList(str_pseudo), 5000);

			hm_user_timer.put(str_pseudo, tim);

		}
	}

	//Print of hm_user_inet which contains pseudo and inet address
	public void printUserList() {
		
		for (String key : hm_user_inet.keySet()) {
			System.out.println("Name : " + key + " | Address : " + hm_user_inet.get(key));
			System.out.flush();
		}
		
	}

	//Return the inet address of a given pseudo
	public InetAddress getAdr(String str_pseudo) {
		InetAddress returned_adr = null;
		if (hm_user_inet.containsKey(str_pseudo)) {
			returned_adr = hm_user_inet.get(str_pseudo);
		}
		return returned_adr;
	}

	//Remove an user from hm_user_inet
	public void removeUser(String pseudo) {

		if (hm_user_inet.containsKey(pseudo)) {
			
			System.out.println(pseudo + " removed from inet list");
			hm_user_inet.remove(pseudo);
			
			this.mainController.updateUL(this);
			
		}

	}

	class MajList extends TimerTask {

		String pseudo;

		public MajList(String pseudo) {
			this.pseudo = pseudo;
		}

		public void run() {
			
			removeUser(pseudo);

			hm_user_timer.remove(pseudo);
			
			System.out.println(pseudo + " removed from timer list");
			
		}
	}

	public HashMap<String, InetAddress> getHashMapUser() {
		return hm_user_inet;
	}
}