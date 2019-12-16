package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalTime;

import java.util.*;
import java.net.*;
import java.time.LocalTime;
import javax.swing.*;
import java.io.*;


public class ConversationFrame  {

	JPanel panel;
	JFrame convFrame;
	JTextArea convMessage;
	JTextArea convEcriture;
	LocalTime localTime;
	JScrollPane scroll;
	JScrollPane scroll2;
	JButton enter;
	GridBagConstraints constraints;
	
	
	public ConversationFrame(String pseudo/*,InetAddress addIp*/)
	{
		this.convFrame = new JFrame(pseudo);
		this.convFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.convFrame.setSize(350,500);
		
		this.panel = new JPanel(new GridBagLayout());
		this.panel.setPreferredSize(new Dimension(345,500));
		
		addWidget();
		//this.enter.addActionListener(this);
		/*this.conversation = new JTextArea();
		this.conversation.setEditable(false);
		this.conversation.setLineWrap(true); // Autoriser le retour a la ligne
		this.conversation.setWrapStyleWord(true);
		this.conversation.setPreferredSize(new Dimension(330,400));
		
		
		this.area = new JTextArea();
		this.area.setPreferredSize(new Dimension(330,100));
		this.area.setLineWrap(true); // Autoriser le retour a la ligne
		
		JScrollPane scroll = new JScrollPane (conversation,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

		this.panel.add(scroll);
		
		
		this.convFrame.getContentPane().add(panel);
		//this.panel.add(conversation, BorderLayout.NORTH);
		//this.panel.add(area,BorderLayout.NORTH);*/
		this.convFrame.getContentPane().add(panel,BorderLayout.CENTER);
		this.convFrame.pack();
		this.convFrame.setVisible(true);
	
	}
	
	void addWidget()
	{
		/*this.textField =  new JTextField(2);
		this.textField.setPreferredSize(new Dimension(330,100));*/

		this.convMessage = new JTextArea();
		this.convMessage.setEditable(false);
		this.convMessage.setLineWrap(true); // Autoriser le retour a la ligne
		this.convMessage.setWrapStyleWord(true);
		this.convMessage.setPreferredSize(new Dimension(330,400));
		
		
		this.convEcriture = new JTextArea();
		this.convEcriture.setLineWrap(true); // Autoriser le retour a la ligne
		this.convEcriture.setWrapStyleWord(true);
		this.convEcriture.setPreferredSize(new Dimension(330,400));
		
		
		this.enter = new JButton("Enter");	
		this.enter.setPreferredSize(new Dimension(100,50));
		
		
		this.scroll = new JScrollPane (convMessage,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		this.scroll2 = new JScrollPane (convEcriture,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		this.constraints = new GridBagConstraints();

		
		this.constraints.fill = GridBagConstraints.BOTH; // remplir l’espace offert, horizontalement et verticalement
    	this.constraints.weighty = 0.85;  
    	this.constraints.weightx = 1;
    	this.constraints.anchor = GridBagConstraints.CENTER; 
    	this.constraints.insets = new Insets(2,2,0,2); 
    	this.constraints.gridx = 0;   	
    	this.constraints.gridwidth = 2;   
    	this.constraints.gridy = 0;   	
		
    	this.panel.add(scroll,constraints);
		
		this.constraints.fill = GridBagConstraints.BOTH;
    	this.constraints.weighty = 0.15;  
    	this.constraints.weightx = 0.8;
    	this.constraints.anchor = GridBagConstraints.LAST_LINE_END; 
    	this.constraints.insets = new Insets(2,1,2,1);  
    	this.constraints.gridx = 0;   	
    	this.constraints.gridwidth = 1;   
    	this.constraints.gridy = 1;   	
    	
		this.panel.add(scroll2,constraints);		
		
		this.constraints.fill = GridBagConstraints.BOTH;
    	this.constraints.weighty = 0.15;  
    	this.constraints.weightx = 0.2;
    	this.constraints.anchor = GridBagConstraints.LAST_LINE_END;
    	this.constraints.insets = new Insets(1,1,2,0);  
    	this.constraints.gridx = 1;   	
    	this.constraints.gridwidth = 1;  
    	this.constraints.gridy = 1;   	
    	
		this.panel.add(enter,constraints);	
	}
	
	public void actionPerformed(ActionEvent event) {
    	String text = convEcriture.getText();
    	if (text != "")
    	{
    		localTime= LocalTime.now();
    		convEcriture.setText("");
    		convMessage.append(localTime + " : " + text + "\n");
    	}
    	
	}

}

		/*this.convFrame = new JFrame(pseudo);
		this.conversation = new JTextArea();
		this.convFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panel = new JPanel();

		this.convFrame.setSize(new Dimension(350,500));

		this.conversation.setEditable(false);
		this.conversation.setLineWrap(true);
		this.conversation.setWrapStyleWord(true);

		this.area = new JTextArea();
		this.area.setPreferredSize(new Dimension(350,50));
		this.area.setLineWrap(true); // Autoriser le retour ï¿½ la ligne

		JScrollPane scroll = new JScrollPane (area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

		this.panel.add(scroll);
		try {
			getHistory("OK");
		}
		catch (Exception e)
		{
			System.out.println("Echec getHistory");
		}

		this.convFrame.setContentPane(panel);
		this.panel.add(conversation,BorderLayout.NORTH);
		this.panel.add(area,BorderLayout.SOUTH);
		this.panel.setVisible(true);
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
		convFrame.setLocationRelativeTo(null);	
		convFrame.setVisible(true);
	}

	/*public void actionPerformed(ActionEvent event) {
    	//Parse degrees Celsius as a double and convert to Fahrenheit.
    	String text = area.getText();
    	localTime = LocalTime.now();
    	area.setText("");
    	conversation.append(localTime + " : " + text + "\n");
	}

}
*/