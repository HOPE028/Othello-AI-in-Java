package Proven;

public class AI {
	private int person = -1;
	
	/*
	 * Makes the move for the computer and returns the new board--> To make it's move 
	 * as good as possible, it utilizes an algorithm by the name of minimax. Minimax 
	 * essentially runs through every single possibility and gives it a rating off whether 
	 * or not the computer has won, how many pieces it has or if it controls important spots.
	 * This algorithm 
	 */
	public int[][] move(Board BOARD, int[][] board) {
		//Alerts user the algorithm is running
		System.out.println("\nAI is Loading...");
		
		//Sets up the algorithm
		int moveX = 0, moveY = 0;
		int score;
		
		/*
		 * We want to maximize and hence the bestScore will start very low and hence will 
		 * be guaranteed to change later.
		 */
		int bestScore = -100000;
		
		//Goes through every single position in the board.
		for (int a = 7; a >= 0; a--) {
			for (int b = 7; b >= 0; b--) {
				//Checks if that random position is a valid place to put it's next piece
				if (BOARD.isValid(board, a, b, person)) {
					
					//Creates a temporary board to not change the original board. 
					int[][] tempBoard = copyArray(board);
					
					//Does the move using the temporary board
					BOARD.move(tempBoard, a, b, person);
					
					//Gets a score for how good that move is from the 'minimax' function
					score = minimax(BOARD, tempBoard, false, 0, this.person*-1, -100000, 100000);
						
					/*
					 * If that move is the best move yet, then it will save the move and 
					 * update the best score.
					 */
					if (bestScore < score) {
						bestScore = score;
						moveX = a;
						moveY = b;
					}
				}
			}
		}
		//Does the best move after going through all the possibilities and prints it out.
		BOARD.move(board, moveX, moveY, person);
		System.out.println("\n");
		BOARD.print(board);
		
		return board;
	}

	/*
	 * Returns a score for how good a position is --> The minimax algorithm work off 
	 * a recursive function. Hence this function has it's base cases and it's recursive steps.
	 * It's base case is when there isn't a possible move because either the game is over or
	 * there isn't a possible move and they have to skip that turn. Or when the depth of that
	 * recursive tree has reached the limit. 
	 * The recursive step of this function is when it calls itself with another piece on the 
	 * board and a greater depth parameter.
	 * 
	 * This algorithm also implements alpha beta pruning.
	 */
	public int minimax(Board BOARD, int[][] board, boolean isMaximizing, int depth, int person, int alpha, int beta) {
		//Base case: Is there a move possible?
		if (BOARD.gameOver(board, person)) {
			//Are all the spots gone
			if (BOARD.spotsGone(board)) {
				//Game is over --> Depending on who won, this will either return 100, -100, or 0.
				return scores(board, BOARD.whoHasMorePieces(board));
			} else {
				/*
				 * No move possible --> If the person parameter is 1, then it returns 50. If not, then 
				 * it returns -50.
				 */
				if (person == 1) return 50;
				return -50;
			}
		}
		//Base Case: Has the depth reached the limit?
		if (depth > 7) return points(BOARD, board); //returns the what the 'points' function returned.
		
		//Setup
		int score, bestScore;
		
		/*
		 * Is it the turn of the person who is trying to maximize (-1) or the person who is trying to 
		 * minimize (1).
		 */
		if (isMaximizing) {
			/*
			 * We want to maximize and hence the bestScore will start very low and hence will 
			 * be guaranteed to change later.
			 */
			bestScore = -100000;
			
			//Going through board position
			for (int a = 0; a < 8; a++) {
				for (int b = 0; b < 8; b++) {
					//Checks if that random position is a valid place to put it's next piece
					if (BOARD.isValid(board, a, b, person)) {
						//Creates a temporary board to not change the original board. 
						int[][] tempBoard = copyArray(board);
						
						//Does the move using the temporary board
						BOARD.move(tempBoard, a, b, person);
						
						//Gets a score for how good that move is from the 'minimax' function
						score = minimax(BOARD, tempBoard, !isMaximizing, depth+1, person*-1, alpha, beta);
						
						/*
						 * Calls minimax again but with the new board, different 'isMaximizing' value,
						 * greater depth, opposite person, and different alpha and beta values.
						 */
//						score = minimax(BOARD, tempBoard, !isMaximizing, depth+1, person*-1, alpha, beta);
						
						//If the score is better than the best score yet, then the best score will be updated.
						if (bestScore < score) {
							bestScore = score;
						}
						
						//Alpha beta pruning for increased efficiency.
						if (alpha < score) {
							alpha = score;
						}
						if (beta <= alpha) {
							return 700;
						}
					}
				}
			}
		} else {
			bestScore = 100000;
			for (int a = 0; a < 8; a++) {
				for (int b = 0; b < 8; b++) {
					//Checks if that random position is a valid place to put it's next piece
					if (BOARD.isValid(board, a, b, person)) {
						//Creates a temporary board to not change the original board. 
						int[][] tempBoard = copyArray(board);
						
						//Does the move using the temporary board
						BOARD.move(tempBoard, a, b, person);
						
						//Gets a score for how good that move is from the 'minimax' function
						score = minimax(BOARD, tempBoard, !isMaximizing, depth+1, person*-1, alpha, beta);
						/*
						 * Calls minimax again but with the new board, different 'isMaximizing' value,
						 * greater depth, opposite person, and different alpha and beta values.
						 */
//						score = minimax(BOARD, tempBoard, !isMaximizing, depth+1, person*-1, alpha, beta);
						
						//If the score is better than the best score yet, then the best score will be updated.
						if (bestScore > score) {
							bestScore = score;
						}
						//Alpha beta pruning for increased efficiency.
						if (beta > score) {
							beta = score;
						}
						if (beta <= alpha) {
							return -700;
						}
					}
				}
			}
		}
		//Returns either the lowest or highest score depending on the 'isMaxamizing' variable
		return bestScore;
	}
	
