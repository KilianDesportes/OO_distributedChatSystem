/**
 * MajList class is used to manage Timer used in UserList HashMap containing Timers.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */

package model;

import java.util.TimerTask;

public class MajList extends TimerTask {

	private String pseudo;
	private UserList userList;

	public MajList(String pseudo, UserList ul) {
		this.pseudo = pseudo;
		this.userList = ul;
	}

	/**
	 * Override the run method of TimerTask. Used to remove the user from the user
	 * list if he or she hasn't shown up for the duration of the timer.
	 * 
	 * @see TimerTask
	 */
	@Override
	public void run() {

		this.userList.removeUser(pseudo);

		this.userList.getHashMapTimer().remove(pseudo);

		System.out.println(pseudo + " removed from timer list");

	}
}