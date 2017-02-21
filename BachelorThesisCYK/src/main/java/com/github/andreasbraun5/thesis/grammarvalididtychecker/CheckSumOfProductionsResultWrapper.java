package com.github.andreasbraun5.thesis.grammarvalididtychecker;

/**
 * Created by Andreas Braun on 21.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class CheckSumOfProductionsResultWrapper {
	private int maxSumOfProductions;
	private boolean isSumOfProductions;

	public int getMaxSumOfProductions() {
		return maxSumOfProductions;
	}

	public static CheckSumOfProductionsResultWrapper builCheckSumOfProductionsResultWrapper(){
		return new CheckSumOfProductionsResultWrapper();
	}

	public CheckSumOfProductionsResultWrapper setMaxSumOfProductions(int maxSumOfProductions) {
		this.maxSumOfProductions = maxSumOfProductions;
		return this;
	}

	public boolean isSumOfProductions() {
		return isSumOfProductions;
	}

	public CheckSumOfProductionsResultWrapper setSumOfProductions(boolean sumOfProductions) {
		isSumOfProductions = sumOfProductions;
		return this;
	}
}
