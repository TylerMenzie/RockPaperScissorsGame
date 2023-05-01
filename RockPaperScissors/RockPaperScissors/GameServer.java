package RockPaperScissors;

//Creators: Seth DeWalt, Tyler Menzie
// Tyler Menzie: B01269607
// Seth DeWalt: B01223206
//This is the backend for the Game server
//THis controls all the action listens for the buttons
//and handles the multithreading and the socket connections.
//This file also handles the rules of the game when the players connect

//packages to import
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Class that the setup and game will played in
public class GameServer {
	
	//The private variables for the server
	private ServerSocket socket;		
	private boolean active;				
	private ServerGUI DisplayServerGUI;
	private ServerGUI log;

	//Handling the game server
	public GameServer() throws Exception {
		// Declaring new ServerGUI Object
        DisplayServerGUI = new ServerGUI();
        DisplayServerGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Setting New Socket to port 8083
        socket = new ServerSocket(8083);


        // Determines if the server is running
        if (!socket.isClosed())
            active = true;

        // Display current status of server
        DisplayServerGUI.log.append("Server started - Ready for Clients to connect!!\n");
        DisplayServerGUI.serverLabel.setForeground(Color.GREEN);

        //Handling if the stop listening button is pressed
        DisplayServerGUI.stopListening.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                try {
                	//Closes the socket
                    socket.close();
                    //writing to the server log
                    DisplayServerGUI.log.append("Server Closed\n");
                } catch (IOException e1) {
                    //print to the server log
                    DisplayServerGUI.log.append("Server failed to closed\n");
                    //error statement
                    e1.printStackTrace();
                }
            }
        });
        //Handling if the exit button is pressed
        DisplayServerGUI.exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
            	//Exiting the system
                System.exit(0);
            }
        });
	}


static // Class that takes in the choices of each player and determined the winner
class Game {

	// The current player
	Player currentplayer;

	// String setting for the Choice of Player 1 & 2
	public static String player1choice;
	public static String player2choice;

	// Determine if the game has a winner
	public int getWinner() {
		//if player one chooses rock and player two chooses scissors
		if ((player1choice.equals("PLAYER_CHOICE_ROCK")  && player2choice.equals("PLAYER_CHOICE_SCISSORS")))
			{
				//return player one wins 
				return 1;
			}
		//if player one chooses paper and player two chooses rock
		else if ((player1choice.equals("PLAYER_CHOICE_PAPER") && player2choice.equals("PLAYER_CHOICE_ROCK")))
			{
				//return player one wins 
			 	return 1;
			}
		//if player one chooses scissors and player two chooses paper
		else if ((player1choice.equals("PLAYER_CHOICE_SCISSORS") && player2choice.equals("PLAYER_CHOICE_PAPER")))
			{
				//return player one wins 
				return 1;
			}
		//if player one chooses rock and player two chooses scissors
		else if ((player2choice.equals("PLAYER_CHOICE_ROCK") && player1choice.equals("PLAYER_CHOICE_SCISSORS")))
			{
				//return player two wins 
				return 2;
			}
		//if player one chooses paper and player two chooses rock
		else if((player2choice.equals("PLAYER_CHOICE_PAPER") && player1choice.equals("PLAYER_CHOICE_ROCK")))
			{
				//return player two wins 
				return 2;
			}
		//if player one chooses scissors and player two chooses paper
		else if((player2choice.equals("PLAYER_CHOICE_SCISSORS") && player1choice.equals("PLAYER_CHOICE_PAPER"))) 
			{
				//return player two wins 
				return 2;
			}
		else 
			{
				//else tie
				return 0;
			}
	}
	//The main driver function that takes in string agruments
	public static void main(String[] args) throws Exception {

		// Displays the GUI and Shows if the server has been connected
		GameServer gameServer = new GameServer();

		// Create a new thread to handle incoming connections
		Thread connectionThread = new Thread(() -> {
			try {
				//Always running
				while (true) {
					if (gameServer.active) {
						// Setting Game Object
						Game game = new Game();

						// Sets both players choice to NULL so no duplicate responses
						game.player1choice = "ZERO";
						game.player2choice = "ZERO";

						// Ensure both players are connected
						Game.Player player1 = game.new Player(gameServer.socket.accept(), 1);
						ServerGUI.log.append("Player #1 has connected to server\n");
						Game.Player player2 = game.new Player(gameServer.socket.accept(), 2);
						ServerGUI.log.append("Player #2 has connected to server\n");

						// Starts the game for each player
						player1.start();
						
						player2.start();
						
						
					}
				}
			//Catch if there is an error
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// Start the thread
		connectionThread.start();

		try {
			// Wait for the thread to finish
			connectionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Close the socket from any new connections
		gameServer.socket.close();
}

	
	// Method that takes in the Choice of Player #1
	public boolean playerOneChoice () {
		//deciding player choices
		return player1choice.startsWith("PLAYER_CHOICE_");
	}
	
	// Method that takes in the Choice of Player #2
	public boolean playerTwoChoice() 
	{
		//deciding player choice
		return player2choice.startsWith("PLAYER_CHOICE_");
	}

	// Begin creating threads for players
	class Player extends Thread {

		// Set up player/opponent values and input/output
		int player;
		Socket socket;
		BufferedReader input;
		PrintWriter output;

		// Method to Start Socket for Player Connection
		public Player(Socket socket, int number) {

			// Set socket and player number values
			this.socket = socket;
			this.player = number;

			// Setting up the input/output stream
			try
			{
				//setting input and output
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
				output.println("NEW_" + number);
			} 

			// Error checking
			catch (Exception e) 
			{
				//printing error statement
				e.printStackTrace();
			}

		}

		// Determine if players have selected their choice
		public boolean setChoice(Player player, String choice) {

			// Set player 1 choice
			if (player.player== 1)
				player1choice = choice;

			// Set player 2 choice
			else
				player2choice = choice;

			return true;
		}
		// Process for the thread when it is running
		public void run() {
			try {

				// Tells the player to start making a choice
				output.println("START");

				// Gets the choice from the player
				String choice = input.readLine();

				// Gets commands from clients and processes
				while (true) {	
					if (choice != null) {

						// If the choice is not "QUIT"
						if (choice.startsWith("PLAYER_CHOICE_"))
						{

							// Sets the player's choice
							setChoice(this, choice);

							//Deciding winner
							if (playerOneChoice() && playerTwoChoice())
							{
								//deciding who to print winner to
								if (getWinner() > 0) {
									if (getWinner() == this.player) 
									{
										output.println("WIN");
										ServerGUI.log.append("Player #" + this.player + " Won!\n");
										break;
									}
									//printing loser if conditions correct
									else if (getWinner() != this.player)
									{
										output.println("DEFEAT");
										ServerGUI.log.append("Player #" + this.player + " was Defeated!\n");
										break;
									}
								}
								//printing the tie 
								else 
								{
									output.println("TIE");
									ServerGUI.log.append("Player #" + this.player + " tied!!\n");
									break;
								}
							}
						}

						// Close socket if game ends
						else if (choice.startsWith("QUIT")) 
						{
							//close socket
							this.socket.close();
							 ServerGUI.log.append("Player #" + this.player + " had disconnected from the server\n");
							return;
						} 
					} 
				}
			//error printing
			} catch (Exception e) 
			{
				e.printStackTrace();
			} finally {
				//closing socket
				try {socket.close();} catch (IOException e) {}
			}
		} 
	}
	}
}