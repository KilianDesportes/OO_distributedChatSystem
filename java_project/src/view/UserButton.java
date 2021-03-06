/**
 * These buttons will be used in MainFrame class, and will open 
 * a ConversationFrame when clicked.
 * 
 * @author      Desportes Kilian
 * @author      Imekraz Yanis
 * @version 	1.0
 * @since   	10-01-2020
 */
package view;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class UserButton extends JButton {

	public UserButton(String name) {

		super(name);
		setFont(new Font("Courier", Font.BOLD, 15));
		setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		setFocusPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setContentAreaFilled(false);
		setBorderPainted(false);
		setOpaque(false);

	}

}
