import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.jfree.ui.RefineryUtilities;

public class Game {
	
	static char PLAYER_1 = 'X';
	static char PLAYER_2 = 'O';
	static char CURRENT_PLAYER = PLAYER_2;
	static char MAX_PLAYER = PLAYER_1;
	static char WINNER = ' ';
	
	
	static char [][] boardArray = new char [3][3];
	static int [] moves = new int [3*3];
	
	static int NUMBER_OF_SIMULATIONS = 100;
	
	static int [] gamesWon = new int [NUMBER_OF_SIMULATIONS/10];
	static int [] gamesPlayed = new int [NUMBER_OF_SIMULATIONS/10];
	
	static Scanner input = new Scanner(System.in);
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Random random = new Random();
		int countOfWin = 0;
		
		for (int i=0; i<NUMBER_OF_SIMULATIONS; i++)
		{
			//init starting player
			int rand = random.nextInt(2);
			
			if (rand == 0)
				CURRENT_PLAYER = PLAYER_1;
			else
				CURRENT_PLAYER = PLAYER_2;
			
			//init favoured player
			rand = random.nextInt(2);
			
			if (rand == 0)
				MAX_PLAYER = PLAYER_2;
			else
				MAX_PLAYER = PLAYER_1;
			
			System.out.printf("\nStart of Game %d \n", (i+1));
			System.out.println("============================");
			System.out.println("Starting with player: " + CURRENT_PLAYER + " and FAVOURED PLAYER:  " + MAX_PLAYER);
			
			
			initBoard();
			initMoves();
			
			while(!isGameOver() && !isWinner()) 
			{
				displayBoard();
				displayAvailableMoves();
				playGame(CURRENT_PLAYER);
			}
			
			//get true winner
			isWinner();
			
			System.out.println("\n" + (WINNER != ' ' ? WINNER + " won" : "Ended in a draw. ") + "\nState at end:");

			displayBoard();
			displayAvailableMoves();
			
			//win count
			countOfWin += (WINNER == MAX_PLAYER ? 1 : 0);
			
			//record data in breaks of 10
			if ((i+1) % 10 == 0)
			{
				gamesWon [i/10] = countOfWin;
				gamesPlayed [i/10] = i+1;
			}
			
		}
		
