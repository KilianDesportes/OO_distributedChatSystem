package sockets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class loginFrame extends JFrame {

	JFrame jframe_mainFrame;
	JPanel jpanel_mainPanel;
	JPanel jpanel_pseudo;
	JButton jbutConnect;
	JTextField jtextfield_pseudo;
	JLabel jlabel_welcome;
	JLabel jlabel_pseudo;

	public loginFrame()
	{
		super("Char System Login");


		jpanel_mainPanel = new JPanel();
		jpanel_mainPanel.setLayout(new BorderLayout());
		jpanel_mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		jpanel_pseudo = new JPanel();
		jpanel_pseudo.setLayout(new GridLayout(2,1));
		jpanel_pseudo.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 


		getContentPane().add(jpanel_mainPanel);

		jlabel_welcome = new JLabel("Bienvenue sur notre chat system online!");
		jlabel_welcome.setFont(new Font("Courier",Font.BOLD,20));

		jlabel_pseudo = new JLabel("Entrez votre pseudonyme");
		
		JTextField jtextfield_pseudo = new JTextField();  
		jtextfield_pseudo.setToolTipText("Please enter your pseudo here");
		jtextfield_pseudo.setHorizontalAlignment(JTextField.CENTER);
		jpanel_pseudo.add(jlabel_pseudo);
		jpanel_pseudo.add(jtextfield_pseudo);
		
		jbutConnect = new JButton("Connect");

		jpanel_mainPanel.add(jlabel_welcome,BorderLayout.NORTH);
		jpanel_mainPanel.add(jpanel_pseudo,BorderLayout.CENTER);
		jpanel_mainPanel.add(jbutConnect,BorderLayout.SOUTH);

		pack();
		setVisible(true);

	}


}