package RockPaperScissors;

//Creators: Seth DeWalt, Tyler Menzie
//This is the Graphical interface for the server. 
//It handles all the buttons and connections to the clients.
// Tyler Menzie: B01269607
// Seth DeWalt: B01223206
//packages to import
import java.awt.*;
import javax.swing.*;

//JFrame class
public class ServerGUI extends JFrame 
{
	//JComponents added to the frame
	JPanel buttons;
	JPanel textBox;	
	public static JTextArea log;
	JLabel serverLabel;
    JButton listen;
    JButton stopListening;
    JButton exit;
    
   
    // Building The GUI for starting the Server
	public ServerGUI() 
	{
		//Creating panels
		buttons  = new JPanel(new GridLayout());
		textBox = new JPanel();
		
		//Creating JLabel and buttons
		listen = new JButton("Listen");
		stopListening = new JButton("Stop");
		exit = new JButton("Exit");
		
		//Creating the log area Panel
		JPanel serverLogPanel = new JPanel(new BorderLayout());
	    serverLabel = new JLabel("Server", JLabel.CENTER);
	    //Creating the buffer
	    JPanel serverLabelBuffer = new JPanel();
	    serverLabelBuffer.add(serverLabel);
	    serverLogPanel.add(serverLabelBuffer, BorderLayout.NORTH);
	    //Setting rules for the log text area
	    log = new JTextArea(10, 35);
	    log.setEditable(false);
	    //Creating the scroll pane for the window
	    JScrollPane serverLogPane = new JScrollPane(log);
	    JPanel serverLogPaneBuffer = new JPanel();

	    //adding them to the panel
	    serverLogPaneBuffer.add(serverLogPane);
	    serverLogPanel.add(serverLogPaneBuffer, BorderLayout.SOUTH);
		
		//Adding textArea to the center panel
	    textBox.add(serverLogPanel);
		
		// Adding buttons to buttons panel
		//buttons.add(listen);
		buttons.add(stopListening);
		buttons.add(exit);
		
		//adding text and panels to the server window
		getContentPane().add(textBox, BorderLayout.NORTH);	
		getContentPane().add(buttons, BorderLayout.SOUTH);		
		
		
		this.setTitle("Server for Rock Paper Scissors Game"); // Sets Title for the Frame
		this.setSize(400, 400);								  // Sets the Size of the Frame
		this.setResizable(false);				  			  // Makes the Frame not able to be dragged and resized
		this.setVisible(true);								  // Makes the Frame Visible to the User
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Makes the Frame close once the program exits
		getContentPane().setLayout(new BorderLayout());					  // Sets the Layout of the panel

	}
}