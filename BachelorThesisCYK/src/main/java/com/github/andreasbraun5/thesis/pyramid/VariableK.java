package com.github.andreasbraun5.thesis.pyramid;

import com.github.andreasbraun5.thesis.grammar.LeftHandSideElement;
import com.github.andreasbraun5.thesis.grammar.TreeElement;
import com.github.andreasbraun5.thesis.grammar.Variable;

/**
 * Created by Andreas Braun on 24.01.2017.
 * https://github.com/AndreasBraun5/
 * The VariableK class is need for the calculation of the setV for the advancedCYK algorithm. The index k of the
 * loop is additionally stored to the Variable.
 */
public class VariableK implements CellElement, TreeElement {

	private String name;
	private Variable variable;
	private int k;

	public VariableK(Variable variable, int k) {
		this.name = variable.toString() + k;
		this.variable = variable;
		this.k = k;
	}

	public String getName() {
        return variable.toString() + k;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		VariableK that = (VariableK) o;

		if ( k != that.k ) {
			return false;
		}
		return variable != null ? variable.equals( that.variable ) : that.variable == null;

	}

	@Override
	public int hashCode() {
		int result = variable != null ? variable.hashCode() : 0;
		result = 31 * result + k;
		return result;
	}

	@Override
	public Variable getLhse() {
		return variable;
	}

	public int getK() {
		return k;
	}

	@Override
	public String toString() {
		return variable.toString() + k;
	}
}