	/* 
	 * Creates a copy of the board --> Recieves the 2d array, makes another
	 * 2d array, and puts all the values of the original in the second one.
	 */
	public int[][] copyArray(int[][] board) {
		int[][] newBoard = new int[8][8];
		for (int a = 0; a < 8; a++) {
			for (int b = 0;  b < 8; b++) {
				newBoard[a][b] = board[a][b];
			}
		}
		return newBoard;
	}
	
	/*
	 * Returns how many points that board position is worth --> Receives a score from 'whoHasMorePieces'
	 * function in the Board class. It will then add or subtract points off the score variable on
	 * whether it has important places on the board or not.
	 */
	public int points(Board BOARD, int[][] board) {
		//score = black pieces - white pieces
		int score = BOARD.whoHasMorePieces(board);
		
		//Plus or Minus three depending on whether it has the far corner pieces
		if (board[0][0] != 0) {
			if (board[0][0] == 1) score -= 3;
			else score += 3;
		}
		if (board[7][0] != 0) {
			if (board[0][0] == 1) score -= 3;
			else score += 3;
		}
		if (board[0][7] != 0) {
			if (board[0][0] == 1) score -= 3;
			else score += 3;
		}
		if (board[7][7] != 0) {
			if (board[0][0] == 1) score -= 3;
			else score += 3;
		}
		
		//Plus or Minus 1 depending on whether it has the center pieces
		if (board[3][3] == 1) score--;
		else score++;

		if (board[4][3] == 1) score--;
		else score++;

		if (board[3][4] == 1) score--;
		else score++;

		if (board[4][4] == 1) score--;
		else score++;
		
		//Plus or minus 1 depending on whether it has the edge pieces.
		for (int a = 1; a < 7; a++) {
			if (board[a][0] != 0) {
				if (board[a][0] == 1) score--;
				else score++;
			}
		}
		for (int a = 1; a < 7; a++) {
			if (board[0][a] != 0) {
				if (board[0][a] == 1) score--;
				else score++;
			}
		}
		for (int a = 1; a < 7; a++) {
			if (board[a][7] != 0) {
				if (board[a][7] == 1) score--;
				else score++;
			}
		}
		for (int a = 1; a < 7; a++) {
			if (board[7][a] != 0) {
				if (board[7][a] == 1) score--;
				else score++;
			}
		}
		
		//Returns final score
		return score;
	}
	
	
	// Returns the assigned points to a draw, loss, or tie
	public int scores(int[][] board, int winner) {
		//winner is the result of the # of black pieces minus the # of white pieces.
		if (winner == 0) return 0;
		else if (winner > 0) return -100;
		else return 100;
	}
	
	//returns -1
	public int getPerson() {
		return person;
	}
}








