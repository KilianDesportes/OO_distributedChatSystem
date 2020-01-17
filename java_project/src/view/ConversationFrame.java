/**
 * ConversationFrame is a view class used to show a conversation between two users.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */
package view;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;

import controller.MainController;

import java.io.*;

public class ConversationFrame {

	private JPanel panel;
	private String pseudo;
	private JFrame convFrame;
	private JTextPane convMessage;
	private JTextArea convEcriture;
	private JScrollPane scroll;
	private JScrollPane scroll2;
	private JButton send;
	private GridBagConstraints constraints;
	private MainController mController;
	private InetAddress dest;

	public ConversationFrame(String pseudo, InetAddress addIp, MainController mController) {

		this.mController = mController;
		this.dest = addIp;
		System.out.println("Ip = " + dest);

		this.pseudo = pseudo;
		this.convFrame = new JFrame();
		this.convFrame.setTitle(pseudo);
		this.convFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.convFrame.setSize(350, 500);
		this.convFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mController.removeConversation(dest);
			}
		});

		this.panel = new JPanel(new GridBagLayout());
		this.panel.setPreferredSize(new Dimension(345, 500));

		addWidget();

		this.send.setMnemonic(KeyEvent.VK_ENTER);
		this.send.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String text = convEcriture.getText();
				System.out.println("Text= [" + text + "]");
				if (text.compareTo("") != 0) {
					convEcriture.setText("");
					appendSend("[" + getTime(":", "-", "/") + "] : " + text + "\n");
				}
			}
		});

		this.convFrame.getContentPane().add(panel, BorderLayout.CENTER);
		this.convFrame.pack();
		this.convFrame.setResizable(false);
		this.convFrame.setVisible(true);
		getHistory();

	}

	/**
	 * Method used to get the history (old messages) of a conversation between these
	 * two users and show it into the frame.
	 */
	void getHistory() {

		new File("HISTORY").mkdirs();

		String file_ipAdr = "HISTORY" + File.separator + this.dest.getHostAddress().replace('.', '_') + ".txt";

		try {

			File fileHistory = new File(file_ipAdr);

			try {

				if (fileHistory.exists()) {

					BufferedReader bReader = new BufferedReader(new FileReader(fileHistory));
					String line;
					while ((line = bReader.readLine()) != null) {
						String[] tab = line.split(";");
						System.out.println(tab[0] + " " + tab[1] + " " + tab[2] + " " + tab[3]);
						if (tab[0].toString().compareTo(dest.getHostAddress().toString()) == 0) {
							messageColor(tab[3], Color.RED);
						} else {
							messageColor(tab[3], Color.BLUE);
						}
					}

					bReader.close();
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}

			convFrame.setLocationRelativeTo(null);
			convFrame.setVisible(true);

		} catch (

		Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Met
	 */
	public void messageColor(String msg1, Color c) // Load history
	{
		String msg = msg1 + "\n";
		try {
			final StyledDocument doc = convMessage.getStyledDocument();
			Style style = doc.addStyle("Style", null);
			StyleConstants.setForeground(style, c);
			convMessage.getDocument().insertString(convMessage.getDocument().getLength(), msg, style);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Method used to show a sent message to the conversation frame
	 * 
	 * @param msg Message to be shown
	 */
	public void appendSend(String msg) {

		mController.getNetworkController().sendMessageUDP(msg, dest);
		try {
			final StyledDocument doc = convMessage.getStyledDocument();
			Style style = doc.addStyle("Style", null);
			StyleConstants.setForeground(style, Color.BLUE);
			convMessage.getDocument().insertString(convMessage.getDocument().getLength(), msg, style);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Method used to show a received message to the conversation frame
	 * 
	 * @param msg Message to be shown
	 */
	public void appendRecv(String msg) {

		try {
			final StyledDocument doc = convMessage.getStyledDocument();
			Style style = doc.addStyle("Style", null);
			StyleConstants.setForeground(style, Color.RED);
			convMessage.getDocument().insertString(convMessage.getDocument().getLength(), msg, style);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * Method used to get the history (old messages) of a conversation between these
	 * two users and show it into the frame.
	 */
	private String getTime(String separatorHour, String separatorHourDate, String separatorDate) {

		int year = LocalDateTime.now().getYear();
		int month = LocalDateTime.now().getMonthValue();
		int day = LocalDateTime.now().getDayOfMonth();
		int hour = LocalDateTime.now().getHour();
		int min = LocalDateTime.now().getMinute();

		String str_hour = "" + hour + separatorHour + min + separatorHourDate + day + separatorDate + month
				+ separatorDate + year;

		return str_hour;
	}

	/**
	 * Method used to get the history (old messages) of a conversation between these
	 * two users and show it into the frame.
	 */
	void addWidget() {

		this.convMessage = new JTextPane();
		this.convMessage.setEditable(false);
		this.convMessage.setPreferredSize(new Dimension(330, 400));

		this.convEcriture = new JTextArea();
		this.convEcriture.setLineWrap(true);
		this.convEcriture.setWrapStyleWord(true);
		this.convEcriture.setPreferredSize(new Dimension(330, 400));

		this.send = new JButton("Send");
		this.send.setPreferredSize(new Dimension(100, 50));

		this.scroll = new JScrollPane(convMessage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.scroll2 = new JScrollPane(convEcriture, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.constraints = new GridBagConstraints();

		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.weighty = 0.85;
		this.constraints.weightx = 1;
		this.constraints.anchor = GridBagConstraints.CENTER;
		this.constraints.insets = new Insets(2, 2, 0, 2);
		this.constraints.gridx = 0;
		this.constraints.gridwidth = 2;
		this.constraints.gridy = 0;

		this.panel.add(scroll, constraints);

		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.weighty = 0.15;
		this.constraints.weightx = 0.8;
		this.constraints.anchor = GridBagConstraints.LAST_LINE_END;
		this.constraints.insets = new Insets(2, 1, 2, 1);
		this.constraints.gridx = 0;
		this.constraints.gridwidth = 1;
		this.constraints.gridy = 1;

		this.panel.add(scroll2, constraints);

		this.constraints.fill = GridBagConstraints.BOTH;
		this.constraints.weighty = 0.15;
		this.constraints.weightx = 0.2;
		this.constraints.anchor = GridBagConstraints.LAST_LINE_END;
		this.constraints.insets = new Insets(1, 1, 2, 0);
		this.constraints.gridx = 1;
		this.constraints.gridwidth = 1;
		this.constraints.gridy = 1;

		this.panel.add(send, constraints);
	}

}