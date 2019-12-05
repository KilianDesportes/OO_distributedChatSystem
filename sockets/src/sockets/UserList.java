package sockets;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserList {

	protected HashMap<String,InetAddress> hm_user;

	public UserList() {
		this.hm_user = new HashMap<String,InetAddress>();
	}

	public void addUser(String str_pseudo, InetAddress adr_user) {
		hm_user.put(str_pseudo, adr_user);
		System.out.println("_____");
		System.out.println("User " + str_pseudo + " added.");
		System.out.println("List:");
		for (String key : hm_user.keySet()) {
		    System.out.println(key + " at adress " + hm_user.get(key));
		}
		System.out.println("_____");
	}

	public InetAddress getAdr(String str_pseudo) {
		return hm_user.get(str_pseudo);
	}

	public void removeUser(String str) {
		hm_user.remove(str);
	}

}