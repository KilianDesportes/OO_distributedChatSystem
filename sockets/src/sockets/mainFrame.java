package sockets;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;

public class mainFrame extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu jmenu_options = new JMenu("Options");
	private JMenu jmenu_help = new JMenu("Help");

	private JMenuItem jmenuitem_pseudo = new JMenuItem("Change pseudo");
	private JMenuItem jmenuitem_disconnect = new JMenuItem("Disconnect");
	private JMenuItem jmenuitem_quit = new JMenuItem("Quit");
	private JMenuItem jmenuitem_refresh = new JMenuItem("Force Refresh");


	private JPanel jpanel_msgs = new JPanel();

	public mainFrame(){
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.setLayout(new BorderLayout());

		jpanel_msgs.setLayout(new BoxLayout(jpanel_msgs, BoxLayout.Y_AXIS));
		
		String msg_test = "Salut, comment ca va aujourd'hui? J'aimerais que l'on puisse avancer sur les interfaces graphiques en POO ca serait cool qu'on se voit d'ici la fin de la semaine.";

		JScrollPane scrollPane = new JScrollPane(jpanel_msgs);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(50, 30, 300, 50);
		
		panelConversation p = new panelConversation("Michel",msg_test);
		
		jpanel_msgs.add(p);
		
		jpanel_msgs.add(new JSeparator());
		jpanel_msgs.add(new panelConversation("Michel","yo"));

		jpanel_msgs.add(new JSeparator());
		jpanel_msgs.add(new panelConversation("Michel",msg_test));
		


		this.add(jpanel_msgs);


		this.jmenuitem_quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});

		this.jmenu_options.add(jmenuitem_pseudo);
		this.jmenu_options.add(jmenuitem_disconnect);
		this.jmenu_options.addSeparator();
		this.jmenu_options.add(jmenuitem_refresh);
		this.jmenu_options.addSeparator();
		this.jmenu_options.add(jmenuitem_quit);

		jmenuitem_quit.setAccelerator(KeyStroke.getKeyStroke('q'));
		jmenuitem_refresh.setAccelerator(KeyStroke.getKeyStroke('f'));

		this.menuBar.add(jmenu_options);  
		this.menuBar.add(jmenu_help);  

		this.setJMenuBar(menuBar);

		this.pack();
		this.setVisible(true);
	}
}