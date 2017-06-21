package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 27.12.2016.
 * https://github.com/AndreasBraun5/
 * Equality is checked via the name of the variable.
 */
public interface LeftHandSideElement {

	String getName();
	default LeftHandSideElement getLeftHandSideElement(){
	    return this;
    }

    boolean equals(Object o);

}
