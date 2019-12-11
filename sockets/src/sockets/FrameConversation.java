package sockets;

import java.awt.*;
import java.net.*;
import javax.swing.*;

public class FrameConversation  {
	
	JFrame convFrame;
	
	
	public FrameConversation(String pseudo/*,InetAddress addIp*/)
	{
			
		convFrame = new JFrame(pseudo);
		convFrame .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		convFrame.setSize(new Dimension(350,500));
		
		convFrame.setLocationRelativeTo(null);	
		convFrame.setVisible(true);
	}

}
