package view;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

<<<<<<< HEAD
import java.util.*;
import java.net.*;
import java.time.LocalTime;

import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
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
	// private int offset =0;
	private MainController mController;
	private InetAddress dest;

	public ConversationFrame(String pseudo, InetAddress addIp, MainController mController) {

		this.mController = mController;
		this.dest = addIp;
		System.out.println("Ip = " + dest);

		pseudo = this.pseudo;
=======
import javax.swing.*;
import javax.swing.text.*;

public class ConversationFrame {

	JPanel panel;
	JFrame convFrame;
	JTextPane convMessage;
	JTextArea convEcriture;
	JScrollPane scroll;
	JScrollPane scroll2;
	JButton enter;
	GridBagConstraints constraints;
	int offset = 0;

	public ConversationFrame(String pseudo/* ,InetAddress addIp */) {
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0
		this.convFrame = new JFrame(pseudo);
		this.convFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.convFrame.setSize(350, 500);

		this.panel = new JPanel(new GridBagLayout());
		this.panel.setPreferredSize(new Dimension(345, 500));

		addWidget();
<<<<<<< HEAD

		this.send.setMnemonic(KeyEvent.VK_ENTER);
		this.send.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String text = convEcriture.getText();
				System.out.println("Text= [" + text + "]");
				if (text.compareTo("") != 0) {
					convEcriture.setText("");
					append("[" + getTime(":", "-", "/") + "] : " + text + "\n", Color.blue);
				}
			}
		});

=======
		this.enter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String text = convEcriture.getText();
				if (text != null) {
					System.out.println("Text=" + text + "FinText");
					convEcriture.setText("");
					append("[" + getTime(":", "-", "/") + "] : " + text + "\n", Color.blue);
					// convMessage.setSelectedTextColor(Color.BLUE);
					// convMessage.append("["+getTime(":","-","/") + "] : " + text + "\n");
				}
			}

		});
		/*
		 * this.conversation = new JTextArea(); this.conversation.setEditable(false);
		 * this.conversation.setLineWrap(true); // Autoriser le retour a la ligne
		 * this.conversation.setWrapStyleWord(true);
		 * this.conversation.setPreferredSize(new Dimension(330,400));
		 * 
		 * 
		 * this.area = new JTextArea(); this.area.setPreferredSize(new
		 * Dimension(330,100)); this.area.setLineWrap(true); // Autoriser le retour a la
		 * ligne
		 * 
		 * JScrollPane scroll = new JScrollPane
		 * (conversation,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.
		 * HORIZONTAL_SCROLLBAR_NEVER);
		 * 
		 * this.panel.add(scroll);
		 * 
		 * 
		 * this.convFrame.getContentPane().add(panel); //this.panel.add(conversation,
		 * BorderLayout.NORTH); //this.panel.add(area,BorderLayout.NORTH);
		 */
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0
		this.convFrame.getContentPane().add(panel, BorderLayout.CENTER);
		this.convFrame.pack();
		this.convFrame.setResizable(false);
		this.convFrame.setVisible(true);
