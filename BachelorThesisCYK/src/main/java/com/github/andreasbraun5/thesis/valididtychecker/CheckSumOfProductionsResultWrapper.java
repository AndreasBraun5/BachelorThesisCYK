package com.github.andreasbraun5.thesis.valididtychecker;

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
public class CheckSumOfProductionsResultWrapper {
	private int maxSumOfProductionsCount;
	private boolean sumOfProductions;
}
