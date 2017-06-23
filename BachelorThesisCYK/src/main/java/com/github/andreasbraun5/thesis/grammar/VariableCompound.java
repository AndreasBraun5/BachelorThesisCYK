package com.github.andreasbraun5.thesis.grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas Braun on 08.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class VariableCompound implements RightHandSideElement {

	protected List<Variable> variables;

	public VariableCompound(Variable varLeft, Variable varRight) {
		variables = new ArrayList<>();
		variables.add( varLeft );
		variables.add( varRight );
	}

	public List<Variable> getVariables() {
		return variables;
	}

	@Override
	public String getTerminalName() {
		return variables.get( 0 ).toString() + variables.get( 1 ).toString();
	}

	@Override
	public String toString() {
		return variables.get( 0 ).toString() + variables.get( 1 ).toString();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}

		VariableCompound that = (VariableCompound) o;

		return variables != null ? variables.equals( that.variables ) : that.variables == null;

	}

	@Override
	public int hashCode() {
		return variables != null ? variables.hashCode() : 0;
	}
}
