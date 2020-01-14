package controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

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
	
	
	public void addUser(String name, InetAddress adr) {

		this.userList.addUser(name, adr);

		this.main_frame.loadUserList(this.userList);

	}

	public void removeUser(String name) {

		this.userList.removeUser(name);

		this.main_frame.loadUserList(this.userList);

	}

	public void updateUL(UserList uList) {

		System.out.println("UserList update");

		this.userList = uList;

		this.main_frame.loadUserList(this.userList);

	}

	public void startApplication() {

		main_frame = new MainFrame(this);
		this.main_frame.loadUserList(this.userList);
		loginFrame.setVisible(false);
	}

	public NetworkController getNetworkController() {

		return this.networkController;

	}

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

			this.networkController.setPseudo(pseudo);
		}

		return valid;
	}

	public void disconnect() {

		this.networkController.disconnect();

		System.exit(0);

	}

}
