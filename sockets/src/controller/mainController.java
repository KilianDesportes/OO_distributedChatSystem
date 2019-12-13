package controller;

import view.loginFrame;
import view.mainFrame;

public class mainController {
	
	private networkController networkController = new networkController();
	private String pseudo = "meh";
	private loginFrame f;

	public mainController() {
		
		f = new loginFrame(this);
		
		networkController.networkInit();
		
	}
	
	
	public boolean setSender(String s_pseudo) {
		boolean ret = false;
		System.out.println("New pseudo test");
		networkController.setSender(s_pseudo);

		if(networkController.isPseudoOk()) {
			this.pseudo = s_pseudo;
			ret = true;
		}
		return ret;

	}
	
	public void startApplication() {
		new mainFrame();
		f.setVisible(false);
	}
	
}
