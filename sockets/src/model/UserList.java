package model;

import java.net.InetAddress;
import java.util.*;
/////////// Mettre a jour les timers si on recoit un "Je suis "X"."
public class UserList {

	protected HashMap<String,InetAddress> hm_user;
	protected HashMap<String,Timer> majUser; //HM pseudo + timer associ�
	

	public UserList() {
		this.hm_user = new HashMap<String,InetAddress>();
		this.majUser = new HashMap<String,Timer>();
	}

	public void addUser(String str_pseudo, InetAddress adr_user) {
		if ( hm_user.put(str_pseudo, adr_user)== null )
		{
			Timer tim = new Timer();
			tim.schedule(new MajList(str_pseudo),60000);

			majUser.put(str_pseudo,tim);
			//System.out.println("_____");
			//System.out.println("User " + str_pseudo + " added.");
			//System.out.println("List:");
			for (String key : hm_user.keySet()) {
				//System.out.println(key + " at adress " + hm_user.get(key));
			}
			//System.out.println("_____");
		}
		else
		{
			majUser.get(str_pseudo).cancel();
			Timer tim = new Timer();
			tim.schedule(new MajList(str_pseudo),60000);
			majUser.put(str_pseudo,tim);
			System.out.println("MAJ timer");
		}
	}

	public void printUserList() {
		for (String key : hm_user.keySet()) {
		    System.out.println("Name : " + key + " | Address : " + hm_user.get(key));
		    System.out.flush();
		}
	}

	public InetAddress getAdr(String str_pseudo) {
		InetAddress returned_adr = null;
		if(hm_user.containsKey(str_pseudo)) {
			returned_adr = hm_user.get(str_pseudo);
		}
		return returned_adr;
	}

	public void removeUser(String str) {
		if(hm_user.containsKey(str)) {
			hm_user.remove(str);
		}
	}

	
	class MajList extends TimerTask {
	
		String pseudo;
		
		public MajList(String pseudo)
		{
			this.pseudo = pseudo;
		}
		public void run() 
		{
			majUser.remove(pseudo);
			System.out.println(pseudo + " deleted");
		}
	}
}