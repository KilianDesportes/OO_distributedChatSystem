package view;

import java.awt.*;
import java.util.*;
import java.net.*;
import java.time.LocalTime;
import javax.swing.*;
import java.io.*;


public class FrameConversation  {

	JPanel panel;
	JFrame convFrame;
	JTextArea conversation;
	JTextArea area;
	JTextField textField;
	LocalTime localTime;



	public FrameConversation(String pseudo/*,InetAddress addIp*/)
	{

		this.convFrame = new JFrame(pseudo);
		this.conversation = new JTextArea();
		this.convFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.convFrame.setSize(new Dimension(350,500));
		
		convFrame.setLocationRelativeTo(null);	
		convFrame.setVisible(true);
		
		
		this.conversation.setEditable(false);
		this.conversation.setLineWrap(true);
		this.conversation.setWrapStyleWord(true);

		this.area = new JTextArea();
		this.area.setPreferredSize(new Dimension(350,50));
		this.area.setLineWrap(true); // Autoriser le retour � la ligne

		JScrollPane scroll = new JScrollPane (area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		this.convFrame.add(scroll);
		try {
			getHistory("OK");
		}
		catch (Exception e)
		{
			System.out.println("Echec getHistory");
		}
		
		this.convFrame.getContentPane().add(conversation,BorderLayout.NORTH);
		this.convFrame.getContentPane().add(area,BorderLayout.SOUTH);
		this.convFrame.setVisible(true);
	}



	void getHistory(String IPpseudo ) 
	{
		try 
		{
			BufferedReader bReader = new BufferedReader(new FileReader("C:\\Users\\yimek\\Desktop\\JJ.txt"));
			String line;
			while ((line = bReader.readLine()) != null)
			{
				String[] tab = line.split(";");
				System.out.println(tab[0] + " "+ tab[1] +" "+ tab[2]+" " + tab[3]);
				if ( tab[0]== "Killian")
				{
					JLabel message = new JLabel(tab[2]); 
					message.setBackground(Color.cyan);
					//message.setToolTipText(tab[3]);
					System.out.println("Affichage bleu");
					this.convFrame.add(message);
				}
				else
				{
					JLabel message = new JLabel(tab[2]); 
					message.setBackground(Color.yellow);

					System.out.println("Affichage jaune");
					//message.setToolTipText(tab[3]);

			    	conversation.append(localTime + " : " + message + "\n");
				}
			}

			bReader.close();
		}
		catch (Exception e)
		{
			System.out.println("File not Found");
		}
	}
	
	/*public void actionPerformed(ActionEvent event) {
    	//Parse degrees Celsius as a double and convert to Fahrenheit.
    	String text = area.getText();
    	localTime = LocalTime.now();
    	area.setText("");
    	conversation.append(localTime + " : " + text + "\n");
	}*/

}
