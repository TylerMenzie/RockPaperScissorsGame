package RockPaperScissors;

import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*//Creators: Seth DeWalt, Tyler Menzie
 * // Tyler Menzie: B01269607
// Seth DeWalt: B01223206
 * A Class that sets up the sockets for the clients to allow for connection to the server
 * and handles communication to and from the server.
 * 
 * Communications between client and server are sent through BUfferedReader and PrintWriter Objects.
 * 
 * Client responses will be sent as a string over the server to client system that allows for the 
 * message to be read into on the GameServer and the system uses these strings to decide what to do.
 * 
 * The System after deciding which player has one the game, will prompt to the GUI who one or lost and then 
 * prompt to the user using a JOPtion Dialog box if they would like to play again.
 * 
 */

public class GameClient {

	// Instance variables
	private GameClientGUI ClientGUI;  // the graphical user interface for the client
	private Socket socket;  // the socket used for communication with the server
	private int PORT = 8083;  // the port number used for the connection
	private int player;  // the player number assigned by the server
	private GameServer connect;  // the connection to the server
	private String Choice;  // the choice made by the player
	private BufferedReader input;  // the input stream for reading data from the server
	private PrintWriter output;  // the output stream for writing data to the server
	private ServerGUI DisplayServerGUI;  // the graphical user interface for the server

	// Constructor method that sets up the client-side socket connection, GUI and action listeners
	public GameClient(String serverAddress) throws Exception {

		// Set up the socket connection
		socket = new Socket(serverAddress, PORT);

		// Set up the input and output streams
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);

		// Set up the graphical user interface for the client
		ClientGUI = new GameClientGUI();
		ClientGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display a message on the GUI depending on whether the connection to the server was successful or not
		if (socket.isConnected())
		{
			ClientGUI.GameFeedbackBox.setText("Player's Connected");
			System.out.println("Connected");
		}
		else
		{
			ClientGUI.GameFeedbackBox.setText("Player's Disconnected");
			System.out.println("NOT Connected");
		}

		// Set up action listeners for the rock, paper, scissors, and quit buttons on the GUI
		ClientGUI.RockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				// Send the player's choice to the server and update the GUI
				output.println("PLAYER_CHOICE_ROCK");
				ClientGUI.GameFeedbackBox.setText("You chose rock");
			}
		});

		ClientGUI.PaperButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				// Send the player's choice to the server and update the GUI
				output.println("PLAYER_CHOICE_PAPER");
				ClientGUI.GameFeedbackBox.setText("You chose paper");
			}
		});

		ClientGUI.ScissorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				// Send the player's choice to the server and update the GUI
				output.println("PLAYER_CHOICE_SCISSORS");
				ClientGUI.GameFeedbackBox.setText("You chose scissors");
			}
		});

		ClientGUI.QuitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				// Send a message to the server indicating that the player wants to quit the game, and exit the program
				output.println("QUIT");
				System.exit(0);
			}
		});
	}
	
	
	public void play() throws Exception {
		String serverResponse;
		try {
			
			serverResponse = input.readLine();

			// Sets up new player connection and updates GUI
			if (serverResponse.startsWith("NEW_"))
			{	// Displays the right text into the right GUI
				this.player = Integer.parseInt(serverResponse.substring(4));
				ClientGUI.setTitle("Rock Paper Scissors - Player #" + this.player);
				ClientGUI.GameFeedbackBox.setText("Connected to Server\nWaiting for Player 2 to Connect");
				ClientGUI.GameFeedbackBox.setForeground(Color.GREEN);
			}
			
			// Awaits server response
			while (true) {
				serverResponse = input.readLine();
				if (serverResponse != null) 
				{
					// Tells players start
					if (serverResponse.startsWith("START")) 
					{ 
						ClientGUI.GameFeedbackBox.setText("Connection Established - Please Choice Rock, Paper, or Scissors");
						ClientGUI.RockButton.setEnabled(true);
						ClientGUI.PaperButton.setEnabled(true);
						ClientGUI.ScissorButton.setEnabled(true);
					} 
					

					// Outputs to user if a player won
					else if (serverResponse.startsWith("WIN"))
					{
						ClientGUI.GameFeedbackBox.setText("You have won!");
						ClientGUI.GameFeedbackBox.setForeground(Color.GREEN);
						break;
					}

					// Outputs to label if player was defeated
					else if (serverResponse.startsWith("DEFEAT")) 
					{
						ClientGUI.GameFeedbackBox.setText("You have been defeated!");
						ClientGUI.GameFeedbackBox.setForeground(Color.RED);
						break;					
					}

					// Outputs to label if both players tied
					else if (serverResponse.startsWith("TIE"))
					{
						ClientGUI.GameFeedbackBox.setText("You have both tied!");
						break;
					}
				}
			}
		} 
		finally {
			
			socket.close();		
		}
	}

	// Displays Popup box that asks if the Users would like to play again
	private boolean PlayAgain() throws Exception 
	{
		int response = JOptionPane.showConfirmDialog(ClientGUI,"Do you want to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
		ClientGUI.dispose();
		return response == JOptionPane.YES_OPTION;
	}


	public static void main(String[] args) throws Exception {
		while (true)
		{	
			String serverAddress = (args.length == 0) ? "localhost" : args[0];	
			GameClient client = new GameClient(serverAddress);	
			client.play();	
			//System.out.println("Current Thread:" + Thread.currentThread().isAlive());	

			if (!client.PlayAgain()) 
			{		
				client.output.println("QUIT");
				break;
			}
		}
	}
}