		//plot graph of wins
		plotGraphData();
		
	}

	static void initMoves()
	{
		for (int i=0; i< moves.length; i++)
		{
			moves[i] = i+1;
		}
	
	}

	static void initBoard()
	{
		for (int i=0; i< boardArray.length; i++)
		{
			for (int j=0; j< boardArray[i].length; j++)
			{
				boardArray [i][j] = '-';
			}
			
		}
	}
	
	static void displayBoard()
	{
		System.out.println("Current board state");
		
		for (int i=0; i< boardArray.length; i++)
		{
			for (int j=0; j< boardArray[i].length; j++)
			{
				System.out.print( boardArray [i][j] + " ");	
			}
			
			System.out.println (); 
		}
	}
	
	static void displayAvailableMoves()
	{
		System.out.println("Currently available moves");
		for (int i=0; i< moves.length; i++)
		{
			if (i % 3 == 0)
				System.out.println();
			
			System.out.print (moves[i] + " "); 
		}
	}
	
	static void playGame(char player)
	{
		System.out.printf("\n Player %c \'s turn. Choose a valid move\n", player);
		
		//manual play, with no AI
//		int move = input.nextInt();
//		boolean isValid = checkIfMoveIsValid(move);
		
		
		int move = 0;
				
		if (player == MAX_PLAYER)
		{
			move = getBestMove(player);
			System.out.println("Best move chosen - " + move);
		}
		else
		{
			move = getRandMove();
			System.out.println("Random move chosen - " + move);
		}
		
		//set move on board
		if (isMoveValid(move))
		{
			boardArray [(move-1)/3][(move-1)%3] = player;
			moves[move-1] = 0;

			togglePlayers();
		}
		else
		{
			System.out.println("Invalid move");
			displayBoard();
			displayAvailableMoves();
			playGame(player);
		}
		
		
	}
	
   static boolean isMoveValid(int move)
   {
	   if (move < 1 || move > 9 || moves[move-1] == 0)
		   return false;
	   
	   return true;
		
   }
	
   static void togglePlayers()
   {
		if (CURRENT_PLAYER == PLAYER_1)
			CURRENT_PLAYER = PLAYER_2;
		else
			CURRENT_PLAYER = PLAYER_1;
		
   }
   
   static boolean isRowSame()
   {
		for (int i=0; i< boardArray.length; i++)
		{
			if (boardArray [i][0] != '-' && boardArray [i][0] == boardArray [i][1] && boardArray [i][1] == boardArray [i][2])
			{
				WINNER = boardArray [i][0];
				return true;
			}
			
		}
		return false;
   }
	
   static boolean isColumnSame()
   {
		for (int i=0; i< boardArray[0].length; i++)
		{
			if (boardArray [0][i] != '-' && boardArray [0][i] == boardArray [1][i] && boardArray [1][i] == boardArray [2][i])
			{
				WINNER = boardArray [0][i];
				return true;
			}
			
		}
		return false;
   }
   
   static boolean isDiagonalSame()
   {
	   if (boardArray [0][0] != '-' && boardArray [0][0] == boardArray [1][1] && boardArray [1][1] == boardArray [2][2])
		{
			WINNER = boardArray [0][0];
			return true;
		}
	   else if (boardArray [0][2] != '-' && boardArray [0][2] == boardArray [1][1] && boardArray [1][1] == boardArray [2][0])
		{
			WINNER = boardArray [0][2];
			return true;
		}
		return false;
  }
   
   
   static int getRandMove()
   {
	   Random rand = new Random();
	   return rand.nextInt(10);
   }
   
	static boolean isWinner()
	{
		return (isColumnSame() || isDiagonalSame() || isRowSame());
	}
	
	static boolean isGameOver()
	{
		for (int i=0; i<moves.length; i++)
		{
			if (moves[i] != 0)
				return false;
		}
		
		return true;
	}
	
	static int minimax(char player)
	{
		int boardScore = evaluateBoardState();
		
		if (boardScore == 10 || boardScore == -10)
		{
			return boardScore;
		}
		else
		{
			if (!isGameOver())
			{ 
				int best = -10;
				if (player == MAX_PLAYER)
				{
					best = -10;
					
					for (int i=0; i<boardArray.length; i++)
					{
						for (int j=0; j<boardArray[i].length; j++)
						{
							if (boardArray[i][j] == '-')
							{
								boardArray[i][j] = CURRENT_PLAYER;
								
								best = Math.max(best, minimax((player == PLAYER_1 ? PLAYER_2 : PLAYER_1)));
							
								boardArray[i][j] = '-';
								
							}

							
								
						}
					}
				}
				else
				{
					best = 10;
					
					for (int i=0; i<boardArray.length; i++)
					{
						for (int j=0; j<boardArray[i].length; j++)
						{
							if (boardArray[i][j] == '-')
							{
								boardArray[i][j] = CURRENT_PLAYER;
								
								best = Math.min(best, minimax((player == PLAYER_1 ? PLAYER_2 : PLAYER_1)));
								
								boardArray[i][j] = '-';
								
							}

							
								
						}
					}
				}
				return best;
			}
			else
				 return 0;
			
		 }
		
		
		
	}
	
	static int evaluateBoardState()
	{
		if (isWinner())
		{
			return (WINNER == MAX_PLAYER ? 10: -10);
		}
		
		return 0;
			
	}
	
	static int getBestMove(char player)
	{
		int best = -10;
		int bestMove = -1;
		
		for (int i=0; i<boardArray.length; i++)
		{
			for (int j=0; j<boardArray[i].length; j++)
			{
				if (boardArray[i][j] == '-')
				{
					boardArray[i][j] = player;
					
					int nextBest = minimax(player);
					
					if  (nextBest >= best)
					{
						best = nextBest;
						bestMove = (3*i+ (j+1)); 
					}
					
					boardArray[i][j] = '-';
				}
					
			}
		}
		WINNER = ' ';
		
		return bestMove;
		
	}
	
	
	static void plotGraphData()
	{
		System.out.println("Won : " + Arrays.toString(gamesWon));
		System.out.println("Played : " + Arrays.toString(gamesPlayed));
		
		GameDataPlot gameDataPlot = new GameDataPlot(
				 "Tic-Tac-Toe AI plot",
		         "Games Won Vs Games Played",
		         gamesWon,
		         gamesPlayed);

		gameDataPlot.pack( );
		RefineryUtilities.centerFrameOnScreen( gameDataPlot );
		gameDataPlot.setVisible( true );
	}

}
