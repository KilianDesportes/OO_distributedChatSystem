package sockets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class panelConversation extends JPanel {

	protected JLabel jlabel_name;
	protected JLabel jlabel_lastmsg;

	public panelConversation(String name, String message) {

		message = format_Label(message);

		jlabel_name = new JLabel(name);
		jlabel_name.setFont(new Font("Courier",Font.BOLD,15));
		jlabel_name.setBorder(BorderFactory.createEmptyBorder(10,0,5,0)); 

		jlabel_lastmsg = new JLabel(message);
		jlabel_lastmsg.setFont(new Font("Courier",0,12));
		jlabel_lastmsg.setBorder(BorderFactory.createEmptyBorder(5,0,10,0)); 


		setLayout(new BorderLayout());
		add(jlabel_name,BorderLayout.NORTH);
		add(jlabel_lastmsg,BorderLayout.SOUTH);

		setBorder(BorderFactory.createEmptyBorder(0,10,0,10)); 
		
		this.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	//Ouverture fenetre de conversation

		    }  
		}); 
	}

	private String format_Label(String message) {

		int str_length = message.length();

		if(str_length > 30) {	
			char[] char_msg = message.toCharArray();
			int y = 0;

			for(int i = 0 ; i < str_length ; i++) {

				if( y>=30 ) {
					if(char_msg[i] == ' ') {
						char_msg[i] = '\n';
						y=0;
					}
				}
				y++;
			}
			message = String.valueOf(char_msg);
		}


		StringBuilder str_builder = new StringBuilder("");
		str_builder.append("<html>").append(message).append("</html>");
		String final_msg = str_builder.toString().replace("\n", "<br>");

		return final_msg;
	}

}
