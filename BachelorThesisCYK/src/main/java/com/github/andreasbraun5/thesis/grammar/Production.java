package com.github.andreasbraun5.thesis.grammar;

import lombok.EqualsAndHashCode;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 * The equality of a production is determined via the equality of the names of the leftHandSideElement and the name of
 * the rightHandSideElement.
 */
@EqualsAndHashCode
public class Production {

	private Variable leftHandSideElement;
	/* List<RightHandSideElement> because A --> BC or A --> AB is possible and the ordering is important.
		The example above shows that there are two rightHandSideElements each.
	 */
	private RightHandSideElement rightHandSideElement;

	public Production(Variable leftHandSideElement, RightHandSideElement rightHandSideElement) {
		this.leftHandSideElement = leftHandSideElement;
		this.rightHandSideElement = rightHandSideElement;
	}

	public static Production of(Variable leftHandSideElement, RightHandSideElement rightHandSideElement) {
	    return new Production(leftHandSideElement, rightHandSideElement);
    }

	public Production(Variable leftHandSideElement) {
		this.leftHandSideElement = leftHandSideElement;
	}

	/*
	Calling isElementAtRightHandSide checks whether one specific production already exists
	 */
	public boolean isElementAtRightHandSide(RightHandSideElement rightHandSideElement) {
		return this.rightHandSideElement != null && this.rightHandSideElement.equals( rightHandSideElement );
	}

	@Override
	public String toString() {
		return "{(" +
				leftHandSideElement + ")-->" + "(" + rightHandSideElement + ")" +
				"}";
	}

	public Variable getLeftHandSideElement() {
		return this.leftHandSideElement;
	}

	public RightHandSideElement getRightHandSideElement() {
		return this.rightHandSideElement;
	}
}
