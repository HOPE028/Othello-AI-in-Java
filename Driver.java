package Proven;

public class Driver {

	/*
	 * Runs the entire game --> Sets up all variables and objects. It then runs a while loop where the game
	 * runs in until the game has ended.
	 */
	public static void main(String[] args) {
		//Game Setup
		Board BOARD = new Board();
		Players playerOne = new Players(1);
		AI playerTwo = new AI();
		int[][] board = BOARD.boardSetup();
		boolean nextMove = true;
		int lengthOfNoMoves = 0;
		BOARD.print(board);
		
		//Game starts
		
		//While the loop has not broken because of the game ending, the game will carry on.
		while (true) {
			//Player one moves if the 'nextMove' variable is equal to true. Also 'lengthOfNoMoves' is reset
			if (nextMove) {
				playerOne.move(BOARD, board);
				lengthOfNoMoves = 0;
			}
			
			//'nextMove' is reset
			nextMove = true;
			
			//Checks if the second player has a possible move
			if (BOARD.gameOver(board, playerTwo.getPerson())) {
				//Checks if all the spots are gone
				if (BOARD.spotsGone(board)) {
					//Game over
					winner(BOARD, board);
					return;
				} else {
					//Next move will be skipped because there is no possible move. 'lengthOfNoMoves' is increased.
					System.out.println("\n**Next Move Skipped Because No Move Is Possible**\n");
					nextMove = false;
					lengthOfNoMoves++;
				}
			}
			
			//Player Two moves if the 'nextMove' variable is equal to true. Also 'lengthOfNoMoves' is reset
			if (nextMove) {
				playerTwo.move(BOARD, board);
				lengthOfNoMoves = 0;
			}
			
			nextMove = true;
			
			//Checks if the first player has a possible move
			if (BOARD.gameOver(board, playerOne.getPerson())) {
				//Checks if all the spots are gone
				if (BOARD.spotsGone(board)) {
					//Game over
					winner(BOARD, board);
					return;
				} else {
					//Next move will be skipped because there is no possible move. 'lengthOfNoMoves' is increased.
					System.out.println("\n**Next Move Skipped Because No Move Is Possible**\n");
					nextMove = false;
					lengthOfNoMoves++;
				}
			}
			
			//If lengthOfNoMoves is equal to two, then that means both player one and player two couldn't go.
			if (lengthOfNoMoves >= 2) {
				//Game over
				noMoreMoves(BOARD, board); 
				return;
			}
		}
	}
	
	//Tells the user the game is over because there aren't any possible moves left even though their are open spots left on the baord.
	public static void noMoreMoves(Board BOARD, int[][] board) {
		System.out.println("Both players don't have a valid move! The Game has hence ENDED:");
		winner(BOARD, board);
	}
	
	/* Prints out a message telling the user who won --> Figures out who has more pieces from 'whoHasMorePieces
	 * which returns # of black pieces minus # of white pieces. Hence if the difference is zero, then it is a tie,
	 * if the number is positive than black won and if the difference is negative than white won. 
	 */
	public static void winner(Board BOARD, int[][] board) {
		int winner = BOARD.whoHasMorePieces(board);
		
		if (winner == 0) System.out.println("It is a TIE!");
		else if (winner < 0) System.out.println("WHITE WON!");
		else System.out.println("BLACK WON");
	}
}

