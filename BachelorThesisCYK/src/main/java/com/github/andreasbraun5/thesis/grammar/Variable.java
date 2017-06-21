package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.pyramid.CellElement;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class Variable implements LeftHandSideElement, CellElement {

	private String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		Variable variable = (Variable) o;

		return name != null ? name.equals( variable.name ) : variable.name == null;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}

    @Override
    public Variable getVariable() {
        return this;
    }
}
