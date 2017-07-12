package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import com.github.andreasbraun5.thesis.pyramid.CellSimple;
/**
 * Created by Andreas Braun on 10.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class CheckRightCellCombinationsForcedResultWrapper {

	private boolean isRightCellCombinationForced;
	private int countRightCellCombinationForced;
	private CellSimple[][] markedRightCellCombinationForced;

	public static CheckRightCellCombinationsForcedResultWrapper buildRightCellCombinationsForcedWrapper() {
		return new CheckRightCellCombinationsForcedResultWrapper();
	}

	public boolean isRightCellCombinationForced() {
		return isRightCellCombinationForced;
	}

	public CheckRightCellCombinationsForcedResultWrapper setRightCellCombinationForced(boolean rightCellCombinationForced) {
		isRightCellCombinationForced = rightCellCombinationForced;
		return this;
	}

	public int getCountRightCellCombinationForced() {
		return countRightCellCombinationForced;
	}

	public CheckRightCellCombinationsForcedResultWrapper setCountRightCellCombinationForced(int countRightCellCombinationForced) {
		this.countRightCellCombinationForced = countRightCellCombinationForced;
		return this;
	}

	public CellSimple[][] getMarkedRightCellCombinationForced() {
		return markedRightCellCombinationForced;
	}

	public CheckRightCellCombinationsForcedResultWrapper setMarkedRightCellCombinationForced(CellSimple[][] markedRightCellCombinationForced) {
		this.markedRightCellCombinationForced = markedRightCellCombinationForced;
		return this;
	}
}
