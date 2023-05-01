package RockPaperScissors;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Rectangle;

/**
 * 
 * //Creators: Seth DeWalt, Tyler Menzie
 * // Tyler Menzie: B01269607
// Seth DeWalt: B01223206
 * This Class sets up the actual ClientGUI interface 
 * that allows the user to see a game of Rock Paper 
 * Scissors. 
 * 
 * The GUI contains 3 buttons respectively pertaining to Rock,
 * Paper, or Scissors while also containing a QUIT button to allow
 * for the user to exit the game at any time once the initial connection
 * has been made.
 * 
 * The GUI also contains a panel that includes the Rules of the game
 * 
 * The GUI contains a Client Notification Panel that send the client 
 * any information that pertains to the game. It displays when they are connected, what
 * choice they have chose and what the outcome is of the game, if they won or lost or if there was a tie!
 */

public class GameClientGUI extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	
	// Instant Declaration
	public JButton RockButton;			
	public JButton PaperButton;			
	public JButton ScissorButton;
	public JButton QuitButton;
	public JLabel GameFeedbackBox;
	 
	public GameClientGUI() {
		
		// Set title, size, location, and layout for the GUI.
		setTitle("Rock Paper Scissors");
		setPreferredSize(new Dimension(450, 300));
		setName("Rock Paper Scissors");
		setBounds(new Rectangle(100, 100, 450, 300));
		setMinimumSize(new Dimension(450, 300));
		this.setVisible(true);
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		// Create the Quit button and add it to the GUI.
		QuitButton = new JButton("");
		QuitButton.setIcon(new ImageIcon(GameClientGUI.class.getResource("/RockPaperScissors/QuitButton.jpg")));
		QuitButton.setBounds(165, 341, 87, 29);
		getContentPane().add(QuitButton);
		
		// Create the Game Feedback Box and add it to the GUI.
		GameFeedbackBox = new JLabel();
		GameFeedbackBox.setEnabled(false);
		GameFeedbackBox.setBounds(421, 218, 149, 150);
		getContentPane().add(GameFeedbackBox);
		
		// Create the Scissors button and add it to the GUI.
		ScissorButton = new JButton("");
		ScissorButton.setIcon(new ImageIcon(GameClientGUI.class.getResource("/RockPaperScissors/ScissorsButton.jpg")));
		ScissorButton.setBounds(137, 218, 135, 102);
		getContentPane().add(ScissorButton);
		
		// Create the Paper button and add it to the GUI.
		PaperButton = new JButton("");
		PaperButton.setIcon(new ImageIcon(GameClientGUI.class.getResource("/RockPaperScissors/PaperButton.jpg")));
		PaperButton.setBounds(253, 95, 129, 101);
		getContentPane().add(PaperButton);
		
		// Create the Rock button and add it to the GUI.
		RockButton = new JButton("");
		RockButton.setIcon(new ImageIcon(GameClientGUI.class.getResource("/RockPaperScissors/RockButtonPic.jpg")));
		RockButton.setBounds(46, 95, 129, 101);
		getContentPane().add(RockButton);
		
		// Create the background image and add it to the GUI.
		JLabel RPSBackground = new JLabel("");
		RPSBackground.setBounds(0, 0, 594, 398);
		RPSBackground.setIcon(new ImageIcon(GameClientGUI.class.getResource("/RockPaperScissors/RockPaperScissorsGUI.jpg")));
		setBounds(0, 0, 610, 437);
		getContentPane().add(RPSBackground);
		this.setVisible(true);
	}
}
