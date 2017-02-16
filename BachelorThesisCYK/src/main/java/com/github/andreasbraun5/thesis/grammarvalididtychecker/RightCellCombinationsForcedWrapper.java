package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.util.SetVMatrix;

/**
 * Created by Andreas Braun on 10.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class RightCellCombinationsForcedWrapper {

	private boolean isRightCellCombinationForced;
	private int countRightCellCombinationForced;
	private SetVMatrix markedRightCellCombinationForced;

	public static RightCellCombinationsForcedWrapper buildRightCellCombinationsForcedWrapper() {
		return new RightCellCombinationsForcedWrapper();
	}

	public boolean isRightCellCombinationForced() {
		return isRightCellCombinationForced;
	}

	public RightCellCombinationsForcedWrapper setRightCellCombinationForced(boolean rightCellCombinationForced) {
		isRightCellCombinationForced = rightCellCombinationForced;
		return this;
	}

	public int getCountRightCellCombinationForced() {
		return countRightCellCombinationForced;
	}

	public RightCellCombinationsForcedWrapper setCountRightCellCombinationForced(int countRightCellCombinationForced) {
		this.countRightCellCombinationForced = countRightCellCombinationForced;
		return this;
	}

	public SetVMatrix getMarkedRightCellCombinationForced() {
		return markedRightCellCombinationForced;
	}

	public RightCellCombinationsForcedWrapper setMarkedRightCellCombinationForced(SetVMatrix markedRightCellCombinationForced) {
		this.markedRightCellCombinationForced = markedRightCellCombinationForced;
		return this;
	}
}
