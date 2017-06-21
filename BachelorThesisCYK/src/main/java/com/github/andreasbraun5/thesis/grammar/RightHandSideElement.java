package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 * Equality is checked via the name of the rightHandSideElement.
 */
public interface RightHandSideElement {

	String getName();
	default RightHandSideElement getRightHandSideElement(){
	    return this;
    }

	boolean equals(Object o);

}
