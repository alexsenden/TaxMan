package taxman;

import java.util.ArrayList;

public class TaxGame {
	private int playerScore;
	private int taxManScore;
	private int maxValue;
	
	private ArrayList<Number> numbers;
	private ArrayList<Integer> usedFactors;
	private ArrayList<Integer> moves;
	
	public TaxGame(int maxValue) {
		this.moves = new ArrayList<Integer>();
		this.maxValue = maxValue;
		this.numbers = fillArray(maxValue);
		this.playerScore = this.taxManScore = 0;
		this.usedFactors = new ArrayList<Integer>();
	}
	
	private ArrayList<Number> fillArray(int maxValue) {
		ArrayList<Number> array = new ArrayList<Number>();
		for(int i = 0; i < maxValue; i++) {
			array.add(new Number(i + 1));
		}
		return array;
	}
	
	public void fullTake(int num) {
		take(num);
		tax(num);
		checkFactors(num);
		moves.add(Integer.valueOf(num));
	}
	
	public void fullUndo(int num) {
		untake(num);
		untax(num);
		uncheckFactors(num);
		moves.remove(Integer.valueOf(num));
	}
	
	public void untake(int num) {
		int index = num - 1;
		this.numbers.get(index).untake();
		playerScore -= num;
	}
	
	public void untax(int num) {
		ArrayList<Integer> facs = Number.getFactors(num);
		for(Integer i : facs) {
			numbers.get(i.intValue() - 1).untake();
			if(!numbers.get(i.intValue() - 1).isTaken()) {
				taxManScore -= i.intValue();
			}
		}
	}
	
	public void uncheckFactors(int num) {
		ArrayList<Integer> toAddBack = Number.getFactors(num);
		toAddBack.add(Integer.valueOf(num));
		for(Integer a : toAddBack) {
			usedFactors.remove(a);
			/*
			for(int i = 0; i < usedFactors.size(); i++) {
				if(a.equals(usedFactors.get(i))) {
					usedFactors.remove(i);
					break;
				}
			}
			*/
		}
		
		for(Number n : numbers) {
			n.readdFactors(this.usedFactors);
		}
	}
	
	public void take(int num) {
		int index = num - 1;
		this.numbers.get(index).take();
		playerScore += num;
	}
	
	public void tax(int num) {
		ArrayList<Integer> facs = Number.getFactors(num);
		for(Integer i : facs) {
			numbers.get(i.intValue() - 1).take();
			if(numbers.get(i.intValue() - 1).getTimesTaken() == 1) {
				taxManScore += i.intValue();
			}
		}
	}
	
	public void checkFactors(int num) {
		ArrayList<Integer> toRemove = Number.getFactors(num);
		toRemove.add(Integer.valueOf(num));
		for(Number n : numbers) {
			n.removeFactors(toRemove);
		}
		for(Integer i : toRemove) {
			this.usedFactors.add(i);
		}
	}
	
	public void printTakeable() { 
		//String s = "";
		for(Number n : this.numbers) {
			if(!n.isTaken() && n.hasFactors()) {
				System.out.print(n.getValue() + " ");
			} else {
				//s += n.getValue() + " " + n.isTaken() + " " + n.hasFactors() + " ";
			}
		}
		//System.out.println("\n" + usedFactors);
		//System.out.println("\n" + s);
		System.out.println();
	}
	
	public boolean canTake(int num) {
		if(!this.numbers.get(num - 1).isTaken() && this.numbers.get(num - 1).hasFactors()) {
			return true;
		}
		return false;
	}
	
	public int getPlayerScore() {
		return this.playerScore;
	}
	
	public int getTaxManScore() {
		return this.taxManScore;
	}
	
	public int getMaxValue() {
		return this.maxValue;
	}
	
	public ArrayList<Integer> getMoves() {
		return this.moves;
	}
	
	public boolean gameOver() {
		for(int i = 0; i < this.maxValue; i++) {
			if(canTake(i + 1)) {
				return false;
			}
		}
		return true;
	}
	
	public void endGame() {
		for(Number n : numbers) {
			if(!n.isTaken()) {
				this.taxManScore += n.getValue();
			}
		}
	}
	
	public void unEndGame() {
		for(Number n : numbers) {
			if(!n.isTaken()) {
				this.taxManScore -= n.getValue();
			}
		}
	}
	
	public int getGreatestPrime() {
		int greatest = 2;
		boolean prime;
		for(int i = 3; i <= this.maxValue; i++) {
			prime = true;
			for(int j = 2; j < i; j++) {
				if(i % j == 0) {
					prime = false;
					break;
				}
			}
			if(prime && i > greatest) {
				greatest = i;
			}
		}
		return greatest;
	}
	
	public int getGreatestSquare() {
		int count = 1;
		while(Math.pow(count + 1, 2) <= this.maxValue) {
			count++;
		}
		return (int)Math.pow(count, 2);
	}
}
