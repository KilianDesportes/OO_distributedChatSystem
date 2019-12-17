package controller;

import model.UserList;
import sockets.MessageSender;
import view.LoginFrame;
import view.MainFrame;

public class MainController {

	private NetworkController networkController;
	private MessageSender mSender;
	private LoginFrame f;

	private UserList userList;

	public MainController() {

		f = new LoginFrame(this);

		this.userList = new UserList();
		this.mSender = new MessageSender();

		this.networkController = new NetworkController(this.userList);
		this.networkController.start();

	}

	public void startApplication() {

		new MainFrame();
		f.setVisible(false);

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

		while (System.currentTimeMillis() - time < 2500 && !this.networkController.isConnected()) {
			
		}

		if ( !this.networkController.isConnected() ) {
			
			valid = false;

		}

		return valid;
	}

}
