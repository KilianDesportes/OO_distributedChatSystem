package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import controller.MainController;

public class LoginFrame extends JFrame {

	JFrame jframe_mainFrame;
	JPanel jpanel_mainPanel;
	JButton jbutConnect;
	JLabel jlabel_welcome;

	public LoginFrame(MainController c) {
		super("Char System Login");

		jpanel_mainPanel = new JPanel();
		jpanel_mainPanel.setLayout(new BorderLayout());
		jpanel_mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().add(jpanel_mainPanel);

		jlabel_welcome = new JLabel("Bienvenue sur notre chat system online!");
		jlabel_welcome.setFont(new Font("Courier", Font.BOLD, 20));

		jbutConnect = new JButton("Connect");

		jbutConnect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String pseudo = JOptionPane.showInputDialog(jframe_mainFrame, "What is your pseudo?", null);

				System.out.println("Pseudo entré = " + pseudo);

				boolean b = c.isPseudoValid(pseudo);

				if (!b) {

					JOptionPane.showMessageDialog(null, "Pseudo non correct", "Attention", JOptionPane.WARNING_MESSAGE);

				} else {

					c.startApplication();

				}
			}
		});

		jpanel_mainPanel.getRootPane().setDefaultButton(jbutConnect);

		jpanel_mainPanel.add(jlabel_welcome, BorderLayout.NORTH);
		jpanel_mainPanel.add(jbutConnect, BorderLayout.SOUTH);

		pack();
		setVisible(true);

	}

}