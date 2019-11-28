import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

	JFrame jframe_mainFrame;
	JPanel jpanel_mainPanel;
	JButton jbutConnect;
	JTextField jtextfield_pseudo;
	
	public MainFrame()
	{
		 //Create and set up the window.
		jframe_mainFrame = new JFrame("Chat System Connection");
		jframe_mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe_mainFrame.setLocationRelativeTo(null);

        //Create and set up the panel.
		jpanel_mainPanel = new JPanel();

        //Add the panel to the window.
        jframe_mainFrame.getContentPane().add(jpanel_mainPanel);
        jbutConnect = new JButton("Connect");
        jtextfield_pseudo = new JTextField("Enter your Pseudo");

        jpanel_mainPanel.add(jtextfield_pseudo);
        jpanel_mainPanel.add(jbutConnect);
        

        //Display the window.
        jframe_mainFrame.pack();
        jframe_mainFrame.setVisible(true);
	
	}
	
	
}
