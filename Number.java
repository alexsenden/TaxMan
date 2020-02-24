package taxman;

import java.util.ArrayList;

public class Number {
	private int value;
	private boolean taken;
	private int timesTaken;
	private ArrayList<Integer> factors;
	private boolean hasFactors;
	
	public Number(int value) {
		this.value = value;
		this.taken = false;
		this.timesTaken = 0;
		this.factors = getFactors(value);
		this.hasFactors = true;
	}
	
	public static ArrayList<Integer> getFactors(int value) {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(Integer.valueOf(1));
		for(int i = 2; i <= Math.sqrt(value); i++) {
			if(value % i == 0) {
				array.add(Integer.valueOf(i));
				if(value / i != i) {
					array.add(Integer.valueOf(value / i));
				}
			}
		}
		return array;
	}

	public int getValue() {
		return this.value;
	}

	public boolean isTaken() {
		return this.taken;
	}

	public ArrayList<Integer> getFactors() {
		return this.factors;
	}
	
	public boolean hasFactors() {
		return this.hasFactors;
	}
	
	public int getTimesTaken() {
		return this.timesTaken;
	}
	
	public void take() {
		this.timesTaken++;
		this.taken = true;
	}
	
	public void untake() {
		this.timesTaken--;
		if(timesTaken == 0) {
			this.taken = false;
		}
	}
	
	public void print() {
		System.out.println(this.value);
		System.out.println(this.taken);
		System.out.println(this.factors);
	}
	
	public void removeFactors(ArrayList<Integer> toRemove) {
		for(Integer r : toRemove) {
			for(int i = 0; i < this.factors.size(); i++) {
				if(r.equals(this.factors.get(i))) {
					this.factors.remove(i);
					i--;
				}
			}
		}
		if(this.factors.size() == 0) {
			this.hasFactors = false;
		}
	}
	
	public void readdFactors(ArrayList<Integer> usedFactors) {
		ArrayList<Integer> absFactors = getFactors(this.value);
		for(Integer f : absFactors) {
			if(!usedFactors.contains(f)) {
				if(!this.factors.contains(f)) {
					factors.add(f);
				}
			}
		}
		if(this.factors.size() > 0) {
			this.hasFactors = true;
		}
	}
}
