package com.github.andreasbraun5.thesis.grammar;

import com.github.andreasbraun5.thesis.pyramid.CellElement;
import lombok.EqualsAndHashCode;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
@EqualsAndHashCode
public class Variable implements LeftHandSideElement, CellElement {

	private String name;

	public Variable(String name) {
		this.name = name;
	}

	public static Variable of(String name)  {
	    return new Variable(name);
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
    public Variable getLhse() {
        return this;
    }
}
