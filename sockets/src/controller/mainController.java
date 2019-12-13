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
		System.out.println("mainController - setSender");
		boolean ret = false;
		networkController.setSender(s_pseudo);

		if(networkController.isPseudoOk()) {
			this.pseudo = s_pseudo;
			ret = true;
		}
		return ret;

	}
	
	public void startApplication() {
		System.out.println("mainController - startApplication");
		new mainFrame();
		f.setVisible(false);
	}
	
}
