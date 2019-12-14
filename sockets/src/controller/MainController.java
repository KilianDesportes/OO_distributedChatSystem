package controller;

import model.UserList;
import view.LoginFrame;
import view.MainFrame;

public class MainController {

	private NetworkController networkController;
	private LoginFrame f;

	private UserList userList;

	public MainController() {

		f = new LoginFrame(this);

		this.userList = new UserList();

		networkController = new NetworkController(this.userList);
		networkController.startReceptionThread();

	}

	public void startApplication() {

		new MainFrame();
		f.setVisible(false);

	}
	
	public NetworkController getNetworkController() {
		
		return this.networkController;
		
	}

	public boolean isPseudoValid(String pseudo) {

		boolean pseudo_valid = true;

		if (pseudo.compareTo("faux") == 0) {

			pseudo_valid = false;

		}

		return pseudo_valid;
	}

}
