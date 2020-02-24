package taxman;

import java.util.ArrayList;
import java.util.Scanner;

public class RunGame {
	
	public static int bestGame;
	public static ArrayList<Integer> bestMoves;
	public static int condition;
	public static int conditionData;
	public static long startTime;
	public static boolean over;

	public static void main(String[] args) {
		int maxValue;
		TaxGame t;
		bestGame = -99999;
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.println("What would you like to do?\n(1) Play against the Taxman\n(2) Let the computer play a game");
			int gameType = input.nextInt();
			
			switch(gameType) {
			case 1:
				System.out.println("What would you like the max number to be?");
				maxValue = input.nextInt();
				t = new TaxGame(maxValue);
				while(!t.gameOver()) {
					t.printTakeable();
					System.out.println("What would you like to take?");
					int num = input.nextInt();
					if(num > 0 && t.canTake(num)) {
						t.fullTake(num);
					}
					else if(num < 0) {
						t.fullUndo(-num);
					}
					else {
						System.out.println("That number cannot be taken.");
					}
					System.out.println("Your Score: " + t.getPlayerScore() + ", Taxman's Score: " + t.getTaxManScore());
				}
				t.endGame();
				System.out.println("Your Final Score: " + t.getPlayerScore() + ", Taxman's Final Score: " + t.getTaxManScore());
				System.out.println("Game Over!");
				break;
			case 2:
				over = false;
				System.out.println("What would you like the max number to be?");
				maxValue = input.nextInt();
				bestGame = -99999;
				bestMoves = null;
				t = new TaxGame(maxValue);
				System.out.println("Since the best possible game can take a while to determine, would you like to put any conditions on this game?");
				System.out.println("(1) Win\n(2) Win by (X)\n(3) Run for (X) time\n(4) No condition");
				condition = input.nextInt();
				if(condition == 2) {
					System.out.println("Win by how much?");
					conditionData = input.nextInt();
				}
				else if(condition == 3) {
					System.out.println("Run for how many seconds?");
					conditionData = input.nextInt();
					startTime = System.currentTimeMillis();
				}
				t.fullTake(t.getGreatestPrime());
				t.fullTake(t.getGreatestSquare());
				//t.endGame();
				solve(t);
				System.out.println(bestMoves);
				System.out.println("Won by: " + bestGame);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void solve(TaxGame t) {
		for(int i = 1; i <= t.getMaxValue(); i++) {
			if(t.canTake(i)) {
				t.fullTake(i);
				solve(t);
				if(over) {
					return;
				}
				t.fullUndo(i);
			}
		}
		t.endGame();
		
		int gameScore = t.getPlayerScore() - t.getTaxManScore();
		if(gameScore > bestGame) {
			bestGame = gameScore;
			bestMoves = (ArrayList<Integer>)t.getMoves().clone();
		}
		//System.out.println(gameScore + " " + bestGame + " " + condition);
		if(condition == 1) {
			if(bestGame > 0) {
				over = true;
				return;
			}
		}
		if(condition == 2) {
			if(bestGame >= conditionData) {
				over = true;
				return;
			}
		}
		if(condition == 3) {
			if(System.currentTimeMillis() - startTime > 1000 * conditionData) {
				over = true;
				return;
			}
		}
		t.unEndGame();
	}
}
