/**
 * MainFrame is a view class used to show every users connected.
 * When a click on one user name is performed, it open a conversation with this user.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */
package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import controller.MainController;
import model.UserList;

public class MainFrame extends JFrame {

	private MainController maincontroller_app;

	private JMenuBar menuBar = new JMenuBar();
	private JMenu jmenu_options = new JMenu("Options");
	private JMenuItem jmenuitem_refresh = new JMenuItem("Refresh");
	private JMenuItem jmenuitem_disconnect = new JMenuItem("Disconnect");

	private JPanel jpanel_users = new JPanel();

	private HashMap<String, InetAddress> user_hm_array;

	private ArrayList<String> user_array = new ArrayList<String>();

	public MainFrame(MainController mc_appli) {

		this.maincontroller_app = mc_appli;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.menuBarLoad();

		jpanel_users.setLayout(new BoxLayout(jpanel_users, BoxLayout.Y_AXIS));
		jpanel_users.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		this.add(jpanel_users);

		this.refreshUI();

		this.setVisible(true);
	}

	/**
	 * Method used to create the menu bar of the frame.
	 */
	private void menuBarLoad() {

		this.jmenu_options.add(jmenuitem_disconnect);
		jmenuitem_disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				maincontroller_app.disconnect();

			}
		});

		this.jmenu_options.addSeparator();
		this.jmenu_options.add(jmenuitem_refresh);
		jmenuitem_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				refreshUI();

			}
		});

		this.menuBar.add(jmenu_options);

		this.setJMenuBar(menuBar);

	}

	/**
	 * Method used to load an user list and show it into the frame. This method is
	 * called when a change occurs on the MainController user list and it need to be
	 * showed into the view.
	 * 
	 * @param ul UserList which will be loaded and showed.
	 */
	public void loadUserList(UserList ul) {

		this.user_hm_array = ul.getHashMapUser();

		this.user_array = new ArrayList<String>();

		Iterator it = ul.getHashMapUser().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String username = pair.getKey().toString();
			System.out.println("Username added = " + username);
			this.user_array.add(username);
		}

		this.refreshUI();

	}

	/**
	 * Method used to refresh the view with the current connected users.
	 */
	private void refreshUI() {

		this.jpanel_users.removeAll();

		System.out.println("MainFrame Refresh");

		Iterator<String> itr = this.user_array.iterator();

		while (itr.hasNext()) {

			String element = itr.next();

			System.out.print(element + " ");

			UserButton user_button = new UserButton(element);

			user_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					InetAddress inet_user = user_hm_array.get(element);

					System.out.println("New conversation frame -> " + element + " - " + inet_user.toString());

					new ConversationFrame(element, inet_user, maincontroller_app);

				}
			});

			jpanel_users.add(user_button);

			if (itr.hasNext() == true) {
				jpanel_users.add(new JSeparator());
			}

		}

		this.pack();

	}

}