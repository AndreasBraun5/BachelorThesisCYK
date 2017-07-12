package com.github.andreasbraun5.thesis.grammarvalididtychecker;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Andreas Braun on 21.02.2017.
 * https://github.com/AndreasBraun5/
 */
@Getter
@Setter
@Builder
public class CheckMaxNumberOfVarsPerCellResultWrapper {
	private int maxNumberOfVarsPerCell;
	private boolean isMaxNumberOfVarsPerCell;
}