<<<<<<< HEAD
		getHistory();

	}

	void getHistory() {

		String file_ipAdr = this.dest.getHostAddress().replace('.', '_') + ".txt";

		try {

			File fileHistory = new File(file_ipAdr);

			try {

				if (!fileHistory.exists()) {

					BufferedReader bReader = new BufferedReader(new FileReader(
							fileHistory));
					String line;
					while ((line = bReader.readLine()) != null) {
						String[] tab = line.split(";");
						System.out.println(tab[0] + " " + tab[1] + " " + tab[2] + " " + tab[3]);
						if (tab[0].compareTo("Kilian") == 0) {
							append(tab[2], tab[3], Color.RED);
						} else {
							append(tab[2], tab[3], Color.BLUE);
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
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void append(String msg1, String date, Color c) // Load history
	{
		String msg = date + " " + msg1 + "\n";
		try {
			final StyledDocument doc = convMessage.getStyledDocument();
			Style style = doc.addStyle("Style", null);
			StyleConstants.setForeground(style, c);
			convMessage.getDocument().insertString(convMessage.getDocument().getLength(), msg, style);
=======

	}

	public void append(String msg, Color c) {
		try {
			convMessage.getDocument().insertString(convMessage.getDocument().getLength(), msg, null);
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

<<<<<<< HEAD
	public void append(String msg, Color c) { // Send message

		mController.getNetworkController().sendMessageUDP(msg, dest);
		mController.getNetworkController().writeFileSend(msg, dest);
		try {
			final StyledDocument doc = convMessage.getStyledDocument();
			Style style = doc.addStyle("Style", null);
			StyleConstants.setForeground(style, c);
			convMessage.getDocument().insertString(convMessage.getDocument().getLength(), msg, style);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

=======
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0
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

	void addWidget() {
<<<<<<< HEAD

		this.convMessage = new JTextPane();
		this.convMessage.setEditable(false);
=======
		/*
		 * this.textField = new JTextField(2); this.textField.setPreferredSize(new
		 * Dimension(330,100));
		 */

		this.convMessage = new JTextPane();
		this.convMessage.setEditable(false);

		// this.convMessage.setLineWrap(true); // Autoriser le retour a la ligne
		// this.convMessage.setWrapStyleWord(true);
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0
		this.convMessage.setPreferredSize(new Dimension(330, 400));

		this.convEcriture = new JTextArea();
		this.convEcriture.setLineWrap(true);
		this.convEcriture.setWrapStyleWord(true);
		this.convEcriture.setPreferredSize(new Dimension(330, 400));

<<<<<<< HEAD
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
=======
		this.enter = new JButton("Enter");
		this.enter.setPreferredSize(new Dimension(100, 50));

		this.scroll = new JScrollPane(convMessage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.scroll2 = new JScrollPane(convEcriture, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.constraints = new GridBagConstraints();

		this.constraints.fill = GridBagConstraints.BOTH; // remplir l�espace offert, horizontalement et verticalement
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

		this.panel.add(enter, constraints);
	}

	public void actionPerformed(ActionEvent event) {

	}

}

/*
 * this.convFrame = new JFrame(pseudo); this.conversation = new JTextArea();
 * this.convFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); this.panel =
 * new JPanel();
 * 
 * this.convFrame.setSize(new Dimension(350,500));
 * 
 * this.conversation.setEditable(false); this.conversation.setLineWrap(true);
 * this.conversation.setWrapStyleWord(true);
 * 
 * this.area = new JTextArea(); this.area.setPreferredSize(new
 * Dimension(350,50)); this.area.setLineWrap(true); // Autoriser le retour � la
 * ligne
 * 
 * JScrollPane scroll = new JScrollPane
 * (area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.
 * HORIZONTAL_SCROLLBAR_NEVER);
 * 
 * this.panel.add(scroll); try { getHistory("OK"); } catch (Exception e) {
 * System.out.println("Echec getHistory"); }
 * 
 * this.convFrame.setContentPane(panel);
 * this.panel.add(conversation,BorderLayout.NORTH);
 * this.panel.add(area,BorderLayout.SOUTH); this.panel.setVisible(true); }
 * 
 * 
 * 
 * void getHistory(String IPpseudo ) { try { BufferedReader bReader = new
 * BufferedReader(new
 * FileReader("C:\\Users\\yimek\\Desktop\\" + IPpseudo +".txt")); String line;
 * while ((line = bReader.readLine()) != null) { String[] tab = line.split(";");
 * System.out.println(tab[0] + " "+ tab[1] +" "+ tab[2]+" " + tab[3]); if (
 * tab[0]== "Killian") { JLabel message = new JLabel(tab[2]);
 * message.setBackground(Color.cyan); //message.setToolTipText(tab[3]);
 * System.out.println("Affichage bleu"); this.convFrame.add(message); } else {
 * JLabel message = new JLabel(tab[2]); message.setBackground(Color.yellow);
 * 
 * System.out.println("Affichage jaune"); //message.setToolTipText(tab[3]);
 * 
 * conversation.append(localTime + " : " + message + "\n"); } }
 * 
 * bReader.close(); } catch (Exception e) {
 * System.out.println("File not Found"); }
 * convFrame.setLocationRelativeTo(null); convFrame.setVisible(true); }
 * 
 * /*public void actionPerformed(ActionEvent event) { //Parse degrees Celsius as
 * a double and convert to Fahrenheit. String text = area.getText(); localTime =
 * LocalTime.now(); area.setText(""); conversation.append(localTime + " : " +
 * text + "\n"); }
 * 
 * }
 */
>>>>>>> a379e4cb06a536d62fd480efa65898c6ad00c2a0
