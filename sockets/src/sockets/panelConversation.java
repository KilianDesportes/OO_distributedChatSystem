package sockets;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class panelConversation extends JPanel {

	protected JLabel jlabel_name;
	protected JLabel jlabel_lastmsg;

	public panelConversation(String name, String message) {
		
		int str_length = message.length();
		int i = 0;
		if(str_length > 40) {
			
		}
		
		jlabel_name = new JLabel(name);
		jlabel_name.setFont(new Font("Courier",Font.BOLD,15));
		jlabel_name.setBorder(BorderFactory.createEmptyBorder(0,0,4,0)); 

		
		jlabel_lastmsg = new JLabel(message);
		jlabel_lastmsg.setFont(new Font("Courier",0,12));


		setLayout(new BorderLayout());
		add(jlabel_name,BorderLayout.NORTH);
		add(jlabel_lastmsg,BorderLayout.SOUTH);

		setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); 

	}

}
