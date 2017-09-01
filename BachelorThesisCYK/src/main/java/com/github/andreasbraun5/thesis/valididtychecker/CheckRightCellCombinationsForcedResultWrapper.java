package com.github.andreasbraun5.thesis.valididtychecker;

import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Andreas Braun on 10.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
@Setter
@Builder
public class CheckRightCellCombinationsForcedResultWrapper {
	private boolean rightCellCombinationForced;
	private int rightCellCombinationForcedCount;
	private CellSimple[][] markedRightCellCombinationForced;
}
