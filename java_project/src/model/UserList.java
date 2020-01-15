/**
 * UserList class, used to make link between user IP address and user name.
 * This class is also used to manage timer linked to a user to check his presence on the network.
 * 
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */

package model;

import java.net.InetAddress;
import java.util.*;

import controller.MainController;
import sockets.ThreadReceiverMulticast;
import sockets.ThreadReceiverUDP;

public class UserList {

	private HashMap<String, InetAddress> hm_user_inet;
	private HashMap<String, Timer> hm_user_timer;

	private MainController mainController;

	public UserList(MainController maincontroller) {

		this.mainController = maincontroller;
		this.hm_user_inet = new HashMap<String, InetAddress>();
		this.hm_user_timer = new HashMap<String, Timer>();
	}

	
	
	/**
	 * Add an user to the HashMap containing IP address.
	 * 
	 * @param str_pseudo Pseudo of the user to be added
	 * @param adr_user   InetAddress of the user to be added
	 */
	public void addUser(String str_pseudo, InetAddress adr_user) {

		System.out.println("Adding user " + str_pseudo + " at address " + adr_user);

		if (hm_user_inet.put(str_pseudo, adr_user) != null) {

			hm_user_timer.get(str_pseudo).cancel();

		}
		Timer tim = new Timer();

		tim.schedule(new MajList(str_pseudo, this), 60000);

		hm_user_timer.put(str_pseudo, tim);
	}

	/**
	 * Print the HashMap containing name and IP address of every users.
	 */
	public void printUserList() {

		for (String key : hm_user_inet.keySet()) {
			System.out.println("Name : " + key + " | Address : " + hm_user_inet.get(key));
			System.out.flush();
		}

	}

	/**
	 * Return the address of a given pseudo.
	 * 
	 * @param str_pseudo Pseudo of the user whose InetAddress needs to be retrieved
	 * @return InetAddress of the given pseudo
	 */
	public InetAddress getAdr(String str_pseudo) {
		InetAddress returned_adr = null;
		if (hm_user_inet.containsKey(str_pseudo)) {
			returned_adr = hm_user_inet.get(str_pseudo);
		}
		return returned_adr;
	}

	/**
	 * Remove an user from the HashMaps. It update the UserList of the
	 * MainController.
	 * 
	 * @param str_pseudo Pseudo of the user who will be removed.
	 */
	public void removeUser(String pseudo) {

		if (hm_user_inet.containsKey(pseudo)) {

			System.out.println(pseudo + " removed from inet list");
			hm_user_inet.remove(pseudo);

			this.mainController.updateUL(this);

		}

	}

	/**
	 * Return the HashMap containing InetAddress.
	 * 
	 * @return HashMap containing InetAddress.
	 */
	public HashMap<String, InetAddress> getHashMapUser() {
		return hm_user_inet;
	}

	/**
	 * Return the HashMap containing Timers.
	 * 
	 * @return HashMap containing Timers.
	 */
	public HashMap<String, Timer> getHashMapTimer() {
		return hm_user_timer;
	}
	
	
	
	public String returnPseudo(InetAddress inetAdd)
	{
		Set entrySet = hm_user_inet.entrySet();
		 
	    // Obtaining an iterator for the entry set
	    Iterator it = entrySet.iterator();
	 
	    // Iterate through HashMap entries(Key-Value pairs)
	    System.out.println("HashMap Key-Value Pairs : ");
	    while(it.hasNext()){
	    	
	       Map.Entry me = (Map.Entry)it.next();
	       if(me.getValue().equals(inetAdd))
	       {
	    	   return (String)me.getKey();
	       }
	   
	   }
	    return null;
	}
}