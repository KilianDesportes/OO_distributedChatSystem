/**
 * MainController class, used to make link between data, network and view.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */

package controller;

import java.net.InetAddress;
<<<<<<< HEAD:sockets/src/controller/MainController.java
import java.net.UnknownHostException;
import java.util.HashMap;
=======
>>>>>>> d785e5bceed1169412b1eb30d3cb796da901840f:java_project/src/controller/MainController.java

import model.UserList;
import sockets.MessageSender;
import view.ConversationFrame;
import view.LoginFrame;
import view.MainFrame;

public class MainController {

	private NetworkController networkController;
	private MessageSender mSender;
	private LoginFrame loginFrame;
	private UserList userList;
	private MainFrame main_frame;
	private HashMap<InetAddress, ConversationFrame> tabConv = new HashMap<InetAddress, ConversationFrame>();

	public MainController() {

		this.loginFrame = new LoginFrame(this);

		this.userList = new UserList(this);

		this.userList.addUser("str1", null);
		this.userList.addUser("str2", null);
		this.userList.addUser("str3", null);
		this.userList.addUser("str4", null);
		this.userList.addUser("str55555555555555", null);

		this.mSender = new MessageSender();

		this.networkController = new NetworkController(this);
		this.networkController.start();

	}
	
	public void addConversation(InetAddress a, ConversationFrame b)
	{
		tabConv.put(a, b);
	}
	public void removeConversation(InetAddress add)
	{
		tabConv.remove(add);
	}

<<<<<<< HEAD:sockets/src/controller/MainController.java
	public boolean isConversation(InetAddress add)
	{
		return tabConv.containsKey(add);
		
	}
	
	public void createConv( InetAddress inetAdd )
	{
		String pseudo = userList.returnPseudo(inetAdd);
		if (pseudo != null)
		{
			new ConversationFrame(pseudo,inetAdd,this);
		}
		
	}
	
	public void msgReceived(String message_received, InetAddress inetAdr_sources)
	{
		tabConv.get(inetAdr_sources).append(message_received);
	}
	
	
=======
	/**
	 * 
	 * Add a given user into the local UserList.
	 * 
	 * @param name The name of the user you want to add into the UserList.
	 * @param adr  The IP address of the user you want to add into the UserList. of
	 *             the destination rectangle in pixels
	 * @see InetAddress
	 */
>>>>>>> d785e5bceed1169412b1eb30d3cb796da901840f:java_project/src/controller/MainController.java
	public void addUser(String name, InetAddress adr) {

		this.userList.addUser(name, adr);

		this.main_frame.loadUserList(this.userList);

	}

	/**
	 * 
	 * Remove a given user from the local UserList.
	 * 
	 * @param name The name of the user you want to add into the UserList.
	 */
	public void removeUser(String name) {

		this.userList.removeUser(name);

		this.main_frame.loadUserList(this.userList);

	}

	/**
	 * Update the UserList with the given one and update the main view with the new
	 * UserList.
	 * 
	 * @param uList The new UserList you want to be used.
	 */
	public void updateUL(UserList uList) {

		System.out.println("UserList update");

		this.userList = uList;

		this.main_frame.loadUserList(this.userList);

	}

	/**
	 * Start the main application (main view) after you get logged.
	 */
	public void startApplication() {

		main_frame = new MainFrame(this);
		this.main_frame.loadUserList(this.userList);
		loginFrame.setVisible(false);
	}

	/**
	 * Disconnect yourself from the application.
	 */
	public void disconnect() {

		this.networkController.disconnect();

		System.exit(0);

	}
	
	/**
	 * Return this local NetworkController.
	 * 
	 * @return The local NetworkController.
	 * @see NetworkController
	 */
	public NetworkController getNetworkController() {

		return this.networkController;

	}

	/**
	 * Check if this pseudo is already taken by someone else using the application.
	 * 
	 * @param pseudo Pseudo you want to check.
	 */
	public boolean isPseudoValid(String pseudo) {

		System.out.println("Main controller is pseudo valid");

		this.networkController.setStateCheck();

		boolean valid = true;

		String msg_to_send = "isPseudoValid>" + pseudo;

		mSender.sendMessageMulticast(msg_to_send);

		long time = System.currentTimeMillis();

		while (System.currentTimeMillis() - time < 3000 && !this.networkController.isConnected()) {

		}

		if (!this.networkController.isConnected()) {

			valid = false;

		} else {

			System.out.println("set pseudo");

			this.networkController.setPseudo(pseudo);
		}

		return valid;
	}



}