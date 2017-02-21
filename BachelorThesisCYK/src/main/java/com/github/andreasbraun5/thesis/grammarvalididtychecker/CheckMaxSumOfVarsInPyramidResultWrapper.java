package com.github.andreasbraun5.thesis.grammarvalididtychecker;

/**
 * Created by Andreas Braun on 21.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class CheckMaxSumOfVarsInPyramidResultWrapper {
	private int maxSumOfVarsInPyramid;
	private boolean isMaxSumOfVarsInPyramid;

	public static CheckMaxSumOfVarsInPyramidResultWrapper buildCheckMaxSumOfVarsInPyramidResultWrapper(){
		return new CheckMaxSumOfVarsInPyramidResultWrapper();
	}

	public int getMaxSumOfVarsInPyramid() {
		return maxSumOfVarsInPyramid;
	}

	public CheckMaxSumOfVarsInPyramidResultWrapper setMaxSumOfVarsInPyramid(int maxSumOfVarsInPyramid) {
		this.maxSumOfVarsInPyramid = maxSumOfVarsInPyramid;
		return this;
	}

	public boolean isMaxSumOfVarsInPyramid() {
		return isMaxSumOfVarsInPyramid;
	}

	public CheckMaxSumOfVarsInPyramidResultWrapper setMaxSumOfVarsInPyramid(boolean maxSumOfVarsInPyramid) {
		isMaxSumOfVarsInPyramid = maxSumOfVarsInPyramid;
		return this;
	}
}
