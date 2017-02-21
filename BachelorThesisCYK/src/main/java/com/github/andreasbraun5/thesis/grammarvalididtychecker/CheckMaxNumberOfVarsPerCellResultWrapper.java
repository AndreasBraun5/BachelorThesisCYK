package com.github.andreasbraun5.thesis.grammarvalididtychecker;

/**
 * Created by Andreas Braun on 21.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class CheckMaxNumberOfVarsPerCellResultWrapper {
	private int maxNumberOfVarsPerCell;
	private boolean isMaxNumberOfVarsPerCell;

	public static CheckMaxNumberOfVarsPerCellResultWrapper buildCheckMaxNumberOfVarsPerCellResultWrapper(){
		return new CheckMaxNumberOfVarsPerCellResultWrapper();
	}

	public int getMaxNumberOfVarsPerCell() {
		return maxNumberOfVarsPerCell;
	}

	public CheckMaxNumberOfVarsPerCellResultWrapper setMaxNumberOfVarsPerCell(int maxNumberOfVarsPerCell) {
		this.maxNumberOfVarsPerCell = maxNumberOfVarsPerCell;
		return this;
	}

	public boolean isMaxNumberOfVarsPerCell() {
		return isMaxNumberOfVarsPerCell;
	}

	public CheckMaxNumberOfVarsPerCellResultWrapper setMaxNumberOfVarsPerCell(boolean maxNumberOfVarsPerCell) {
		isMaxNumberOfVarsPerCell = maxNumberOfVarsPerCell;
		return this;
	}
}
